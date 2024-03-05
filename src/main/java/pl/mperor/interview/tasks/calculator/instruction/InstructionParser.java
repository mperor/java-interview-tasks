package pl.mperor.interview.tasks.calculator.instruction;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class InstructionParser {

    public List<CalculatorInstruction> parse(List<String> lines) {
        return lines.stream()
                .map(line -> line.split("\\s"))
                .map(split -> new CalculatorInstruction(CalculatorInstruction.Type.getByTypeOrNull(split[0]), new BigDecimal(split[1])))
                .collect(Collectors.toList());
    }

}
