package pl.mperor.interview.tasks.calculator;

import java.math.BigDecimal;
import java.util.function.Function;

class CalculatorEngine {

    private BigDecimal initialValue;
    private Function<BigDecimal, BigDecimal> linkedInstruction = Function.identity();

    CalculatorEngine(BigDecimal initialValue) {
        this.initialValue = initialValue;
    }

    public void setInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
    }

    public void append(Function<BigDecimal, BigDecimal> instruction) {
        linkedInstruction = linkedInstruction.andThen(instruction);
    }

    public BigDecimal compute() {
        return linkedInstruction.apply(initialValue);
    }

}
