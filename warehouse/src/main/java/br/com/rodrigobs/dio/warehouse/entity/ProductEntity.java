package br.com.rodrigobs.dio.warehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Getter
@Setter
@ToString
public class ProductEntity {

    @Id
    private UUID id;

    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StockEntity> stocks = ConcurrentHashMap.newKeySet();

    /**
     * Retorna o menor preço de venda entre os estoques disponíveis.
     * Se não houver estoque disponível, retorna BigDecimal.ZERO.
     */
    public BigDecimal getPrice() {
        return getStockWithMinSoldPrice()
                .map(StockEntity::getSoldPrice)
                .orElse(BigDecimal.ZERO);
    }

    /**
     * Decrementa o estoque do produto.
     * Lança exceção se não houver estoque disponível.
     */
    public StockEntity decStock() {
        var stock = getStockWithMinSoldPrice()
                .orElseThrow(() -> new IllegalStateException("Produto sem estoque disponível"));
        stock.decAmount();
        return stock;
    }

    /**
     * Retorna o estoque com menor preço de venda entre os disponíveis.
     */
    private Optional<StockEntity> getStockWithMinSoldPrice() {
        synchronized (this.stocks) {
            return this.stocks.stream()
                    .filter(s -> s.getStatus().equals(StockStatus.AVAILABLE))
                    .min(Comparator.comparing(StockEntity::getSoldPrice));
        }
    }

    @PrePersist
    private void prePersist() {
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
