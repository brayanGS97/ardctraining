package com.ardctraining.facades.populators;

import com.ardctraining.facades.product.data.CustomProductLabelData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ArdctrainingSearchResultVariantPopulator extends SearchResultVariantProductPopulator {

    private UserService userService;

    private static final String CUSTOM_LABEL_PROPERTY="customLabel";
    private static final String PROPERTY_VALUE_SEPARATOR="\\|";
    private static final String ALL_WILDCARD="*";

    private static final Logger LOG = Logger.getLogger(ArdctrainingSearchResultVariantPopulator.class);

    @Override
    public void populate(SearchResultValueData source, ProductData target) {
        super.populate(source, target);
        populateCustomLabels(source,target);
    }
    private void populateCustomLabels(final SearchResultValueData source, final ProductData target){
        final List<String> labels = getValue(source,CUSTOM_LABEL_PROPERTY);
        if (CollectionUtils.isNotEmpty(labels)) {
            final UserModel userModel = getUserService().getCurrentUser();
            target.setLabels(convertLabels(labels,userModel));
        }else{
            target.setLabels(Collections.emptyList());
        }
    }
    private List<CustomProductLabelData> convertLabels(final List<String> labels, final UserModel user){
        final List<CustomProductLabelData> labelsList = new ArrayList<>();
        labels.forEach((String label) -> {
            final String[] parts = label.split(PROPERTY_VALUE_SEPARATOR);
            if (parts.length==3){
                if (parts[0].trim().equals(user.getUid()) ||parts[0].trim().equals(ALL_WILDCARD)){
                    final CustomProductLabelData tLabel = new CustomProductLabelData();
                    tLabel.setLabel(parts[1]);
                    tLabel.setLabel(parts[2]);
                    labelsList.add(tLabel);
                }
            }else{
                LOG.error(String.format("Unable tto convert custom label indexed property [%s]",label));
            }
        });
        return labelsList;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
