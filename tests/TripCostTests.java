import Models.Stop;
import Services.TripSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TripCostTests {

    TripSystem tripSystem;
    @BeforeEach
    void setUp() {
        tripSystem = new TripSystem();

        Stop stop1 = tripSystem.addStop("Stop1");
        Stop stop2 = tripSystem.addStop("Stop2");
        Stop stop3 = tripSystem.addStop("Stop3");

        tripSystem.addTrip(stop1, stop2, 3.25);
        tripSystem.addTrip(stop2, stop3, 5.50);
        tripSystem.addTrip(stop1, stop3, 7.30);
    }

    @ParameterizedTest
    @MethodSource("stopProvider")
    public void calculateCost_should_produce_correct_cost(Stop fromStop, Stop toStop, double expectedValue) {
        Assertions.assertEquals(expectedValue, tripSystem.calculateCostBetweenStops(fromStop, toStop), 0);
    }

    static Stream<Arguments> stopProvider() {
        return Stream.of(
            Arguments.arguments(new Stop("Stop1"), new Stop("Stop2"), 3.25),
            Arguments.arguments(new Stop("Stop2"), new Stop("Stop3"), 5.5),
            Arguments.arguments(new Stop("Stop3"), new Stop("Stop1"), 7.30),
            Arguments.arguments(new Stop("Stop2"), new Stop("Stop1"), 3.25),
            Arguments.arguments(new Stop("Stop3"), new Stop("Stop2"), 5.5),
            Arguments.arguments(new Stop("Stop1"), new Stop("Stop3"), 7.30),
            Arguments.arguments(new Stop("Stop1"), null, 7.30),
            Arguments.arguments(new Stop("Stop2"), null, 5.50),
            Arguments.arguments(new Stop("Stop3"), null, 7.30),
            Arguments.arguments(null, new Stop("Stop1"), 7.30),
            Arguments.arguments(null, new Stop("Stop2"), 5.50),
            Arguments.arguments(null, new Stop("Stop3"), 7.30),
            Arguments.arguments(null, new Stop("Stop4"), 0),
            Arguments.arguments(new Stop("Stop4"), new Stop("Stop3"), 0)
        );
    }
}
