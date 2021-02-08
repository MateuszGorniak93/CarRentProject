package com.matix.carrent.repositories;

import com.matix.carrent.model.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
}
