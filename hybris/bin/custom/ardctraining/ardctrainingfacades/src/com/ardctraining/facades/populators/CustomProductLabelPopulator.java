package com.ardctraining.facades.populators;

import com.ardctraining.core.model.CustomProductLabelModel;
import com.ardctraining.facades.product.data.CustomProductLabelData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class CustomProductLabelPopulator implements Populator<CustomProductLabelModel, CustomProductLabelData> {


    @Override
    public void populate(CustomProductLabelModel customProductLabelModel, CustomProductLabelData customProductLabelData) throws ConversionException {
        customProductLabelData.setType(customProductLabelModel.getLabelType().getCode().toLowerCase());
        customProductLabelData.setLabel(customProductLabelModel.getLabel());
    }
}
