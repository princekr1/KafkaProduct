package com.example.products.rest;

import com.example.products.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/products")
//https://localhost:<port>/products
public class ProductController {

    @Autowired
    ProductService productService;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody CreateProductRestModel product){
        String productId;
        try{
            //Asynchronous call
            //productId=productService.createProduct(product);
            //Synchronous call
            productId=productService.createProductSynchronous(product);
        } catch (Exception e) {
            //e.fillInStackTrace();
            logger.error(e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(new Date(),e.getMessage(),"/products"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

}
