package br.com.rodrigobs.dio.warehouse.service;

import br.com.rodrigobs.dio.warehouse.entity.StockEntity;
import br.com.rodrigobs.dio.warehouse.entity.StockStatus;

import java.util.UUID;

public interface IStockService {
    StockEntity save(final StockEntity entity);

    void release (final UUID id);

    void inactive (final UUID id);

    void changeStatus (final UUID id, StockStatus status);
}
