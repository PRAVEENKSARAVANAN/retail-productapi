package com.retail.productapi.service;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.retail.productapi.model.Price;
import com.retail.productapi.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Service Implementation Class to hold all the product information including Price.
 * Kind of orchestration service for product information
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

    private ExternalApiService externalApiService;

    private PriceService priceService;

    private String redskyDomainUrl;

    private String pdpPathUrl;

    private String pdpQueryParams;

    private String jsonPathProductName;

    @Autowired
    public ProductServiceImpl(ExternalApiService externalApiService, PriceService priceService,
                              @Value("${redsky.domain.url}") String redskyDomainUrl,
                              @Value("${redsky.pdp.path.url}") String pdpPathUrl,
                              @Value("${redsky.pdp.url.params}") String pdpQueryParams,
                              @Value("${config.json.path.product.name}") String jsonPathProductName) {
        this.externalApiService = externalApiService;
        this.priceService = priceService;
        this.redskyDomainUrl = redskyDomainUrl;
        this.pdpPathUrl = pdpPathUrl;
        this.pdpQueryParams = pdpQueryParams;
        this.jsonPathProductName = jsonPathProductName;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * This Method is used to fetch the product details along with the Price information
     * store in the database.
     *
     * @param productId
     * @return Product.class
     */
    @Override
    public Product getProductWithPrice(Long productId) {
        Product productResponse = getProduct(productId);
        if (productResponse != null)
            productResponse.setPrice(priceService.getPrice(productId));
        return productResponse;
    }

    /**
     *
     * This Method is used to update the price information of the Product
     *
     * @param productId
     * @param product
     * @return product
     */
    @Override
    public Product updatePriceByProductId(Long productId, Product product) {

        // Fetch the Product Information
        Product productResponse = getProduct(productId);

        // If there is valid Product information, update the price, if not empty product Response.
        if (productResponse != null) {
            Price priceResponse = priceService.updatePrice(productId, product.getPrice());
            productResponse.setPrice(priceResponse);
        }
        return productResponse;
    }

    /**
     * This method is used to fetch the product information from the externalService.
     *
     *
     * @param productId
     * @return Product
     */
    private Product getProduct(Long productId) {
        // Fetch the Product Info from external service.
        CompletableFuture<String> completableFuture = externalApiService.getExternalApiResponse(redskyDomainUrl + pdpPathUrl + productId.toString() + pdpQueryParams, String.class);

        String externalApiResponseString = null;
        try {
            // Wait for the future result to respond
            externalApiResponseString = (String) completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Redsky Product PDP API Exception : " + e.getMessage());
        }

        Product productResponse = null;

        // If ExternalAPIRespons is Not Empty, populate the name.
        if (externalApiResponseString != null) {
            // Create JSON Path Configuration to return null value, when the path is not available
            Configuration conf = Configuration.defaultConfiguration();
            conf = conf.addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);

            // Use JsonPath library to extract the product title field.
            String productName = JsonPath.using(conf).parse(externalApiResponseString).read(jsonPathProductName);

            // Populate the product information based on the Product API Response.
            if(productName != null) {
                productResponse = new Product();
                productResponse.setId(productId);
                productResponse.setName(productName);
            }
        }

        return productResponse;
    }


}
