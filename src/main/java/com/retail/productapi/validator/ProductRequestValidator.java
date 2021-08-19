package com.retail.productapi.validator;

import com.retail.productapi.model.Product;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestValidator {

    Logger logger = LoggerFactory.getLogger(ProductRequestValidator.class);

    /**
     * Implements the validation logic for the update Product Request Body
     *
     * @param product  object to validate
     * @param productId
     * @return boolean
     */
    public boolean isValidRequest(Long productId, Product product) {

        if(product != null
                && product.getId() > 0
                && product.getPrice()!=null
                && product.getPrice().getPrice() > 0.0
                && StringUtils.isNotEmpty(product.getPrice().getCurrencyCode())){
            if(product.getId().longValue() == productId.longValue()){
                return true;
            }else{
                logger.info("Product Id mismatch in the request body, Id={} ", productId, product.getId());
                return false;
            }
        }else{
            return false;
        }
    }
}
