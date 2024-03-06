package pl.mperor.interview.tasks.car;

import java.util.Arrays;
import java.util.Comparator;


public class Car {

    int maxSpeed;
    int acceleration;

    public Car(int maxSpeed, int acceleration) {
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
    }

    @Override
    public String toString() {
        return "Car with maxSpeed=" + maxSpeed + " and acceleration=" + acceleration;
    }

    public static Car getMax(Car[] cars) {
        return Arrays.stream(cars)
                .max(Comparator.comparingInt((Car car) -> car.maxSpeed)
                        .thenComparing((Car car) -> car.acceleration))
                .orElseThrow(() -> new IllegalArgumentException("No cars available!"));
    }
}

