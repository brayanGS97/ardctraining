package com.ardctraining.core.feedback.dao.impl;

import com.ardctraining.core.feedback.dao.CustomerFeedbackDao;
import com.ardctraining.core.model.CustomProductLabelModel;
import com.ardctraining.core.model.CustomerFeedbackModel;
import com.ardctraining.core.product.dao.impl.DefaultCustomProductLabelDao;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DefaultCustomerFeedbackDao  implements CustomerFeedbackDao {

    private FlexibleSearchService flexibleSearchService;
    private UserService userService;
    private ModelService modelService;

    private static final Logger LOG= Logger.getLogger(DefaultCustomerFeedbackDao.class);

    private static final String SELECT_FROM_FEEDBACK=
            "SELECT {" + ItemModel.PK + "} " + " FROM {" + CustomerFeedbackModel._TYPECODE + "} " ;
    private static final String WHERE_CUSTOMER=
            " WHERE  {" + CustomerFeedbackModel.CUSTOMER + "} = ?customer";
    private static final String NOT_READ_OR_PASTDUE_STATUS="{" + CustomerFeedbackModel.READ + "} = FALSE";

    private static final String FIND_FEEDBACKS_BY_CUSTOMER_AND_NOT_READ_OR_PASTDUE_STATUS_QUERY=
            SELECT_FROM_FEEDBACK + WHERE_CUSTOMER + " AND " + NOT_READ_OR_PASTDUE_STATUS;

    @Override
    public List<CustomerFeedbackModel> findByCustomerNotReadAndPastDue(CustomerModel customerModel) {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(FIND_FEEDBACKS_BY_CUSTOMER_AND_NOT_READ_OR_PASTDUE_STATUS_QUERY);
        query.addQueryParameter("customer",customerModel);

        return findResult(query);
    }

    @Override
    public CustomerFeedbackModel save(CustomerFeedbackModel customerFeedbackModel) {
        final CustomerModel customerModel = (CustomerModel) getUserService().getCurrentUser();
        if(Objects.nonNull(customerModel) && customerModel instanceof CustomerModel) {
            customerFeedbackModel.setCustomer(customerModel);
            customerFeedbackModel.setSubmittedDate(new Date());
            customerFeedbackModel.setRead(false);
            getModelService().save(customerFeedbackModel);
            return customerFeedbackModel;
        }
        else{
            LOG.error(String.format("unable to save feedback with current user %s", customerModel.getUid()));
            return null;
        }
    }

    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    private List<CustomerFeedbackModel> findResult(FlexibleSearchQuery query){
        final SearchResult<CustomerFeedbackModel> result = getFlexibleSearchService().search(query);

        if (Objects.nonNull(result) && CollectionUtils.isNotEmpty(result.getResult())){
            return result.getResult();
        }
        LOG.warn("unable to find results for feedbacks");
        return Collections.emptyList();
    }

}
