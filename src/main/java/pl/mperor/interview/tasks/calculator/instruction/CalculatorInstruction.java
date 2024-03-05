package pl.mperor.interview.tasks.calculator.instruction;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.BinaryOperator;

public record CalculatorInstruction(Type type, BigDecimal value) {

    public enum Type {
        ADD("+", BigDecimal::add),
        SUBTRACT("-", BigDecimal::subtract),
        MULTIPLY("*", BigDecimal::multiply),
        DIVIDE("/", BigDecimal::divide),
        APPLY("", (a, b) -> a);

        private final String mark;
        private final BinaryOperator<BigDecimal> operator;

        Type(String mark, BinaryOperator<BigDecimal> operator) {
            this.mark = mark;
            this.operator = operator;
        }

        public static Type getByTypeOrNull(String input) {
            return Arrays.stream(CalculatorInstruction.Type.values())
                    .filter(type -> type.name().equalsIgnoreCase(input))
                    .findFirst().orElse(null);
        }

        public BigDecimal apply(BigDecimal value1, BigDecimal value2) {
            return operator.apply(value1, value2);
        }
    }
}
