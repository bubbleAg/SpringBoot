package com.koro.carshomework3.service;

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
        carList.add(new Car(1, "Audi", "A6", "black"));
        carList.add(new Car(2, "Volkswagen", "Polo", "red"));
        carList.add(new Car(3, "Skoda", "Superb", "red"));
    }

    @Override
    public List<Car> getCarsList() {
        return carList;
    }

    @Override
    public Car getCarById(int id) {
        Optional<Car> optionalCar = carList.stream().filter(car -> car.getId() == id).findFirst();
        if (optionalCar.isPresent()) {
            return optionalCar.get();
        }
        return null;
    }

    @Override
    public List<Car> getCarsByColor(String color) {
        return carList.stream().filter(car -> car.getColor().equals(color)).collect(Collectors.toList());
    }

    @Override
    public boolean addCar(Car car) {
        if (getCarById(car.getId()) != null) {
            return false;
        }
        carList.add(car);
        return true;
    }

    @Override
    public boolean modifyWholeCar(Car newCar) {
        Car foundCar = getCarById(newCar.getId());
        if (foundCar != null) {
            carList.remove(foundCar);
            carList.add(newCar);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyCar(Car newCar) {
        Car foundCar = getCarById(newCar.getId());
        if (foundCar != null) {
            foundCar.modifyCar(newCar);
            return true;
        }
        return false;
    }


    @Override
    public boolean removeCar(int id) {
        Car foundCar = getCarById(id);
        if (foundCar != null) {
            carList.remove(foundCar);
            return true;
        }
        return false;
    }
}
