package com.food.ordering.system.application.handler;

import lombok.Builder;

@Builder
public record ErrorDTO(String errorCode, String message) {
}
