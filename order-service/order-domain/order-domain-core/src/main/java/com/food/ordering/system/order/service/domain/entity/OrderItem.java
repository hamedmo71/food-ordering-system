package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemID;

public class OrderItem extends BaseEntity<OrderItemID> {

    private OrderId orderId;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;

    private OrderItem(Builder builder) {
        super.setId(builder.orderItemId);
        this.orderId = builder.orderId;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.price = builder.price;
        this.subTotal = builder.subTotal;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    public void intitializeOrderItem(OrderId orderId, OrderItemID orderItemID) {
        this.orderId = orderId;
        super.setId(orderItemID);
    }

    public boolean isPriceValid() {
        return price.isGreaterThanZero() &&
                price.equals(product.getPrice()) &&
                price.multiply(quantity).equals(subTotal);

    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderItemID orderItemId;
        private OrderId orderId;
        private Product product;
        private int quantity;
        private Money price;
        private Money subTotal;

        public Builder() {
        }

        public Builder orderItemID(OrderItemID val) {
            this.orderItemId = val;
            return this;
        }

        public Builder orderID(OrderId val) {
            this.orderId = val;
            return this;
        }

        public Builder product(Product val) {
            this.product = val;
            return this;
        }

        public Builder quantity(int val) {
            this.quantity = val;
            return this;
        }

        public Builder price(Money val) {
            this.price = val;
            return this;
        }

        public Builder subTotal(Money val) {
            this.subTotal = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
