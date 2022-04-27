package ru.job4j.lambda;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import static org.hamcrest.Matchers.is;
import java.util.Arrays;

public class DiapasonCalculateTest {
    @Test
    public void whenLinearFunctionThenLinearResults() {
        DiapasonCalculate function = new DiapasonCalculate();
        List<Double> result = function.diapason(5, 8, x -> 2 * x + 1);
        List<Double> expected = Arrays.asList(11D, 13D, 15D);
        assertThat(result, is(expected));
    }

    @Test
    public void whenLinearFunctionThenSquareResults() {
        DiapasonCalculate function = new DiapasonCalculate();
        List<Double> result = function.diapason(5, 8, x -> x * x + 1);
        List<Double> expected = Arrays.asList(26D, 37D, 50D);
        assertThat(result, is(expected));
    }

    @Test
    public void whenLinearFunctionExpResults() {
        DiapasonCalculate function = new DiapasonCalculate();
        List<Double> result = function.diapason(5, 8, x -> Math.pow(2, x));
        List<Double> expected = Arrays.asList(32D, 64D, 128D);
        assertThat(result, is(expected));
    }
}