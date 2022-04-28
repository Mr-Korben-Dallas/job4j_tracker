package ru.job4j.stream;

import java.time.LocalDate;

public class Car {
    private String brand;

    private String model;

    private LocalDate created;

    private double volume;

    private String color;

    static class Builder {
        private String brand;
        private String model;
        private LocalDate created;
        private double volume;
        private String color;

        Builder buildBrand(String brand) {
            this.brand = brand;
            return this;
        }

        Builder buildModel(String model) {
            this.model = model;
            return this;
        }

        Builder buildCreated(LocalDate created) {
            this.created = created;
            return this;
        }

        Builder buildVolume(double volume) {
            this.volume = volume;
            return this;
        }

        Builder buildColor(String color) {
            this.color = color;
            return this;
        }

        Car build() {
            Car car = new Car();
            car.brand = brand;
            car.color = color;
            car.created = created;
            car.model = model;
            car.volume = volume;
            return car;
        }
    }

    @Override
    public String toString() {
        return "Car{"
                + "brand='"
                + brand
                + '\''
                + ", model='"
                + model
                + '\''
                + ", created="
                + created
                + ", volume="
                + volume
                + ", color='"
                + color
                + '\''
                + '}';
    }

    public static void main(String[] args) {
        Car firstCar = new Builder()
                .buildBrand("Toyota")
                .buildModel("Camry")
                .buildCreated(LocalDate.of(2021, 6, 1))
                .buildVolume(2.5)
                .buildColor("Red")
                .build();
        System.out.println(firstCar);
        Car secondCar = new Builder()
                .buildBrand("VAZ")
                .buildVolume(1.6)
                .buildColor("Black")
                .build();
        System.out.println(secondCar);
    }
}