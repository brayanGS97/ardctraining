package com.ardctraining.backoffice.labels;

import com.ardctraining.core.model.CustomProductLabelModel;
import com.hybris.cockpitng.labels.LabelProvider;

import java.util.Objects;

public class CustomProductLabelLabel implements LabelProvider<CustomProductLabelModel> {
    private static final String ALL_WILDCARD = "*";

    @Override
    public String getLabel(final CustomProductLabelModel customProductLabelModel) {
        final String customer = Objects.nonNull(customProductLabelModel.getCustomer())?
                customProductLabelModel.getCustomer().getUid() : ALL_WILDCARD;

        return new StringBuilder().append(customProductLabelModel.getProduct().getCode())
                .append(" - ")
                .append(customer)
                .append(" - ").append(customProductLabelModel.getLabel()).toString();
    }

    @Override
    public String getDescription(CustomProductLabelModel customProductLabelModel) {
        return getLabel(customProductLabelModel);
    }

    @Override
    public String getIconPath(CustomProductLabelModel customProductLabelModel) {
        return null;
    }
}
