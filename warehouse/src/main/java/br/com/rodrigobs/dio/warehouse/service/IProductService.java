package br.com.rodrigobs.dio.warehouse.service;

import br.com.rodrigobs.dio.warehouse.entity.ProductEntity;

import java.util.UUID;

public interface IProductService {

    ProductEntity save (final ProductEntity entity);

    void purchase (final UUID id);
}
