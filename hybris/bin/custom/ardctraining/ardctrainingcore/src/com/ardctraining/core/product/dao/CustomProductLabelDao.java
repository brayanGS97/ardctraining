package com.ardctraining.core.product.dao;

import com.ardctraining.core.model.CustomProductLabelModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;

import java.util.Date;
import java.util.List;

public interface CustomProductLabelDao {
    /**
     *
     * @param customer
     * @param product
     * @return
     */
    List<CustomProductLabelModel> findByCustomerAndProduct(CustomerModel customer, ProductModel product);

    /**
     *
     * @param now
     * @return
     */
    List<CustomProductLabelModel> findExpired(Date now);

    /**
     *
     * @param customer
     * @param product
     * @return
     */
    List<CustomProductLabelModel> findByCustomerAndProductAndNullCustomer(CustomerModel customer, ProductModel product);

    /**
     *
     * @param product
     * @return
     */
    List<CustomProductLabelModel> findByProduct( ProductModel product);
}
