package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.exc.OrderDomainException;

import java.util.List;

public class Restaurant extends AggregateRoot<RestaurantId> {

    private final List<Product> products;
    private boolean active;

    private Restaurant(Builder builder) {
        super.setId(builder.id);
        products = builder.products;
        active = builder.active;
    }

    public boolean isActive() {
        return active;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()){
            throw new OrderDomainException("Restaurant with id " + restaurant.getId().getValue() + "is not valid.");
        }
    }


    public static final class Builder {
        private RestaurantId id;
        private List<Product> products;
        private boolean active;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(RestaurantId val) {
            id = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}
