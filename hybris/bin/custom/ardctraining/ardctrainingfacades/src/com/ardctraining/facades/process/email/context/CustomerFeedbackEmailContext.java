package com.ardctraining.facades.process.email.context;

import com.ardctraining.core.model.CustomerFeedbackEmailProcessModel;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.customer.CustomerService;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomerFeedbackEmailContext extends AbstractEmailContext<CustomerFeedbackEmailProcessModel> {

    private Integer size;
    private Set<Map<String, String>> feedbacks;

    private UserService userService;

    private static final String WILDCARD = "*";
    private static final String SEPARATOR = "\\|";

    private static final Logger LOG= Logger.getLogger(CustomerFeedbackEmailContext.class);

    @Override
    public void init(CustomerFeedbackEmailProcessModel businessProcessModel, EmailPageModel emailPageModel) {
        super.init(businessProcessModel, emailPageModel);

        LOG.info("Entering init method from CustomerFeedbackEmailContext");
        setSize(businessProcessModel.getCustomerFeedback().size());
        setFeedbacks(businessProcessModel.getCustomerFeedback());
    }

    @Override
    public String getToEmail() {
        return "bragarcia@externosdeloittemx.com";
    }

    @Override
    public String getToDisplayName() {
        return getUserService().getCurrentUser().getDisplayName();
    }

    @Override
    protected BaseSiteModel getSite(CustomerFeedbackEmailProcessModel businessProcessModel) {
        return businessProcessModel.getSite();
    }

    @Override
    protected CustomerModel getCustomer(CustomerFeedbackEmailProcessModel businessProcessModel) {
        return (CustomerModel) businessProcessModel.getUser();
    }

    @Override
    protected LanguageModel getEmailLanguage(CustomerFeedbackEmailProcessModel businessProcessModel) {
        return businessProcessModel.getLanguage();
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Set<Map<String, String>> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<String> feedbacks) {
        final Set<Map<String, String>> customerFeedbacks= new HashSet<>();

        feedbacks.forEach((String s) ->{
            final Map<String,String> feedback = new HashMap<>();
            final String [] parts = s.split(SEPARATOR);

            if(parts.length>0){
                feedback.put("customer", (StringUtils.isNotEmpty(parts[0]) ? parts[0] : WILDCARD));
                feedback.put("subject",parts[1]);
                feedback.put("message",parts[2]);
                feedback.put("submittedDate",parts[3]);

                customerFeedbacks.add(feedback);
            }
        });
        this.feedbacks = customerFeedbacks;
    }
}
