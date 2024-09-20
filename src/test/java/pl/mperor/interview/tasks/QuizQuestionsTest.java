package pl.mperor.interview.tasks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class QuizQuestionsTest {

    private PrintStream original = System.out;
    private ByteArrayOutputStream systemOut;

    @BeforeEach
    void setUp() {
        systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
    }

    @AfterEach
    void tearDown() {
        System.setOut(original);
    }

    @Test
    public void testForceGarbageCollector() {
        //How can you force garbage collection to run immediately?
        //Select one:
        //a. Runtime.gc()
        //b. Both Runtime.gc() and System.gc()
        //c. System.gc()
        //d. Garbage collection cannot be forced to run immediately

        long beforeGarbageCalled = Runtime.getRuntime().freeMemory();

        System.gc();
        // same as
        //Runtime.getRuntime().gc();

        long afterGarbageCalled = Runtime.getRuntime().freeMemory();

        assertTrue(beforeGarbageCalled >= afterGarbageCalled);
    }

    @Test
    public void testArrayBehaviour() {
        //What is the result of:
        //int array[] = new int[] {1,2,3};
        //System.out.print();(array[3]);
        //Select one:
        //а. 3
        //b. NAN
        //c. java.lang.ArraylllegallndexException
        //d. java.lang.ArrayIndexOutOfBoundsException

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            int array[] = new int[]{1, 2, 3};
            int outOfBound = array[3];
        });
    }

    @Test
    public void compareStrings() {
        //What is the result of the logic statement:
        //String a = new String("Abc");
        //String b = new String("Abc");
        //System.out.print();(a == b);
        //Select one:
        //a. True
        //b. False

        String a = new String("Abc");
        String b = new String("Abc");

        // equals vs '=='
        assertTrue("Abc" == "Abc");
        assertFalse(a == b);

        assertTrue("Abc".equals("Abc"));
        assertTrue(a.equals(b));
    }

    @Test
    public void implementComparable() {
        //When implementing Comparable interface, a.compareTo(b) should return? Select one:
        //a. Any positive number when a < b, exactly 0 when a = b, any negative number when a ≥ b
        //b. Exactly: -1 when a < b, 0 when a = b, 1 when a>b*
        //c. Exactly: 1 when a < b, 0 when a = b, -1 when a =b
        //d. Any negative number when a < b, exactly 0 when a = b, any positive number when a ≥ b

        Comparator<Integer> comparator = QuizQuestionsTest::compare;
        assertEquals(-1, comparator.compare(1, 2));
        assertEquals(0, comparator.compare(1, 1));
        assertEquals(1, comparator.compare(2, 1));

        Comparable<Integer> comparableZero = Integer.valueOf(0)::compareTo;
        // under the hood 'compareTo' uses a static method: Integer.compare(a, b);
        assertEquals(-1, comparableZero.compareTo(1));
        assertEquals(0, comparableZero.compareTo(0));
        assertEquals(1, comparableZero.compareTo(-1));
    }

    private static int compare(int a, int b) {
        // same as
        // Integer.compare(a, b);
        return (a < b) ? -1 : ((a == b) ? 0 : 1);
    }

    @Test
    public void testDefaultBlockExecutionOfSwitch() {
        //The default statement of a switch is always executed.
        //Select one: 
        //a. True 
        //b. False 

        switch (0) {
            case 0:
                break;
            default:
                fail("Default block was called!");
        }
    }

    @Test
    public void testArrayListOfString() {
        // Considering that "f" is an ArrayList of Strings is code below correct?
        // for (String c : f){
        // …..
        //}
        //Select one:
        //a. True
        //b. False

        assertDoesNotThrow(() -> {
            var f = Arrays.asList("a", "b", "c");
            for (String c : f) {
            }
        });
    }

    @Test
    public void testPolymorphism() {
        //Is everything ok with the code below?
        class Car {
            // final means cannot override run()
            // compilation error
            void run() {
                System.out.print("running at 100kmph");
            }
        }
        class Ferrari extends Car {
            void run() {
                System.out.print("running at 200kmph");
            }
        }

        Ferrari f = new Ferrari();
        f.run();

        assertEquals("running at 200kmph", systemOut.toString());
    }

    @Test
    public void testSummingTwoIntegerFromArrays() {
        //What would display from the following statements?
        int[] nums = {1, 2, 3, 4, 5, 6};
        System.out.print(nums[1] + nums[3]);
        //Select one:
        //a. 4
        //b. 1+3
        //c. 2+4
        //d. 6

        assertEquals(6, nums[1] + nums[3]);
        assertEquals("6", systemOut.toString());
    }

    @Test
    public void testCreatingInstanceOfObject() {
        //What is created by code below?
        Object object = new Object();
        //Select one:
        //a. Function
        //b. Object definition
        //c. Object object
        //d. Object class

        assertInstanceOf(Object.class, object);
        assertInstanceOf(Class.class, object.getClass());
    }

    @Test
    public void testMethodInheritanceAndOverriding() {
        // What will be the output of?
        class Base {
            void method(int a) {
                System.out.print("Base class with integer a = " + a);
            }

            void method(double d) {
                System.out.print("Base class with double d = " + d);
            }
        }
        class Child extends Base {
            @Override
            void method(double d) {
                System.out.print("Child class with double d = " + d);
            }
        }

        new Child().method(100);
        assertEquals("Base class with integer a = 100", systemOut.toString());
    }

    @Test
    public void testSwapTwoNumbersWithoutUsingAThirdVariable() {
        // How do you swap two numbers without using a third variable in Java??
        int a = 1;
        int b = 2;
        b = b + a; // now b is sum of both the numbers
        a = b - a; // (b + a) - a = b (a is swapped)
        b = b - a; // (b + a) - b = a (b is swapped)
        assertEquals(2, a);
        assertEquals(1, b);
    }

    @Test
    public void testDivisionByZero() {
        // What will be the output of the following Java statement?
        double a = 3.0 / 0;
        double b = 0 / 4.0;
        double c = 0 / 0.0;

        // double vs int
        assertTrue(Double.isInfinite(a));
        assertEquals(0, b);
        assertTrue(Double.isNaN(c));

        assertThrows(ArithmeticException.class, () -> {
            int ai = 3 / 0;
        });
        assertEquals(0, 0 / 4);
        assertThrows(ArithmeticException.class, () -> {
            int ci = 0 / 0;
        });
    }

    @Test
    public void testAlphabetRangeInAsciiTable() {
        // What is the range of numbers in the ASCII table for the alphabet?
        assertEquals(65, 'A');
        assertEquals(90, 'Z');

        assertEquals(97, 'a');
        assertEquals(122, 'z');

        assertEquals(26, IntStream.rangeClosed('a', 'z').count());
    }

    @Test
    public void testDoubleValuePrecision() {
        // What is the advantage of BigDecimal over double?

        // BigDecimal offers more precision with control over rounding, but double is faster in terms of performance.
        assertEquals(new BigDecimal("0.01"), new BigDecimal("0.03").subtract(new BigDecimal("0.02")));
        assertNotEquals(0.01, 0.3, 0.2);
    }

}
