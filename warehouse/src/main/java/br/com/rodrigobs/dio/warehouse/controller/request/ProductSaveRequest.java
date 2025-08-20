package br.com.rodrigobs.dio.warehouse.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductSaveRequest(
        @JsonProperty("name")
        String name
) {
}
