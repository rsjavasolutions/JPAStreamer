package com.rsjava.JPAStreamer.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

    List<CarEntity> findAll();

    Optional<CarEntity> findByUuid(String uuid);

    void deleteByUuid(String uuid);
}
