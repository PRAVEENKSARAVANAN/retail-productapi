package com.retail.productapi.service;

import com.retail.productapi.dao.PriceDao;
import com.retail.productapi.model.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation Class for Price Service
 */
@Service
public class PriceServiceImpl implements PriceService {

    private PriceDao priceDao;

    public PriceServiceImpl(PriceDao priceDao) {
        this.priceDao = priceDao;
    }

    private static final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

    /**
     * This method is used to fetch Price Information based on the ProductId
     *
     * @param productId
     * @return Price
     */
    @Override
    public Price getPrice(Long productId) {
        // Fetch Price from the DB.
        Optional<com.retail.productapi.entity.Price> price = priceDao.findById(productId);
        if (!price.isEmpty()) {
            logger.info(" Price Info for the Product Id {} : " + productId);
            return populatePriceModelObject(price.get());
        } else {
            logger.info("Price - Not Available ");
            return null;
        }
    }

    /**
     *
     * This method is used to update the price information for the product Id
     *
     * @param productId
     * @param price
     * @return Price
     */
    @Override
    public Price updatePrice(Long productId, Price price) {

        com.retail.productapi.entity.Price priceEntity = new com.retail.productapi.entity.Price(productId, price.getPrice(), price.getCurrencyCode());
        // update Price in the DB & populate the model object based on the return value
        return populatePriceModelObject(priceDao.save(priceEntity));
    }

    /**
     * This method is used to populate the Price Model Object from the entity.
     *
     * @param price
     * @return
     */
    private Price populatePriceModelObject(com.retail.productapi.entity.Price price) {
        return new Price(price.getPrice(), price.getCurrencyCode());
    }

}
