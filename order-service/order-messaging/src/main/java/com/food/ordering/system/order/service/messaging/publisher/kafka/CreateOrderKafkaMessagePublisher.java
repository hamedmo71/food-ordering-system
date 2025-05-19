package com.food.ordering.system.order.service.messaging.publisher.kafka;

import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.food.ordering.system.kafka.producer.KafkaMessageHelper;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.food.ordering.system.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.service.domain.ports.output.messaging.publisher.payment.OrderCreatedPaymentRequestPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateOrderKafkaMessagePublisher implements OrderCreatedPaymentRequestPublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> paymentRequestKafkaProducer;
    private final KafkaMessageHelper kafkaMessageHelper;

    @Override
    public void publish(OrderCreatedEvent domainEvent) {

        String orderId = domainEvent.getOrder().getId().getValue().toString();
        log.info("Publishing order id {}", orderId);

        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper.orderCreatedEventToPaymentRequestAvroModel(domainEvent);
             paymentRequestKafkaProducer.send(
                     orderServiceConfigData.getPaymentRequestTopicName(),
                     orderId,
                     paymentRequestAvroModel,
                     kafkaMessageHelper.getKafkaCallback(
                             orderServiceConfigData.getPaymentResponseTopicName(),
                              paymentRequestAvroModel,
                             orderId,
                             "PaymentRequestAvroModel"
                     )
             );
        } catch (Exception e){
            log.error("Error while sending PaymentRequestAvroModel message to kafka with id {} , error {}.",
                    orderId, e.getMessage());
        }
    }
}
