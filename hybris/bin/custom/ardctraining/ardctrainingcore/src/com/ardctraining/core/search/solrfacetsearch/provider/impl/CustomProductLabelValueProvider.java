package com.ardctraining.core.search.solrfacetsearch.provider.impl;

import com.ardctraining.core.model.CustomProductLabelModel;
import com.ardctraining.core.product.service.CustomProductLabelService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

public class CustomProductLabelValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider {

    private FieldNameProvider fieldNameProvider;
    private CustomProductLabelService customProductLabelService;

    private static final String ALL_WILDCARD="*";
    private static final String FIELD_SEPARATOR="|";

    public FieldNameProvider getFieldNameProvider() {
        return fieldNameProvider;
    }

    public void setFieldNameProvider(FieldNameProvider fieldNameProvider) {
        this.fieldNameProvider = fieldNameProvider;
    }

    public CustomProductLabelService getCustomProductLabelService() {
        return customProductLabelService;
    }

    public void setCustomProductLabelService(CustomProductLabelService customProductLabelService) {
        this.customProductLabelService = customProductLabelService;
    }

    @Override
    public Collection<FieldValue> getFieldValues(IndexConfig indexConfig, IndexedProperty indexedProperty, Object model)
            throws FieldValueProviderException {
        if(model instanceof ProductModel){
            final ProductModel product = (ProductModel) model;
            final List<CustomProductLabelModel> labels = getCustomProductLabelService().findByProduct(product);
                    if(CollectionUtils.isNotEmpty(labels)){
                        final Collection<FieldValue> fieldValues = new ArrayList<>();

                        labels.forEach((CustomProductLabelModel label) ->
                                fieldValues.addAll(createFieldValue(label,indexedProperty)));
                        return fieldValues;
                    }
        }
        return Collections.emptyList();
    }
    protected List<FieldValue> createFieldValue(final CustomProductLabelModel label, final IndexedProperty indexedProperty){
        List<FieldValue> fieldValues = new ArrayList<>();
        final String customerValue = Objects.nonNull(label.getCustomer()) ? label.getCustomer().getOriginalUid() : ALL_WILDCARD;
        final String labelType = label.getLabelType().getCode().toLowerCase();
        final Object value = new StringBuilder()
                .append(customerValue)
                .append(FIELD_SEPARATOR)
                .append(label.getLabel())
                .append(FIELD_SEPARATOR)
                .append(labelType)
                .toString();
        final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
        fieldNames.forEach((String fieldName) -> fieldValues.add(new FieldValue(fieldName,value)));
        return fieldValues;
    }
}