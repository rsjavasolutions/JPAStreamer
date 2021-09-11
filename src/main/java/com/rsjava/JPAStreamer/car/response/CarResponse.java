package com.rsjava.JPAStreamer.car.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CarResponse {
    private final String uuid;
    private final long id;
    private final String band;
    private final String model;
    private final int year;
    private final BigDecimal price;
    private final LocalDateTime creationDateTime;
}
