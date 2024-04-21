package Services;

import Models.*;

import java.time.ZonedDateTime;
import java.util.*;

import static Services.TripSystem.CANCELLED_TRIP_COST;

public class TapProcessor {
    TripSystem tripSystem;
    public TapProcessor(TripSystem tripSystem) {
        this.tripSystem = tripSystem;
    }

    public List<UserTrip> processTaps(HashMap<String, List<Tap>> taps) {
        List<UserTrip> trips = new ArrayList<>();

        for(Map.Entry<String, List<Tap>> userTaps : taps.entrySet()) {
            List<UserTrip> userTrips = processUserTrip(userTaps.getValue());
            trips.addAll(userTrips);
        }
        return trips;
    }

    public List<UserTrip> processUserTrip(List<Tap> taps) {
        List<UserTrip> trips = new ArrayList<>();
        //TODO: order taps or add in order during initial parsing
        if(taps.isEmpty()) {
            return trips;
        }
        int currentTapIndex = 0;
        Tap nextTap;
        do {
            Tap currentTap = taps.size() >= currentTapIndex + 1 ? taps.get(currentTapIndex) : null;
            if(currentTap == null) {
                break;
            }
            nextTap = taps.size() >= currentTapIndex + 2 ? taps.get(currentTapIndex + 1) : null;

            if(isTripIncomplete(currentTap, nextTap)) {
                trips.add(getIncompleteTrip(currentTap));
                currentTapIndex += 1;
                continue;
            }

            if(isTripCancelled(currentTap, nextTap)) {
                trips.add(getCancelledTrip(currentTap, nextTap));
                currentTapIndex += 2;
                continue;
            }

            trips.add(getCompleteTrip(currentTap, nextTap));
            currentTapIndex += 2;
        } while (nextTap != null);

        return trips;
    }

    private static boolean isTripIncomplete(Tap currentTap, Tap nextTap){
        return currentTap.getTapType() == TapType.OFF
                || nextTap == null
                || nextTap.getTapType() == TapType.ON
                || !Objects.equals(currentTap.getBusId(), nextTap.getBusId())
                || !Objects.equals(currentTap.getCompanyId(), nextTap.getCompanyId());
    }
    private static boolean isTripCancelled(Tap currentTap, Tap nextTap) {
        return Objects.equals(currentTap.getCompanyId(), nextTap.getCompanyId()) &&
                Objects.equals(currentTap.getBusId(), nextTap.getBusId()) &&
                Objects.equals(currentTap.getStop(), nextTap.getStop()) &&
                currentTap.getTapType() == TapType.ON && nextTap.getTapType() == TapType.OFF;
    }

    private static UserTrip getCancelledTrip(Tap currentTap, Tap nextTap) {

        return new UserTrip(currentTap.getDateTime(),
                nextTap.getDateTime(),
                currentTap.getStop(),
                nextTap.getStop(),
                CANCELLED_TRIP_COST,
                currentTap.getCompanyId(),
                currentTap.getBusId(),
                currentTap.getPan(),
                TripStatus.CANCELLED);
    }

    private UserTrip getCompleteTrip(Tap currentTap, Tap nextTap) {

        return new UserTrip(currentTap.getDateTime(),
                nextTap.getDateTime(),
                currentTap.getStop(),
                nextTap.getStop(),
                this.tripSystem.calculateCostBetweenStops(currentTap.getStop(), nextTap.getStop()),
                currentTap.getCompanyId(),
                currentTap.getBusId(),
                currentTap.getPan(),
                TripStatus.COMPLETED);
    }

    private UserTrip getIncompleteTrip(Tap currentTap) {
        ZonedDateTime startedAt = currentTap.getTapType() == TapType.ON ? currentTap.getDateTime() : null;
        ZonedDateTime finishedAt = currentTap.getTapType() == TapType.ON ? null : currentTap.getDateTime();
        Stop fromStop = currentTap.getTapType() == TapType.ON ? currentTap.getStop() : null;
        Stop toStop = currentTap.getTapType() == TapType.ON ? null : currentTap.getStop();

        return new UserTrip(
                startedAt,
                finishedAt,
                fromStop,
                toStop,
                this.tripSystem.calculateCostBetweenStops(fromStop, toStop),
                currentTap.getCompanyId(),
                currentTap.getBusId(),
                currentTap.getPan(),
                TripStatus.INCOMPLETE);
    }
}
