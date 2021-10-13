package com.ardctraining.facades.feedback;

import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;

import java.util.List;

public interface ArdctrainningFeedbackFacade extends CustomerFacade {

    /**
     *
     * @return
     */
    List<CustomerFeedbackData> getCustomerFeedback();

    /**
     *
     * @param customerFeedbackData
     */
    void save(CustomerFeedbackData customerFeedbackData);

}
