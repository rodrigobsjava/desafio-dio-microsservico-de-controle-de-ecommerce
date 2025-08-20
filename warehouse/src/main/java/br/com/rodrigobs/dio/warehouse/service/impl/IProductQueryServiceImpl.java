package br.com.rodrigobs.dio.warehouse.service.impl;

import br.com.rodrigobs.dio.warehouse.entity.ProductEntity;
import br.com.rodrigobs.dio.warehouse.repository.ProductRepository;
import br.com.rodrigobs.dio.warehouse.service.IProductQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class IProductQueryServiceImpl implements IProductQueryService {
    private final ProductRepository repository;


    @Override
    @Transactional(readOnly = true)
    public ProductEntity findById(UUID id) {
        return repository.findById(id).orElseThrow();
    }
}
