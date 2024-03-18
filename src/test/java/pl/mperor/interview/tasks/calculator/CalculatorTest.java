package pl.mperor.interview.tasks.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.interview.tasks.calculator.instruction.CalculatorInstruction;
import pl.mperor.interview.tasks.calculator.instruction.InstructionParser;

import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Calculator Instructions 🧮
 * <p><strong>Problem: Calculator</strong></p>
 *
 * <p>Write some code to calculate a result from a set of instructions.
 * Instructions comprise of a keyword and a number that are separated by a space per line.
 * Instructions are loaded from the file and results are output to the screen.
 * Any number of Instructions can be specified. Instructions can be any binary operators of your choice (e.g., add, divide, subtract, multiply etc).
 * The instructions will ignore mathematical precedence.
 * The last instruction should be “apply” and a number (e.g., “apply 3”).
 * The calculator is then initialised with that number and the previous instructions are applied to that number.</p>
 *
 *
 * <p>Examples of the calculator lifecycle:
 *
 * <ul>
 *     <li><b>Example 1</b>
 *          <p>Input from file: add 2, multiply 3, apply 10
 *          <p>Output: 36</p>
 *          <p>Explanation: 10 + 2 * 3 = 36</p>
 *     </li>
 *     <li><b>Example 2</b>
 *          <p>Input from file: multiply 3, add 2, apply 10</p>
 *          <p>Output: 32</p>
 *          <p>Explanation: 10 * 3 + 2 = 32</p>
 *     </li>
 *     <li><b>Example 3</b>
 *          <p>Input from file: apply 1</p>
 *          <p>Output: 1</p>
 *     </li>
 * </ul>
 */
public class CalculatorTest {

    private final InstructionParser parser = new InstructionParser();

    private Function<String, List<CalculatorInstruction>> textStoredInstructionsLoader = input ->
            parser.parse(Arrays.stream(input.split("\r\n")).toList());

    private Function<File, List<CalculatorInstruction>> fileStoredInstructionsLoader = file -> {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            return parser.parse(lines);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    private CalculatorCreator calculatorCreator = new CalculatorCreator();

    @Test
    public void calculateExample1FromText() {
        String input = "add 2\r\n" +
                "multiply 3\r\n" +
                "apply 10";

        List<CalculatorInstruction> instructions = textStoredInstructionsLoader.apply(input);
        CalculatorEngine calculator = calculatorCreator.create(instructions);
        Assertions.assertEquals(new BigDecimal(36), calculator.compute());
    }

    @Test
    public void calculateExample1FromFileCalledExample1Txt() throws URISyntaxException {
        List<CalculatorInstruction> instructions = fileStoredInstructionsLoader.apply(new File(getClass().getClassLoader().getResource("example1.txt").toURI()));
        CalculatorEngine calculator = calculatorCreator.create(instructions);
        Assertions.assertEquals(new BigDecimal(36), calculator.compute());
    }

    @Test
    public void calculateExample2StoredInTemporaryFile() {
        String input = "multiply 3\r\n" +
                "add 2\r\n" +
                "apply 10";

        List<CalculatorInstruction> instructions = fileStoredInstructionsLoader.apply(createTmpFileWithText(input));
        CalculatorEngine calculator = calculatorCreator.create(instructions);
        Assertions.assertEquals(new BigDecimal(32), calculator.compute());
    }

    @Test
    public void calculateExample3FromText() {
        String input = "apply 10";

        List<CalculatorInstruction> instructions = textStoredInstructionsLoader.apply(input);
        CalculatorEngine calculator = calculatorCreator.create(instructions);
        Assertions.assertEquals(new BigDecimal(10), calculator.compute());
    }

    private File createTmpFileWithText(String text) {
        try {
            File tmp = File.createTempFile("temp.txt", null);
            PrintWriter pw = new PrintWriter(tmp);
            pw.print(text);
            pw.close();
            return tmp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
