package com.rsjava.JPAStreamer.car;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Setter(AccessLevel.NONE)
    private String uuid;
    private String brand;
    private String model;
    private Integer year;
    private BigDecimal price;
    @Setter(AccessLevel.NONE)
    private LocalDateTime creationDateTime;

    public CarEntity(String brand,
                     String model,
                     int year,
                     BigDecimal price) {
        this.uuid = UUID.randomUUID().toString();
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.creationDateTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarEntity carEntity = (CarEntity) o;
        return Objects.equals(uuid, carEntity.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

