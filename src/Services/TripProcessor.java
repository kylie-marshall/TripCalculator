package Services;

import Models.Tap;
import Models.TapType;
import Models.Trip;
import Models.TripStatus;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static Services.TripCost.CalculateCost;

public final class TripProcessor {
    public static List<Trip> processUserTrip(List<Tap> taps) {
        List<Trip> trips = new ArrayList<>();
        //TODO: order trips or add in order during initial parsing
        if(taps.isEmpty()) {
            return trips;
        }
        Tap currentTap = taps.getFirst();
        if(taps.size() == 1) {
            trips.add(getIncompleteTrip(currentTap));
            return trips;
        }
        Tap nextTap = taps.get(1);

        return trips;
    }

    private static Trip getIncompleteTrip(Tap currentTap) {
        ZonedDateTime startedAt = currentTap.getTapType() == TapType.ON ? currentTap.getDateTime() : null;
        ZonedDateTime finishedAt = currentTap.getTapType() == TapType.ON ? null : currentTap.getDateTime();
        String fromStop = currentTap.getTapType() == TapType.ON ? currentTap.getStopId() : null;
        String toStop = currentTap.getTapType() == TapType.ON ? null : currentTap.getStopId();

        return new Trip(
                startedAt, finishedAt, 0, fromStop, toStop, CalculateCost(fromStop, toStop),
                currentTap.getCompanyId(), currentTap.getBusId(), currentTap.getPan(), TripStatus.INCOMPLETED);
    }
}
