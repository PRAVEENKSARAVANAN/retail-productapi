package com.retail.productapi.controller;

import com.retail.productapi.model.Product;
import com.retail.productapi.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Api(value = "/api/v1", produces = "application/json", consumes = "application/json")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    /**
     * Method used to fetch Product Information from External API & retrieve price info for the same product
     * from No-SQL Db.
     *
     * @param productId
     * @return
     */

    @GetMapping(path = "/products/{productId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Product Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})

    @ApiOperation(value = "Retrieve Product and Price details by Product Id")
    ResponseEntity<Product> getProductWithPrice(@PathVariable Long productId) {

        Product product = productService.getProductWithPrice(productId);

        ResponseEntity<Product> responseEntity = null;
        if (product != null) {
            responseEntity = new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            logger.info("getProductInfo(), Product not found for id - ", productId);
            responseEntity = new ResponseEntity("Product not found for the Id - " + productId, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @PutMapping(path = "/products/{productId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Product Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})

    @ApiOperation(value = "Update Price details based on Product Id")
    ResponseEntity<Product> updatePriceForProduct(@PathVariable Long productId, @RequestBody Product product) {

        Product productResponse = productService.updatePriceByProductId(productId, product);

        ResponseEntity<Product> responseEntity = null;
        if (productResponse != null) {
            responseEntity = new ResponseEntity<>(productResponse, HttpStatus.OK);
        } else {
            logger.info("getProductInfo(), Product not found for id - ", productId);
            responseEntity = new ResponseEntity("Product not found for the Id - " + productId, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }
}
