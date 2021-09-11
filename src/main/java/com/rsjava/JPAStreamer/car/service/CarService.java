package com.rsjava.JPAStreamer.car.service;

import com.rsjava.JPAStreamer.car.CarEntity;
import com.rsjava.JPAStreamer.car.CarEntity$;
import com.rsjava.JPAStreamer.car.CarRepository;
import com.rsjava.JPAStreamer.car.exception.CarNotFoundException;
import com.rsjava.JPAStreamer.car.mapper.CarMapper;
import com.rsjava.JPAStreamer.car.request.CarRequest;
import com.rsjava.JPAStreamer.car.response.CarResponse;
import com.speedment.jpastreamer.application.JPAStreamer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rsjava.JPAStreamer.car.mapper.CarMapper.mapToEntity;
import static com.rsjava.JPAStreamer.car.mapper.CarMapper.mapToResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    private final JPAStreamer jpaStreamer;
    private final CarRepository carRepository;

    @Transactional
    public List<CarResponse> getCars(String model,
                                     BigDecimal priceMin,
                                     BigDecimal priceMax,
                                     Set<String> uuids
    ) {

        //carRepository.findAll()

        return jpaStreamer
                .stream(CarEntity.class)
                .filter(CarEntity$.brand.equalIgnoreCase(model)
                        .and(CarEntity$.price.between(priceMin, priceMax))
                        .and(CarEntity$.uuid.in(uuids)))
                .sorted(CarEntity$.brand)
                .map(CarMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CarResponse getCar(String uuid) {
        CarEntity carEntity = carRepository.findByUuid(uuid).orElseThrow(() -> new CarNotFoundException(uuid));
        return mapToResponse(carEntity);
    }

    @Transactional
    public Map<String, List<CarResponse>> getCarSGroupedByBrand() {
        return convertToResponsesMap(
                jpaStreamer
                        .stream(CarEntity.class)
                        .collect(Collectors.groupingBy(CarEntity::getBrand)));
    }

    private Map<String, List<CarResponse>> convertToResponsesMap(Map<String, List<CarEntity>> carEntitiesGroupedBrand) {
        Map<String, List<CarResponse>> brandToCarResponse = new HashMap<>();

        for (Map.Entry<String, List<CarEntity>> entry : carEntitiesGroupedBrand.entrySet()) {
            brandToCarResponse.put(
                    entry.getKey(),
                    entry.getValue()
                            .stream()
                            .map(CarMapper::mapToResponse)
                            .collect(Collectors.toList()));
        }
        return brandToCarResponse;
    }

    @Transactional
    public String saveCar(CarRequest request) {
        log.debug("Save car request with params: {}", request);

        return carRepository.save(mapToEntity(request)).getUuid();
    }

    @Transactional
    public CarResponse updateCar(String uuid, CarRequest request) {
        CarEntity carEntity = carRepository.findByUuid(uuid).orElseThrow(() -> new CarNotFoundException(uuid));

        carEntity.setBrand(request.getBrand());
        carEntity.setModel(request.getModel());

        return mapToResponse(carEntity);
    }

    @Transactional
    public void deleteCar(String uuid) {
        carRepository.deleteByUuid(uuid);
    }
}
