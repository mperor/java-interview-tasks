package pl.mperor.interview.tasks.car;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CarTest {

    @Test
    void getMax_theFastestCarExisting_returnsCarWithMaxSpeed() {
        Car fastest = new Car(200, 20);
        Car actualMax = Car.getMax(new Car[]{
                new Car(150, 30),
                fastest,
                new Car(120, 50),
        });

        Assertions.assertSame(fastest, actualMax);
    }

    @Test
    void getMax_carWithGreaterAccelerationExisting_returnsCarWithMaxAcceleration() {
        Car greaterAccelerationCar = new Car(200, 100);
        Car[] sameSpeedCars = {
                greaterAccelerationCar,
                new Car(200, 50)
        };
        Car actualMax = Car.getMax(sameSpeedCars);

        Assertions.assertSame(greaterAccelerationCar, actualMax);
    }
}