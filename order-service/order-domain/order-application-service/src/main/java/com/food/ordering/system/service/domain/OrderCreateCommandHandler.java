package com.food.ordering.system.service.domain;

import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.service.domain.ports.output.publisher.payment.OrderCreatedPaymentRequestPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class OrderCreateCommandHandler {


    private final OrderCreatedPaymentRequestPublisher orderCreatedPaymentRequestPublisher;
    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateCommandHandler(OrderCreatedPaymentRequestPublisher orderCreatedPaymentRequestPublisher, OrderCreateHelper orderCreateHelper, OrderDataMapper orderDataMapper) {
        this.orderCreatedPaymentRequestPublisher = orderCreatedPaymentRequestPublisher;
        this.orderCreateHelper = orderCreateHelper;
        this.orderDataMapper = orderDataMapper;
    }

    /*1. when we use @Transactional method must be public because it uses reflection,
     and it could reflect public methods.
     2. when we use @Transactional the method must call by another bean. we couldn't use this annotation
     without proxying it with another bean.*/
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
        orderCreatedPaymentRequestPublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(), "Order created successfully.");
    }

}
