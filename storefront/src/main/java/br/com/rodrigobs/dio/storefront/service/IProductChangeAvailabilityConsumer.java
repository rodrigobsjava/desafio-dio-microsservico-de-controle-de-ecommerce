package br.com.rodrigobs.dio.storefront.service;

import br.com.rodrigobs.dio.storefront.dto.StockStatusMessage;

public interface IProductChangeAvailabilityConsumer {
    void receive(final StockStatusMessage message);
}
