package br.com.rodrigobs.dio.warehouse.mapper;

import br.com.rodrigobs.dio.warehouse.controller.request.ProductSaveRequest;
import br.com.rodrigobs.dio.warehouse.controller.response.ProductDetailResponse;
import br.com.rodrigobs.dio.warehouse.controller.response.ProductSavedResponse;
import br.com.rodrigobs.dio.warehouse.dto.ProductStorefrontSaveDTO;
import br.com.rodrigobs.dio.warehouse.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import br.com.rodrigobs.dio.warehouse.entity.StockStatus;

@Mapper(componentModel = SPRING)
public interface IProductMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stocks", ignore = true)
    ProductEntity toEntity(ProductSaveRequest request);

    ProductSavedResponse toSavedResponse(final ProductEntity entity);

    @Mapping(target = "price", expression = "java(entity.getPrice())")
    @Mapping(
            target = "active",
            expression = "java(entity.getStocks().stream().anyMatch(s -> s.getStatus().equals(br.com.rodrigobs.dio.warehouse.entity.StockStatus.AVAILABLE)))"
    )
    ProductStorefrontSaveDTO toDTO(ProductEntity entity);

    default Boolean isActive(ProductEntity entity) {
        synchronized (entity.getStocks()) {
            return entity.getStocks().stream()
                    .anyMatch(s -> s.getStatus().equals(StockStatus.AVAILABLE));
        }
    }

    ProductDetailResponse toDetailResponse(final ProductEntity entity);
}
