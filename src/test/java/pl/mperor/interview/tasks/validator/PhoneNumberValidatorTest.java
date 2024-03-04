package pl.mperor.interview.tasks.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

class PhoneNumberValidatorTest {


    /**
     * Task: Phone Number Validation 📞
     * <p>
     * Description:
     * Your task is to create an application for validating phone numbers in Java.
     * The application should take a phone phoneNumber as input and check if it is valid according to specified criteria.
     * <p>
     * Requirements:
     * Validation Method: Create a method that takes a phone phoneNumber as a parameter and returns a boolean value (true/false) depending on the validity of the phoneNumber.
     * Conditions: The phone phoneNumber should meet the following conditions:
     * <p>
     * Consist of exactly 9 digits.
     * May contain only digits from 0 to 9.
     * Example:
     * For the phone phoneNumber "123456789":
     * Calling the method validatePhoneNumber("123456789") should return true.
     * For the phone phoneNumber "9876543210":
     * Calling the method validatePhoneNumber("9876543210") should return false because this phoneNumber contains more than 9 digits.
     * <p>
     * Additional Points:
     * Support for different phone phoneNumber formats (e.g., including hyphens, parentheses, or other separators).
     * Implementation of a simple user interface allowing to enter a phone phoneNumber and display the validation result.
     * <p>
     * Expected Points:
     * Code Quality: Is the code readable, well-formatted, and understandable?
     * Correctness: Does the application correctly validate phone numbers according to the requirements?
     * Flexibility: Does the application support different phone phoneNumber formats?
     */
    private final static Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^[0-9]{3}[-\\s]?[0-9]{3}[-\\s]?[0-9]{3}$");

    @Test
    void testValidate_incorrectPhoneNumbers_returnsFalse() {
        Predicate<String> matcher = PHONE_NUMBER_PATTERN.asMatchPredicate();

        // empty or null
        //Assertions.assertFalse(matcher.test(null));
        Assertions.assertFalse(validate(null));
        Assertions.assertFalse(matcher.test(""));
        Assertions.assertFalse(validate(""));

        // not a number
        Assertions.assertFalse(matcher.test("aaabbbccc"));
        Assertions.assertFalse(validate("aaabbbccc"));

        // greater than 9 numbers
        Assertions.assertFalse(matcher.test("9876543210"));
        Assertions.assertFalse(validate("9876543210"));

        // lower than 9 numbers
        Assertions.assertFalse(matcher.test("11122233"));
        Assertions.assertFalse(validate("11122233"));

        // phone number with spaces after two digits
        Assertions.assertFalse(matcher.test("11 12 22 33 1"));
        //Assertions.assertTrue(validate("11 12 22 33 1"));

        // phone number with other separator "_"
        Assertions.assertFalse(matcher.test("111_222_333"));
        //Assertions.assertTrue(validate("111_222_333"));
    }

    @Test
    void testValidate_correctPhoneNumbers_returnsTrue() {
        Predicate<String> matcher = PHONE_NUMBER_PATTERN.asMatchPredicate();

        // phone number with spaces
        Assertions.assertTrue(matcher.test("123 123 123"));
        Assertions.assertTrue(validate("123 123 123"));

        // phone number with dashes
        Assertions.assertTrue(matcher.test("111-222-333"));
        Assertions.assertTrue(validate("111-222-333"));

        // phone number with exact 9 numbers
        Assertions.assertTrue(matcher.test("123456789"));
        Assertions.assertTrue(validate("123456789"));
    }

    private static boolean validate(String input) {
        return Optional.ofNullable(input)
                .map(s -> s.replaceAll("\\D", ""))
                .stream()
                .anyMatch(s1 -> s1.length() == 9);
    }

}