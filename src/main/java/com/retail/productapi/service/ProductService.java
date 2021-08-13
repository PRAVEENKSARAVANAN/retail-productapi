package com.retail.productapi.service;

import com.retail.productapi.model.Product;

public interface ProductService {
    Product getProductInfo(Long productId);
}
