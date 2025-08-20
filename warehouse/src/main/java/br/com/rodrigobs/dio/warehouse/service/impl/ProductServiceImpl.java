package br.com.rodrigobs.dio.warehouse.service.impl;

import br.com.rodrigobs.dio.warehouse.dto.ProductStorefrontSaveDTO;
import br.com.rodrigobs.dio.warehouse.entity.ProductEntity;
import br.com.rodrigobs.dio.warehouse.entity.StockEntity;
import br.com.rodrigobs.dio.warehouse.mapper.IProductMapper;
import br.com.rodrigobs.dio.warehouse.repository.ProductRepository;
import br.com.rodrigobs.dio.warehouse.service.IProductQueryService;
import br.com.rodrigobs.dio.warehouse.service.IProductService;
import br.com.rodrigobs.dio.warehouse.service.IStockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository repository;
    private final IProductQueryService productQueryService;
    private final IStockService stockService;
    private final RestClient storefrontClient;
    private final IProductMapper mapper;

    @Override
    public ProductEntity save(ProductEntity entity) {
        repository.save(entity);
        var dto = mapper.toDTO(entity);
        saveStorefront(dto);
        return entity;
    }

    @Override
    public void purchase(UUID id) {
        var entity = productQueryService.findById(id);
        StockEntity stock;

        synchronized (entity.getStocks()) {
            stock = entity.decStock();
            repository.save(entity);
        }

        if (stock.inUnavailable()) {
            stockService.changeStatus(stock.getId(), stock.getStatus());
        }
    }

    private void saveStorefront(ProductStorefrontSaveDTO dto) {
    storefrontClient.post()
            .uri("/products")
            .body(dto)
            .retrieve()
            .body(ProductStorefrontSaveDTO.class);
    }
}
