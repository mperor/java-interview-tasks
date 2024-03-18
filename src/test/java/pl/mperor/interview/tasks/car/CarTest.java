package pl.mperor.interview.tasks.car;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Class Car
 *
 * <p>This class represents a Car with a maxSpeed and acceleration.
 *
 * <p>A car is "greater" than another car when its maxSpeed is greater. If maxSpeeds are equal the greater car is the one with the greater acceleration.</p>
 *
 * <p><i>Method: getMax</i>
 * This static method accepts an array of Car and returns the greatest element of this array.
 *
 * <p>In case if the input array is empty, it throws IllegalStatmentException with a message "Bad array size".</p>
 * <p>In case if the input array is null, it throws RuntimeException with a message "Input array should not be null".</p>
 *
 * <pre><code>
 * public class Car {
 *   public static Car getMax(Car[] cars){
 *     // code here
 *   }
 *   //// DO NOT MODIFY ANYTHING BELOW THIS LINE
 *   int maxSpeed;
 *   int acceleration;
 *   public Car(int maxSpeed, int acceleration){
 *     this.maxSpeed = maxSpeed;
 *     this.acceleration = acceleration;
 *   }
 *
 *   public String toString() {
 *     return "Car with maxSpeed=" + maxSpeed + " and acceleration=" + acceleration;
 *   }
 * }
 * </code></pre>
 */
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