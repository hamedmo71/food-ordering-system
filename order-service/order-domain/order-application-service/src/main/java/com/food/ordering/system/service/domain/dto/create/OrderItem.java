package com.food.ordering.system.service.domain.dto.create;

import com.food.ordering.system.domain.valueobject.ProductId;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class OrderItem {

    @NotNull
    private final ProductId productId;
    @NotNull
    private final Integer quantity;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final BigDecimal subtotal;
}
