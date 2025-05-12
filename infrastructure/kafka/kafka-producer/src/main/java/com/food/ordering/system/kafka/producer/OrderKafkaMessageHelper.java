package com.food.ordering.system.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
@Slf4j
public class OrderKafkaMessageHelper {

    public <T> BiConsumer<SendResult<String, T>, Throwable> getKafkaCallback(String responseTopicName, T avroModel,
                                                                             String orderId, String avroModelName) {

        return (result, ex) -> {
            if (ex == null) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info("Received successful response from kafka for order id : {}," +
                                " Topic: {}, Pratition: {} , Offset: {}, Timesstamp: {}",
                        orderId, recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(), recordMetadata);
            } else {
                log.error("Error while sending {} with message {} to topic {}",
                        avroModelName, avroModel.toString(), responseTopicName, ex);
            }
        };
    }
}
