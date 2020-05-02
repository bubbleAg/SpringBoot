package com.koro.carshomework3.controller;

import com.koro.carshomework3.model.Car;
import com.koro.carshomework3.service.CarService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CarControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CarService carService;

    private List<Car> carList = new ArrayList<>();

    @BeforeEach
    void setCars(){
        carList.clear();
        carList.add(new Car(1L, "Audi", "A6", "black"));
        carList.add(new Car(2L, "Volkswagen", "Polo", "red"));
        carList.add(new Car(3L, "Skoda", "Superb", "red"));

        ReflectionTestUtils.setField(carService, "carList", carList);
    }

    @Test
    void should_get_all_cars() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.carList", hasSize(carList.size())));
    }

    @Test
    void should_get_car_by_id() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    }

    @Test
    void should_return_message_that_car_not_found() throws Exception {
        String expectedMessage = "Could not find car with id: 100";

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/{id}", 100))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is(expectedMessage)));
    }

    @Test
    void should_get_cars_with_specified_color() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/color/{color}", "red"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.carList", hasSize(2)));
    }

    @Test
    void should_add_new_car() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cars/add")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("{ \"id\": \"5\"," +
                                                        "\"mark\" : \"Fiat\"," +
                                                        "\"model\" : \"Stilo\"," +
                                                        "\"color\" : \"blue\"" +
                                                        "}"))
                .andExpect(status().isCreated());
        assertTrue(carService.getCarById(5).isPresent());
    }

    @Test
    void should_modify_car() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": \"1\"," +
                        "\"mark\" : \"Audi\"," +
                        "\"model\" : \"A5\"," +
                        "\"color\" : \"blue\"" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    void should_remove_car() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cars/delete/{id}", 1))
                .andExpect(status().isOk());
        assertFalse(carService.getCarById(1).isPresent());
    }
}