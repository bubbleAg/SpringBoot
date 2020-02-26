package com.koro.carshomework3.controller;

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
@RequestMapping(value = "/cars",
        produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
        )
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Car>> getCars() {
        List<Car> carList = carService.getCarsList();
        carList.forEach(car -> car.add(linkTo(CarController.class).slash(car.getId()).withSelfRel()));
        Link link = linkTo(CarController.class).withSelfRel();
        CollectionModel<Car> carCollection = new CollectionModel<>(carList, link);
        return new ResponseEntity<>(carCollection, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Car>> getCarById(@PathVariable int id) {
        Link link = linkTo(CarController.class).slash(id).withSelfRel();
        Car carById = carService.getCarById(id);
        EntityModel<Car> carEntity = new EntityModel<>(carById, link);
        if (carById != null) {
            return new ResponseEntity<>(carEntity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car newCar) {
        if (carService.addCar(newCar)) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity modifyWholeCar(@RequestBody Car newCar) {
        if (carService.modifyWholeCar(newCar)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @PatchMapping
    public ResponseEntity modifyCar(@RequestBody Car newCar) {
        if (carService.modifyCar(newCar)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeCar(@PathVariable int id) {
        if(carService.removeCar(id)){
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

}
