package com.food.ordering.system.service.domain.ports.output.messaging.publisher.payment;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestPublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
