package br.com.rodrigobs.dio.warehouse.mapper;

import br.com.rodrigobs.dio.warehouse.controller.request.StockSaveRequest;
import br.com.rodrigobs.dio.warehouse.controller.response.StockSavedResponse;
import br.com.rodrigobs.dio.warehouse.entity.StockEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface IStockMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "status", expression = "java(br.com.rodrigobs.dio.warehouse.entity.StockStatus.IN_CONFERENCE)")
    StockEntity toEntity (final StockSaveRequest request);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    StockSavedResponse toResponse (final StockEntity entity);
}
