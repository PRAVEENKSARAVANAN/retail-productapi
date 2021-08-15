package com.retail.productapi.service;

import com.retail.productapi.model.Price;

public interface PriceService {
    Price getPrice(Long productId);

    Price updatePrice(Long productId, Price price);
}
