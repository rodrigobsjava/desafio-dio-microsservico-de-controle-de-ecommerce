package br.com.rodrigobs.dio.storefront.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    private UUID id;

    private String name;

    private Boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntity that)) return false;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getActive(), that.getActive());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getActive());
    }
}
