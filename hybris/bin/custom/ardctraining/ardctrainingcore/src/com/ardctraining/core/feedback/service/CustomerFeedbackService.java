package com.ardctraining.core.feedback.service;

import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

public interface CustomerFeedbackService {
    /**
     *
     * Fetch the type’s information by customer and NOT_READ/PASTDUE status .
     * @param customerModel
     * @return
     */
    List<CustomerFeedbackModel> findByCustomerNotReadAndPastDue(CustomerModel customerModel);

    CustomerFeedbackModel save(CustomerFeedbackModel customerFeedbackModel);

}
