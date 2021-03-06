package uk.co.ryanharrison.mathengine.parser;

import org.junit.jupiter.api.Test;
import uk.co.ryanharrison.mathengine.parser.nodes.NodeConstant;
import uk.co.ryanharrison.mathengine.parser.nodes.NodeFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EvaluatorCustomFunctionTest {

    private Evaluator evaluator = Evaluator.newSimpleEvaluator();

    @Test
    void canDefineCustomFunction() {
        NodeConstant nodeConstant = evaluator.evaluateConstant("f(x) := x + 2");
        assertThat(nodeConstant).isInstanceOf(NodeFunction.class);
        assertThat(evaluator.evaluateDouble("f(2)")).isEqualTo(4);
    }

    @Test
    void canRedefineFunction() {
        evaluator.evaluateConstant("f(x) := x + 2");
        assertThat(evaluator.evaluateDouble("f(2)")).isEqualTo(4);

        evaluator.evaluateConstant("f(x) := x + 5");
        assertThat(evaluator.evaluateDouble("f(2)")).isEqualTo(7);

        evaluator.evaluateConstant("f(x) := x^2 + 8*x + 12");
        assertThat(evaluator.evaluateDouble("f(2)")).isEqualTo(32);
    }

    @Test
    void cannotDefineFunctionAsSystemOperator() {
        assertThrows(Exception.class, () -> evaluator.evaluateConstant("sin(x) := x + 2"));
        assertThrows(Exception.class, () -> evaluator.evaluateConstant("plus(x) := x + 2"));
        assertThrows(Exception.class, () -> evaluator.evaluateConstant("+(x) := x + 2"));
    }

}
