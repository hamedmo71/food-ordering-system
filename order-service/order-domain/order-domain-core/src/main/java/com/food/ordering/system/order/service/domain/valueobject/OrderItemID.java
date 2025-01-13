package com.food.ordering.system.order.service.domain.valueobject;

import com.food.ordering.system.domain.valueobject.BaseId;

public class OrderItemID extends BaseId<Long> {

    public OrderItemID(Long value) {
        super(value);
    }
}
