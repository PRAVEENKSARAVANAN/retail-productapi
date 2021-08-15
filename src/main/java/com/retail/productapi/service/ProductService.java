package com.retail.productapi.service;

import com.retail.productapi.model.Product;

public interface ProductService {
    Product getProductWithPrice(Long productId);

    Product updatePriceByProductId(Long productId, Product product);
}
