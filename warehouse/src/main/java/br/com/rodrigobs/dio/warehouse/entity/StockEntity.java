package br.com.rodrigobs.dio.warehouse.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class StockEntity {

    @Id
    private UUID id;

    private Long amount;

    private BigDecimal boughtPrice;

    @Enumerated(EnumType.STRING)
    private StockStatus status;

    private BigDecimal soldPrice;

    public void decAmount(){
        this.amount -= 1;
        if (this.amount == 0){
            this.status = StockStatus.UNAVAILABLE;
        }
    }

    public boolean inUnavailable(){
        return status == StockStatus.UNAVAILABLE;
    }

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getAmount(), that.getAmount()) && Objects.equals(getBoughtPrice(), that.getBoughtPrice()) && getStatus() == that.getStatus() && Objects.equals(getSoldPrice(), that.getSoldPrice()) && Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAmount(), getBoughtPrice(), getStatus(), getSoldPrice(), getProduct());
    }
    @PrePersist
    private void prePersiste(){
        this.id = UUID.randomUUID();
    }
}
