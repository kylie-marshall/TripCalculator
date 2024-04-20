package Services;

import Models.Tap;
import Models.TapType;
import Models.Trip;
import Models.TripStatus;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Services.TripCost.CANCELLED_TRIP_COST;
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
        do {
            if(isTripCancelled(currentTap, nextTap)) {
                trips.add(getCancelledTrip(currentTap, nextTap));
            }
            nextTap = null;
        } while (nextTap != null);

        return trips;
    }

    private static boolean isTripCancelled(Tap currentTap, Tap nextTap) {
        return Objects.equals(currentTap.getCompanyId(), nextTap.getCompanyId()) &&
                Objects.equals(currentTap.getBusId(), nextTap.getBusId()) &&
                Objects.equals(currentTap.getStopId(), nextTap.getStopId()) &&
                currentTap.getTapType() == TapType.ON && nextTap.getTapType() == TapType.OFF;
    }

    private static Trip getCancelledTrip(Tap currentTap, Tap nextTap) {
        //TODO: fix duration difference calculation
        return new Trip(currentTap.getDateTime(), nextTap.getDateTime(), 0, currentTap.getStopId(), nextTap.getStopId(), CANCELLED_TRIP_COST,
                currentTap.getCompanyId(), currentTap.getBusId(), currentTap.getPan(), TripStatus.CANCELLED);
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
