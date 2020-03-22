package com.koro.carshomework3.controller;

import com.koro.carshomework3.exception.CarNotFoundException;
import com.koro.carshomework3.model.Car;
import com.koro.carshomework3.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Car>> getCars() {
        List<Car> carList = carService.getCarList();
        carList.forEach(car -> car.add(linkTo(CarController.class).slash(car.getId()).withSelfRel()));
        Link link = linkTo(CarController.class).withSelfRel();
        CollectionModel<Car> carCollection = new CollectionModel<>(carList, link);
        return new ResponseEntity<>(carCollection, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Car>> getCarById(@PathVariable int id) {
        Car carById = carService.getCarById(id).orElseThrow(() -> new CarNotFoundException(id));
        Link link = linkTo(CarController.class).slash(id).withSelfRel();
        EntityModel<Car> carEntity = new EntityModel<>(carById, link);
        return new ResponseEntity<>(carEntity, HttpStatus.OK);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<CollectionModel<Car>> getCarsByColor(@PathVariable String color) {
        List<Car> carList = carService.getCarsByColor(color);
        carList.forEach(car -> car.add(linkTo(CarController.class).slash(car.getId()).withSelfRel()));
        carList.forEach(car -> car.add(linkTo(CarController.class).withRel("allColors")));
        Link link = linkTo(CarController.class).withSelfRel();
        CollectionModel<Car> carCollection = new CollectionModel<>(carList, link);
        return new ResponseEntity(carCollection, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity addCar(@RequestBody Car newCar) {
        carService.addCar(newCar);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity modifyCar(@RequestBody Car newCar) {
        carService.modifyCar(newCar);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity removeCar(@PathVariable int id) {
        carService.removeCar(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
