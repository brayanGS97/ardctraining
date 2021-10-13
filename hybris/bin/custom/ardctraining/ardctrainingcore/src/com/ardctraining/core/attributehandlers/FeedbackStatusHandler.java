package com.ardctraining.core.attributehandlers;

import com.ardctraining.core.constants.ArdctrainingCoreConstants;
import com.ardctraining.core.enums.FeedbackStatusEnum;
import com.ardctraining.core.model.CustomerFeedbackModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;
import de.hybris.platform.servicelayer.time.TimeService;

import java.util.Calendar;
import java.util.Date;

public class FeedbackStatusHandler implements DynamicAttributeHandler<FeedbackStatusEnum, CustomerFeedbackModel> {
    private ConfigurationService configurationService;
    private TimeService timeService;


    @Override
    public FeedbackStatusEnum get(CustomerFeedbackModel model) {
        Date now = getTimeService().getCurrentTime();
        if(now.before(getDueDate(model))){
            if(model.getRead().equals(true)){
                return FeedbackStatusEnum.READ;
            }else{
                return FeedbackStatusEnum.NOT_READ;
            }
        }
        else{
            if(model.getRead().equals(true)){
                return FeedbackStatusEnum.READ_PASTDUE;
            }else{
                return FeedbackStatusEnum.PASTDUE;
            }
        }
    }

    @Override
    public void set(CustomerFeedbackModel model, FeedbackStatusEnum feedbackStatusEnum) {
        throw new UnsupportedOperationException("Write is not a valid operation for this dynamic attribute");
    }



    public ConfigurationService getConfigurationService() {
        return configurationService;
    }

    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public TimeService getTimeService() {
        return timeService;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    private Date getDueDate(CustomerFeedbackModel model) {
        int dueDays = getConfigurationService().getConfiguration().getInt(ArdctrainingCoreConstants.FEEDBACK_DUE_DAYS);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getSubmittedDate());
        calendar.add(Calendar.DAY_OF_YEAR,dueDays);

        return calendar.getTime();
    }
}
