import Models.*;
import Services.TapProcessor;
import Services.TripSystem;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestProcessor {
    TapProcessor tapProcessor;
    @BeforeEach
    void setUp() {
        TripSystem tripSystem = new TripSystem();

        Stop stop1 = tripSystem.addStop("Stop1");
        Stop stop2 = tripSystem.addStop("Stop2");
        Stop stop3 = tripSystem.addStop("Stop3");

        tripSystem.addTrip(stop1, stop2, 3.25);
        tripSystem.addTrip(stop2, stop3, 5.50);
        tripSystem.addTrip(stop1, stop3, 7.30);

        tapProcessor = new TapProcessor(tripSystem);
    }

    @Test
    public void processUserTrip_should_return_one_start_incomplete_trip() {
        HashMap<String, List<Tap>> taps = new HashMap<>();
        taps.put("5500005555555559", new ArrayList<>());
        taps.get("5500005555555559").add(new Tap(
                1,
                "22-01-2023 13:00:00",
                TapType.ON,
                "Stop1",
                "Company1",
                "Bus37",
                "5500005555555559"
        ));
        List<UserTrip> trips = tapProcessor.processTaps(taps);

        Assertions.assertEquals(1, trips.size());
        UserTrip trip = trips.getFirst();
        Assertions.assertNotNull(trip.getStatedAt()); //TODO: assert date values correctly
        Assertions.assertNull(trip.getFinishedAt());
        Assertions.assertEquals("Stop1", trip.getFromStopId());
        Assertions.assertNull(trip.getToStopId());
        Assertions.assertEquals("Company1", trip.getCompanyId());
        Assertions.assertEquals("Bus37", trip.getBusId());
        Assertions.assertEquals("5500005555555559", trip.getPan());
        Assertions.assertEquals(TripStatus.INCOMPLETE, trip.getTripStatus());
        Assertions.assertEquals(0, trip.getDurationSecs());
        Assertions.assertEquals(7.3, trip.getChangeAmount(), 0);
    }
    @Test
    public void processUserTrip_should_return_one_end_incomplete_trip() {
        HashMap<String, List<Tap>> taps = new HashMap<>();
        taps.put("5500005555555559", new ArrayList<>());
        taps.get("5500005555555559").add(new Tap(
                1,
                "22-01-2023 13:00:00",
                TapType.OFF,
                "Stop1",
                "Company1",
                "Bus37",
                "5500005555555559"
        ));
        List<UserTrip> trips = tapProcessor.processTaps(taps);

        Assertions.assertEquals(1, trips.size());
        UserTrip trip = trips.getFirst();
        Assertions.assertNull(trip.getStatedAt());
        Assertions.assertNotNull(trip.getFinishedAt());
        Assertions.assertEquals("Stop1", trip.getToStopId());
        Assertions.assertNull(trip.getFromStopId());
        Assertions.assertEquals("Company1", trip.getCompanyId());
        Assertions.assertEquals("Bus37", trip.getBusId());
        Assertions.assertEquals("5500005555555559", trip.getPan());
        Assertions.assertEquals(TripStatus.INCOMPLETE, trip.getTripStatus());
        Assertions.assertEquals(0, trip.getDurationSecs());
        Assertions.assertEquals(7.3, trip.getChangeAmount(), 0);
    }
    @Test
    public void processUserTrip_should_return_one_cancelled_trip() {
        HashMap<String, List<Tap>> taps = new HashMap<>();
        taps.put("5500005555555559", new ArrayList<>());
        taps.get("5500005555555559").add(new Tap(
                1,
                "22-01-2023 13:00:00",
                TapType.ON,
                "Stop1",
                "Company1",
                "Bus37",
                "5500005555555559"
        ));
        taps.get("5500005555555559").add(new Tap(
                2,
                "22-01-2023 13:01:00",
                TapType.OFF,
                "Stop1",
                "Company1",
                "Bus37",
                "5500005555555559"
        ));
        List<UserTrip> trips = tapProcessor.processTaps(taps);

        Assertions.assertEquals(1, trips.size());
        UserTrip trip = trips.getFirst();
        Assertions.assertNotNull(trip.getStatedAt());
        Assertions.assertNotNull(trip.getFinishedAt());
        Assertions.assertEquals("Stop1", trip.getToStopId());
        Assertions.assertEquals("Stop1", trip.getFromStopId());
        Assertions.assertEquals("Company1", trip.getCompanyId());
        Assertions.assertEquals("Bus37", trip.getBusId());
        Assertions.assertEquals("5500005555555559", trip.getPan());
        Assertions.assertEquals(TripStatus.CANCELLED, trip.getTripStatus());
        Assertions.assertEquals(60, trip.getDurationSecs());
        Assertions.assertEquals(0, trip.getChangeAmount(), 0);
    }
    @Test
    public void processUserTrip_should_return_one_complete_trip() {
        HashMap<String, List<Tap>> taps = new HashMap<>();
        taps.put("5500005555555559", new ArrayList<>());
        taps.get("5500005555555559").add(new Tap(
                1,
                "22-01-2023 13:00:00",
                TapType.ON,
                "Stop1",
                "Company1",
                "Bus37",
                "5500005555555559"
        ));
        taps.get("5500005555555559").add(new Tap(
                2,
                "22-01-2023 13:01:00",
                TapType.OFF,
                "Stop2",
                "Company1",
                "Bus37",
                "5500005555555559"
        ));
        List<UserTrip> trips = tapProcessor.processTaps(taps);

        Assertions.assertEquals(1, trips.size());
        UserTrip trip = trips.getFirst();
        Assertions.assertNotNull(trip.getStatedAt());
        Assertions.assertNotNull(trip.getFinishedAt());
        Assertions.assertEquals("Stop1", trip.getFromStopId());
        Assertions.assertEquals("Stop2", trip.getToStopId());
        Assertions.assertEquals("Company1", trip.getCompanyId());
        Assertions.assertEquals("Bus37", trip.getBusId());
        Assertions.assertEquals("5500005555555559", trip.getPan());
        Assertions.assertEquals(TripStatus.COMPLETED, trip.getTripStatus());
        Assertions.assertEquals(60, trip.getDurationSecs());
        Assertions.assertEquals(3.25, trip.getChangeAmount(), 0);
    }

    @Test
    public void processUserTrip_empty_should_return_no_trips() {
        HashMap<String, List<Tap>> taps = new HashMap<>();
        taps.put("5500005555555559", new ArrayList<>());

        List<UserTrip> trips = tapProcessor.processTaps(taps);

        Assertions.assertEquals(0, trips.size());
    }
}
