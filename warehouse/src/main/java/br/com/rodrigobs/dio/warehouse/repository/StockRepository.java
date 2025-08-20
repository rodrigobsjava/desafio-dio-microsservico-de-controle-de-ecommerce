package br.com.rodrigobs.dio.warehouse.repository;

import br.com.rodrigobs.dio.warehouse.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, UUID> {
}
