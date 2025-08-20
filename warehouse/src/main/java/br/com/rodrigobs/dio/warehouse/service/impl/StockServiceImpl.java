package br.com.rodrigobs.dio.warehouse.service.impl;

import br.com.rodrigobs.dio.warehouse.dto.StockStatusMessage;
import br.com.rodrigobs.dio.warehouse.entity.StockEntity;
import br.com.rodrigobs.dio.warehouse.entity.StockStatus;
import br.com.rodrigobs.dio.warehouse.repository.StockRepository;
import br.com.rodrigobs.dio.warehouse.service.IProductChangeAvailabilityProducer;
import br.com.rodrigobs.dio.warehouse.service.IProductQueryService;
import br.com.rodrigobs.dio.warehouse.service.IStockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class StockServiceImpl implements IStockService {

    private final StockRepository repository;
    private final IProductQueryService productQueryService;
    private final IProductChangeAvailabilityProducer producer;

    @Override
    public StockEntity save(StockEntity entity) {
        entity.setProduct(productQueryService.findById(entity.getProduct().getId()));
        return repository.save(entity);
    }

    @Override
    public void release(UUID id) {
        changeStatus(id, StockStatus.AVAILABLE);
    }

    @Override
    public void inactive(UUID id) {
        changeStatus(id, StockStatus.UNAVAILABLE);
    }

    @Override
    public void changeStatus(UUID id, StockStatus status) {
        var entity = repository.findById(id).orElseThrow();
        entity.setStatus(status);
        repository.save(entity);
        producer.notifyStatusChange(new StockStatusMessage(entity.getProduct().getId(), status));
    }
}
