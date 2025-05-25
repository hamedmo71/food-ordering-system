package com.food.ordering.system.service.domain;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exc.OrderDomainException;
import com.food.ordering.system.order.service.domain.exc.OrderNotFoundException;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import com.food.ordering.system.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.service.domain.ports.output.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
public class OrderTrackCommandHandler {

    private final OrderRepository repository;
    private final OrderDataMapper orderDataMapper;

    public OrderTrackCommandHandler(OrderRepository repository, OrderDataMapper orderDataMapper) {
        this.repository = repository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery){
        Optional<Order> orderByTrackingId = repository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if (orderByTrackingId.isEmpty()){
            log.error("Order with id {} not found", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Order with id " + trackOrderQuery.getOrderTrackingId() + " not found.");
        }
        return orderDataMapper.orderToOrderTrackResponse(orderByTrackingId.get());
    }
}
