package com.koro.carshomework3.service;

import com.koro.carshomework3.model.Car;
import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> getCarList();
    Optional<Car> getCarById(int id);
    List<Car> getCarsByColor(String color);
    void addCar(Car car);
    void modifyCar(Car car);
    void removeCar(int id);
}
