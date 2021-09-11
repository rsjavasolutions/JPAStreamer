package com.rsjava.JPAStreamer.car;

import com.rsjava.JPAStreamer.car.request.CarRequest;
import com.rsjava.JPAStreamer.car.response.CarResponse;
import com.rsjava.JPAStreamer.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public CarResponse getCar(@PathVariable String uuid) {
        return carService.getCar(uuid);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CarResponse> getCars(@RequestParam(value = "brand") String brand,
                                     @RequestParam(value = "princeMin") BigDecimal priceMin,
                                     @RequestParam(value = "princeMax") BigDecimal priceMax,
                                     @RequestParam(value = "uuids") Set<String> uuids
    ) {
        return carService.getCars(brand, priceMin, priceMax, uuids);
    }

    @GetMapping("grouped")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<CarResponse>> getCarSGroupedByBrand() {
        return carService.getCarSGroupedByBrand();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveCar(@RequestBody @Valid CarRequest request) {
        return carService.saveCar(request);
    }

    @PutMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public CarResponse updateCar(@PathVariable String uuid,
                                 @RequestBody CarRequest request) {
        return carService.updateCar(uuid, request);
    }

    @DeleteMapping("{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable String uuid) {
        carService.deleteCar(uuid);
    }
}
