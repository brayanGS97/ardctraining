package com.ardctraining.facades.populators;

import com.ardctraining.core.model.CustomerFeedbackModel;
import com.ardctraining.facades.feedback.CustomerFeedbackData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CustomerFeedbackPopulator implements Populator<CustomerFeedbackModel, CustomerFeedbackData> {
    @Override
    public void populate(CustomerFeedbackModel customerFeedbackModel, CustomerFeedbackData customerFeedbackData) throws ConversionException {
        customerFeedbackData.setSubject(customerFeedbackModel.getSubject());
        customerFeedbackData.setMessage(customerFeedbackModel.getMessage());
        customerFeedbackData.setStatus(customerFeedbackModel.getStatus());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        customerFeedbackData.setSubmittedDate(dateFormat.format(customerFeedbackModel.getSubmittedDate()));
    }
}
