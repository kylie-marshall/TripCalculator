import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static Services.TripCost.calculateCost;


public class TripCostTests {


    @ParameterizedTest
    @MethodSource("stopProvider")
    public void calculateCost_should_produce_correct_cost(String fromStop, String toStop, double expectedValue) {
        Assertions.assertEquals(expectedValue, calculateCost(fromStop, toStop), 0);
    }

    static Stream<Arguments> stopProvider() {
        return Stream.of(
            Arguments.arguments("Stop1", "Stop2", 3.25),
            Arguments.arguments("Stop2", "Stop3", 5.5),
            Arguments.arguments("Stop3", "Stop1", 7.30),
            Arguments.arguments("Stop2", "Stop1", 3.25),
            Arguments.arguments("Stop3", "Stop2", 5.5),
            Arguments.arguments("Stop1", "Stop3", 7.30),
            Arguments.arguments("Stop1", null, 7.30),
            Arguments.arguments("Stop2", null, 5.50),
            Arguments.arguments("Stop3", null, 7.30),
            Arguments.arguments(null, "Stop1", 7.30),
            Arguments.arguments(null, "Stop2", 5.50),
            Arguments.arguments(null, "Stop3", 7.30),
            Arguments.arguments(null, "Stop4", 0),
            Arguments.arguments("Stop4", "Stop3", 0)
        );
    }
}
