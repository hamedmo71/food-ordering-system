package com.food.ordering.system.order.service.domain.entity;


import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.exc.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemID;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;

import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {

    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress streetAddress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessage;

    public void initializeOrder() {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();

    }

    private void initializeOrderItems() {
        long orderId = 1;
        for (OrderItem orderItem : items) {
            orderItem.intitializeOrderItem(super.getId(), new OrderItemID(orderId++));
        }
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemPrice();
    }

    private void validateItemPrice() {
        Money orderItemsTotalPrice = items.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO, Money::add);

        if (!price.equals(orderItemsTotalPrice)) {
            throw new OrderDomainException("Order price " + price.getAmount() +
                    " is not equals to order items total: " + orderItemsTotalPrice.getAmount() + "!");
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (orderItem.isPriceValid()) {
            throw new OrderDomainException("Order item price " + orderItem.getPrice().getAmount() +
                    " is not valid for productId " + orderItem.getProduct().getId().getValue());
        }
    }

    private void validateTotalPrice() {
        if (price == null || !price.isGreaterThanZero()) {
            throw new OrderDomainException("Total price must be greater than zero.");
        }
    }

    private void validateInitialOrder() {
        if (orderStatus != null || getId() != null) {
            throw new OrderDomainException("Order is not state for initialization.");
        }
    }

    public void paid() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order status is not correct state for pay operation!");
        }

        orderStatus = OrderStatus.PAID;
    }

    public void approved() {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("Order status is not correct state for approve operation!");
        }

        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessage) {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("Order status is not correct state for cancel operation!");
        }

        orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessage);
    }

    public void cancel() {
        if (orderStatus != OrderStatus.PENDING && orderStatus != OrderStatus.CANCELLING) {
            throw new OrderDomainException("Order status is not correct state for cancel operation!");
        }

        orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessage);
    }

    private void updateFailureMessages(List<String> failureMessage) {
        if (this.failureMessage != null && failureMessage != null) {
            this.failureMessage.addAll(failureMessage.stream().filter(message -> !message.isEmpty()).toList());
        }

        if (this.failureMessage == null) {
            this.failureMessage = failureMessage;
        }
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessage() {
        return failureMessage;
    }

    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        streetAddress = builder.streetAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessage = builder.failureMessage;
    }


    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress streetAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessage;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(OrderId orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder streetAddress(StreetAddress val) {
            streetAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessage(List<String> val) {
            failureMessage = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
