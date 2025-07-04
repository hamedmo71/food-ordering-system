package com.food.ordering.system.service.domain;

import com.food.ordering.system.domain.event.EmptyEvent;
import com.food.ordering.system.order.service.domain.OrderDomainService;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.saga.SagaStep;
import com.food.ordering.system.service.domain.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.service.domain.ports.output.messaging.publisher.payment.OrderCancelledPaymentRequestPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderApprovalSaga implements SagaStep<RestaurantApprovalResponse, EmptyEvent, OrderCancelledEvent> {

    private final OrderDomainService orderDomainService;
    private final OrderSagaHelper orderSagaHelper;
    private final OrderCancelledPaymentRequestPublisher orderCancelledPaymentRequestPublisher;

    @Override
    @Transactional
    public EmptyEvent process(RestaurantApprovalResponse restaurantApprovalResponse) {
        log.info("Approving order with id {}", restaurantApprovalResponse.getOrderId());
        Order order = orderSagaHelper.findOrder(restaurantApprovalResponse.getOrderId());
        orderDomainService.approveOrder(order);
        orderSagaHelper.saveOrder(order);
        log.info("Order with id {} has been approved.", restaurantApprovalResponse.getOrderId());
        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public OrderCancelledEvent rollback(RestaurantApprovalResponse restaurantApprovalResponse) {
        log.info("Cancelling order with id {}", restaurantApprovalResponse.getOrderId());
        Order order = orderSagaHelper.findOrder(restaurantApprovalResponse.getOrderId());
        OrderCancelledEvent orderCancelledEvent = orderDomainService.cancelOrderPayment(order, restaurantApprovalResponse.getFailureMessages(), orderCancelledPaymentRequestPublisher);
        orderSagaHelper.saveOrder(order);
        log.info("Cancelling order with id {}", restaurantApprovalResponse.getOrderId());
        return orderCancelledEvent;
    }
}
