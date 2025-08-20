package br.com.rodrigobs.dio.warehouse.controller.response;

import br.com.rodrigobs.dio.warehouse.entity.StockStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductSavedResponse(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name
) {
}
