package com.food.ordering.system.order.service.dataaccess.order.adapter;

import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity;
import com.food.ordering.system.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import com.food.ordering.system.order.service.dataaccess.order.repository.OrderJPARepository;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import com.food.ordering.system.service.domain.ports.output.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJPARepository orderJPARepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderDataAccessMapper.orderToOrderEntity(order);
        OrderEntity savedOrder = orderJPARepository.save(orderEntity);
        return orderDataAccessMapper.orderEntityToOrder(savedOrder);

        /*more compact way*/
        /*return orderDataAccessMapper.orderEntityToOrder
                (orderJPARepository.save(orderDataAccessMapper.orderToOrderEntity(order)));*/
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderJPARepository.findById(orderId.getValue())
                .map(orderDataAccessMapper::orderEntityToOrder);
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJPARepository.findByTrackingId(trackingId.getValue())
                .map(orderDataAccessMapper::orderEntityToOrder);
    }
}
