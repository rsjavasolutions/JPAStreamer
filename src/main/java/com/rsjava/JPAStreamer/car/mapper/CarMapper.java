package com.rsjava.JPAStreamer.car.mapper;

import com.rsjava.JPAStreamer.car.CarEntity;
import com.rsjava.JPAStreamer.car.request.CarRequest;
import com.rsjava.JPAStreamer.car.response.CarResponse;

public class CarMapper {

    public static CarEntity mapToEntity(CarRequest request) {
        return new CarEntity(
                request.getBrand(),
                request.getModel(),
                request.getYear(),
                request.getPrice()
        );
    }

    public static CarResponse mapToResponse(CarEntity entity) {
        return new CarResponse(
                entity.getUuid(),
                entity.getId(),
                entity.getBrand(),
                entity.getModel(),
                entity.getYear(),
                entity.getPrice(),
                entity.getCreationDateTime());
    }
}
