package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.service.domain.ports.output.publisher.payment.OrderCancelledPaymentRequestPublisher;
import com.food.ordering.system.service.domain.ports.output.publisher.payment.OrderCreatedPaymentRequestPublisher;
import com.food.ordering.system.service.domain.ports.output.publisher.restaurant.OrderPaidRestaurantRequestMessagePublisher;
import com.food.ordering.system.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.service.domain.ports.output.repository.RestaurantRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class OrderTestConfiguration {

    @Bean
    public OrderCreatedPaymentRequestPublisher orderCreatedPaymentRequestPublisher(){
        return Mockito.mock(OrderCreatedPaymentRequestPublisher.class);
    }

    @Bean
    public OrderCancelledPaymentRequestPublisher orderCancelledPaymentRequestPublisher(){
        return Mockito.mock(OrderCancelledPaymentRequestPublisher.class);
    }

    @Bean
    public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher(){
        return Mockito.mock(OrderPaidRestaurantRequestMessagePublisher.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository(){
        return Mockito.mock(RestaurantRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository(){
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public OrderRepository orderRepository(){
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService(){
        return new OrderDomainServiceImpl();
    }
}
