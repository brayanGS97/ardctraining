package com.ardctraining.facades.feedback.impl;

import com.ardctraining.core.feedback.service.CustomerFeedbackService;
import com.ardctraining.core.model.CustomerFeedbackEmailProcessModel;
import com.ardctraining.core.model.CustomerFeedbackModel;
import com.ardctraining.facades.feedback.ArdctrainningFeedbackFacade;
import com.ardctraining.facades.feedback.CustomerFeedbackData;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.site.BaseSiteService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DefaultArdctrainingFeedbackFacade extends DefaultCustomerFacade implements ArdctrainningFeedbackFacade{

    private CustomerFeedbackService customerFeedbackService;
    private Converter<CustomerFeedbackModel,CustomerFeedbackData> customerFeedbackConverter;
    private BusinessProcessService businessProcessService;
    private TimeService timeService;
    private BaseSiteService baseSiteService;
    private Logger LOG = Logger.getLogger(DefaultArdctrainingFeedbackFacade.class);

    private static final String FIELD_SEPARATOR = "|";
    private static final String EMAIL_PROCESS_CUSTOMER_FEEDBACK = "customerFeedbackEmailProcess";


    @Override
    public List<CustomerFeedbackData> getCustomerFeedback() {

        final CustomerModel customerModel = (CustomerModel) getUserService().getCurrentUser();

        if(Objects.nonNull(customerModel) && customerModel instanceof CustomerModel){
            final List<CustomerFeedbackModel> feedbacks = getCustomerFeedbackService().findByCustomerNotReadAndPastDue(customerModel);
            return getCustomerFeedbackConverter().convertAll(feedbacks);
        }else{
            LOG.error(String.format("unable to get feedbacks with current user %s", customerModel.getUid()));
        }
        return Collections.emptyList();
    }

    @Override
    public void save(CustomerFeedbackData customerFeedbackData) {
        CustomerFeedbackModel customerFeedbackModel = new CustomerFeedbackModel();
        customerFeedbackModel.setSubject(customerFeedbackData.getSubject());
        customerFeedbackModel.setMessage(customerFeedbackData.getMessage());
        final String feedback = getCustomerFeedback(getCustomerFeedbackService().save(customerFeedbackModel));

        final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss.S");
        final Date now= getTimeService().getCurrentTime();
        StringBuilder businessProcess =
                new StringBuilder().append(EMAIL_PROCESS_CUSTOMER_FEEDBACK).append("-").append(now.toString());
        final CustomerFeedbackEmailProcessModel processModel =
                getBusinessProcessService().createProcess(businessProcess.toString(),EMAIL_PROCESS_CUSTOMER_FEEDBACK);
        processModel.setLanguage(getBaseSiteService().getCurrentBaseSite().getDefaultLanguage());
        Set<String> feedbackSet = new HashSet<>();
        feedbackSet.add(feedback);
        processModel.setCustomerFeedback(feedbackSet);
        processModel.setSite(getBaseSiteService().getCurrentBaseSite());

        getModelService().save(processModel);
        getBusinessProcessService().startProcess(processModel);
    }

    public CustomerFeedbackService getCustomerFeedbackService() {
        return customerFeedbackService;
    }

    public void setCustomerFeedbackService(CustomerFeedbackService customerFeedbackService) {
        this.customerFeedbackService = customerFeedbackService;
    }

    public Converter<CustomerFeedbackModel, CustomerFeedbackData> getCustomerFeedbackConverter() {
        return customerFeedbackConverter;
    }

    public void setCustomerFeedbackConverter(Converter<CustomerFeedbackModel, CustomerFeedbackData> customerFeedbackConverter) {
        this.customerFeedbackConverter = customerFeedbackConverter;
    }

    @Override
    public BusinessProcessService getBusinessProcessService() {
        return businessProcessService;
    }

    @Override
    public void setBusinessProcessService(BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    public TimeService getTimeService() {
        return timeService;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    @Override
    public BaseSiteService getBaseSiteService() {
        return baseSiteService;
    }

    @Override
    public void setBaseSiteService(BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
    }

    private String getCustomerFeedback(CustomerFeedbackModel customerFeedbackModel){
        return new StringBuilder()
                .append(Objects.isNull(customerFeedbackModel.getCustomer().getUid()) ? StringUtils.EMPTY : customerFeedbackModel.getCustomer().getUid())
                .append(FIELD_SEPARATOR)
                .append(customerFeedbackModel.getSubject())
                .append(FIELD_SEPARATOR)
                .append(customerFeedbackModel.getMessage())
                .append(FIELD_SEPARATOR)
                .append(customerFeedbackModel.getSubmittedDate())
                .toString();
    }
}
