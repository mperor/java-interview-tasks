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
