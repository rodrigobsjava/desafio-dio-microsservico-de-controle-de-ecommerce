package br.com.rodrigobs.dio.warehouse.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record StockSaveRequest(
        @JsonProperty("amount")
        Long amount,

        @JsonProperty("boughtPrice")
        BigDecimal boughtPrice,

        @JsonProperty("soldPrice")
        BigDecimal soldPrice,

        @JsonProperty("productId")
        UUID productId
) {
}
