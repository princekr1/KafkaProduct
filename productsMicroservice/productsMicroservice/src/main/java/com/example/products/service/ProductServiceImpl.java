package com.example.products.service;

import com.appsdeveloperblog.ws.core.ProductCreatedEvent;
import com.example.products.rest.CreateProductRestModel;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public String createProduct(CreateProductRestModel productRestModel) {

        String productId= UUID.randomUUID().toString();

        //TODO : Persist Product Details into database table before publishing an Event

        ProductCreatedEvent productCreatedEvent=new ProductCreatedEvent(productId,
                productRestModel.getTitle(),productRestModel.getPrice(),
                productRestModel.getQuantity());

        CompletableFuture<SendResult<String,ProductCreatedEvent>> future=
                kafkaTemplate.send("product-created-events",productId,productCreatedEvent);

        future.whenComplete((result,exception)->{
            if(exception!=null){
                logger.error("Failed to send Message : " +exception.getMessage());
            }else{
                logger.info("Message sent successfully: "+result.getRecordMetadata());
            }
        });
        logger.info("*********** Returning ProductId");
        return productId;
    }

    @Override
    public String createProductSynchronous(CreateProductRestModel productRestModel) throws Exception{

        String productId= UUID.randomUUID().toString();

        //TODO : Persist Product Details into database table before publishing an Event

        ProductCreatedEvent productCreatedEvent=new ProductCreatedEvent(productId,
                productRestModel.getTitle(),productRestModel.getPrice(),
                productRestModel.getQuantity());

        logger.info("Before publishing a ProductCreatedEvent");
        SendResult<String,ProductCreatedEvent> result=
                kafkaTemplate.send("product-created-events",productId,productCreatedEvent).get();


        logger.info("After publishing a ProductCreatedEvent");
        logger.info("Partition: "+result.getRecordMetadata().partition());
        logger.info("Topic: "+result.getRecordMetadata().topic());
        logger.info("Offset: "+result.getRecordMetadata().offset());
        logger.info("Timestamp: "+result.getRecordMetadata().timestamp());

        logger.info("*********** Returning ProductId Synchronously");
        return productId;
    }


}
