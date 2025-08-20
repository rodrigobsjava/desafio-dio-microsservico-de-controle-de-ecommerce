package br.com.rodrigobs.dio.warehouse.service;

import br.com.rodrigobs.dio.warehouse.dto.StockStatusMessage;
import br.com.rodrigobs.dio.warehouse.entity.StockStatus;

public interface IProductChangeAvailabilityProducer {
    void notifyStatusChange(final StockStatusMessage message);
}
