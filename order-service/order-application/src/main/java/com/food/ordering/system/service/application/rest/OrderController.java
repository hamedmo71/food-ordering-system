package com.food.ordering.system.service.application.rest;

import com.food.ordering.system.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.service.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.service.domain.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/vmd.api.v1+json")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand command) {
        log.info("Creating order fo customer {} at restaurant {}", command.getCustomerId(), command.getRestaurantId());

        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(command);
        log.info("Order created with tracking id {}.", createOrderResponse.getOrderTrackingId());
        return new ResponseEntity<>(createOrderResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResponse> getOrderByTrackingId(@PathVariable("trackingId") UUID trackingId) {
        log.info("Retrieving order by tracking id {}.", trackingId);
        TrackOrderResponse trackOrderResponse = orderApplicationService.trackOrder(TrackOrderQuery.builder().orderTrackingId(trackingId).build());
        log.info("Order status with tracking id {} is {}.", trackingId, trackOrderResponse.getOrderStatus());
        return new ResponseEntity<>(trackOrderResponse, HttpStatus.OK);
    }
}
