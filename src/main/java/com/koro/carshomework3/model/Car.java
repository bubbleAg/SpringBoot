package com.koro.carshomework3.model;

import org.springframework.hateoas.RepresentationModel;

public class Car extends RepresentationModel {

    private int id;
    private String mark;
    private String model;
    private String color;

    public Car() {
    }

    public Car(int id, String mark, String model, String color) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.color = color;
    }

    public void modifyCar(Car newCar) {
        String newMark = newCar.getMark();
        String newModel = newCar.getModel();
        String newColor = newCar.getColor();

        if (newMark != null) {
            this.mark = newMark;
        }
        if (newModel != null) {
            this.model = newModel;
        }
        if (newColor != null) {
            this.color = newColor;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
