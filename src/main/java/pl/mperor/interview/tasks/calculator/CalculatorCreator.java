package pl.mperor.interview.tasks.calculator;

import pl.mperor.interview.tasks.calculator.instruction.CalculatorInstruction;

import java.math.BigDecimal;
import java.util.List;

public class CalculatorCreator {

    public CalculatorEngine create(List<CalculatorInstruction> instructions) {
        CalculatorEngine calculator = new CalculatorEngine(BigDecimal.ZERO);
        instructions.stream()
                .forEach(instruction -> {
                    if (instruction.type() == CalculatorInstruction.Type.APPLY) {
                        calculator.setInitialValue(instruction.value());
                    } else {
                        calculator.append(initValue -> instruction.type().apply(initValue, instruction.value()));
                    }
                });

        return calculator;
    }

}
