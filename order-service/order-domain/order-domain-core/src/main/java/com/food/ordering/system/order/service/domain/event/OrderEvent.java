package com.food.ordering.system.order.service.domain.event;

import com.food.ordering.system.domain.event.DomainEvent;
import com.food.ordering.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public abstract class OrderEvent implements DomainEvent<Order> {

    private Order order;
    private final ZonedDateTime createdAt;

    public OrderEvent(final ZonedDateTime createdAt,final Order order) {
        this.createdAt = createdAt;
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
