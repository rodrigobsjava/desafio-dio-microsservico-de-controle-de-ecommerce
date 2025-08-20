package br.com.rodrigobs.dio.storefront.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDetailsDTO(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name,
        @JsonProperty("price")
        BigDecimal price
) {
}
