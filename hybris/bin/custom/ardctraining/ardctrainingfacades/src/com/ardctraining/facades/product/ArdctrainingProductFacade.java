package com.ardctraining.facades.product;


import com.ardctraining.facades.product.data.CustomProductLabelData;
import de.hybris.platform.commercefacades.product.ProductFacade;

import java.util.List;

public interface ArdctrainingProductFacade extends ProductFacade {
   // List<CustomProductLabelData> getCustomLAbels(final String product);
   List<CustomProductLabelData> getCustomLabels(final String product);

   /**
    *
    * @param customerId
    * @param productCode
    * @return
    */
   List<CustomProductLabelData> getCustomLabelsByCustomerAndProduct(String customerId, String productCode);
}
