package com.ardctraining.core.feedback.service.impl;

import com.ardctraining.core.feedback.dao.CustomerFeedbackDao;
import com.ardctraining.core.feedback.service.CustomerFeedbackService;
import com.ardctraining.core.model.CustomProductLabelModel;
import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultCustomerFeedbackService implements CustomerFeedbackService {
    private CustomerFeedbackDao customerFeedbackDao;

    private Logger LOG = Logger.getLogger(DefaultCustomerFeedbackService.class);
    @Override
    public List<CustomerFeedbackModel> findByCustomerNotReadAndPastDue(CustomerModel customerModel) {
        ServicesUtil.validateParameterNotNull(customerModel,"customer cannot be null");

        return getCustomerFeedbackDao().findByCustomerNotReadAndPastDue(customerModel);
    }

    @Override
    public CustomerFeedbackModel save(CustomerFeedbackModel customerFeedbackModel) {
        return getCustomerFeedbackDao().save(customerFeedbackModel);
    }

    public CustomerFeedbackDao getCustomerFeedbackDao() {
        return customerFeedbackDao;
    }

    public void setCustomerFeedbackDao(CustomerFeedbackDao customerFeedbackDao) {
        this.customerFeedbackDao = customerFeedbackDao;
    }
}
