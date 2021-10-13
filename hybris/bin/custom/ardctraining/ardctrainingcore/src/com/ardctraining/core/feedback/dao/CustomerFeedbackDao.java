package com.ardctraining.core.feedback.dao;

import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

public interface CustomerFeedbackDao {
    /**
     *
     * Fetch the type’s information by customer and NOT_READ/PASTDUE status .
     * @param customerModel
     * @return
     */
    List<CustomerFeedbackModel> findByCustomerNotReadAndPastDue(CustomerModel customerModel);

    /**
     * Method to save a feedback
     * @param customerFeedbackModel
     * @return
     */
    CustomerFeedbackModel save(CustomerFeedbackModel customerFeedbackModel);
}
