package br.com.rodrigobs.dio.warehouse.service;

import br.com.rodrigobs.dio.warehouse.entity.ProductEntity;

import java.util.UUID;

public interface IProductQueryService {
    ProductEntity findById (final UUID id);
}
