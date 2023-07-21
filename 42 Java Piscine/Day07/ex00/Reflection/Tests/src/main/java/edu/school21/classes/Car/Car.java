package edu.school21.classes.Car;
import java.util.StringJoiner;

public class Car {
    private String model;
    private Integer maxSpeed;
    private Integer price;

    public Car() {
        this.model = "Default model";
        this.maxSpeed = 200;
        this.price = 250;
    }
    public Car(String model, Integer maxSpeed, Integer price) {
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.price = price;
    }

    public int increaseCost(int value) {
        this.price += value;
        return price;
    }

    public String changeModel(String model) {
        this.model = model;
        return model;
    }

    public void test(Boolean flag, Long num, Integer minunum) {

    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                . add("model" + model + "'")
                . add("max speed='" + maxSpeed + "'")
                . add("price=" + price)
                . toString();
    }
}