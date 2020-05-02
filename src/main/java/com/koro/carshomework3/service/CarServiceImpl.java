package com.koro.carshomework3.service;

import com.koro.carshomework3.exception.CarAlreadyExistsException;
import com.koro.carshomework3.exception.CarNotFoundException;
import com.koro.carshomework3.model.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private List<Car> carList;

    public CarServiceImpl() {
        carList = new ArrayList<>();
        setDefaultCars();
    }

    private void setDefaultCars() {
//        carList.add(new Car(1L, "Audi", "A6", "black"));
//        carList.add(new Car(2L, "Volkswagen", "Polo", "red"));
//        carList.add(new Car(3L, "Skoda", "Superb", "red"));
    }

    @Override
    public List<Car> getCarList() {
        return carList;
    }

    @Override
    public Optional<Car> getCarById(long id) {
        Optional<Car> optionalCar = carList.stream().filter(car -> car.getId() == id).findFirst();
        return optionalCar;
    }

    @Override
    public List<Car> getCarsByColor(String color) {
        return carList.stream().filter(car -> car.getColor().equals(color)).collect(Collectors.toList());
    }

    @Override
    public void addCar(Car car) {
        getCarById(car.getId()).ifPresent(c -> {
            throw new CarAlreadyExistsException(car.getId());
        });
        carList.add(car);
    }

    @Override
    public void modifyCar(Car newCar) {
        Car foundCar = getCarById(newCar.getId()).orElseThrow(() -> new CarNotFoundException(newCar.getId()));
        foundCar.modifyCar(newCar);
    }


    @Override
    public void removeCar(long id) {
        Car foundCar = getCarById(id).orElseThrow(() -> new CarNotFoundException(id));
        carList.remove(foundCar);
    }
}
