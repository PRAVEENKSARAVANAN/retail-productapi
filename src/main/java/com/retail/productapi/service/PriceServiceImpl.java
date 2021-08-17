package com.retail.productapi.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.retail.productapi.dao.PriceDao;
import com.retail.productapi.model.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    private PriceDao priceDao;

    public PriceServiceImpl(PriceDao priceDao) {
        this.priceDao = priceDao;
    }

    private static final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

    @Override
    public Price getPrice(Long productId) {
        Optional<com.retail.productapi.entity.Price> price = priceDao.findById(productId);
        if (!price.isEmpty()) {
            logger.info(" Price Info for the Product Id {} : " + productId);
            return populatePriceModelObject(price.get());
        } else {
            logger.info("Price - Not Available ");
            return null;
        }
    }

    @Override
    public Price updatePrice(Long productId, Price price) {
        com.retail.productapi.entity.Price priceEntity = new com.retail.productapi.entity.Price(productId, price.getPrice(), price.getCurrencyCode());
        return populatePriceModelObject(priceDao.save(priceEntity));
    }

    /**
     * @param price
     * @return
     */
    private Price populatePriceModelObject(com.retail.productapi.entity.Price price) {
        return new Price(price.getPrice(), price.getCurrencyCode());
    }

}
