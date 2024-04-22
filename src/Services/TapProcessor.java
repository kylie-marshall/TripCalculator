package Services;

import Models.*;

import java.util.*;

public class TapProcessor {
    TripCostSystem tripCostSystem;
    public TapProcessor(TripCostSystem tripCostSystem) {
        this.tripCostSystem = tripCostSystem;
    }

    /*
     * for each user, process their taps individually
     */
    public List<UserTrip> processTaps(HashMap<String, List<Tap>> taps) {
        List<UserTrip> trips = new ArrayList<>();

        for(Map.Entry<String, List<Tap>> userTaps : taps.entrySet()) {
            List<UserTrip> userTrips = processUserTaps(userTaps.getValue());
            trips.addAll(userTrips);
        }
        return trips;
    }

    /*
     * Process each tap for a user, calculating their trips including cost
     */
    private List<UserTrip> processUserTaps(List<Tap> taps) {
        List<UserTrip> trips = new ArrayList<>();

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
        return new CancelledTrip(currentTap.getDateTime(),
                nextTap.getDateTime(),
                currentTap.getStop(),
                nextTap.getStop(),
                currentTap.getCompanyId(),
                currentTap.getBusId(),
                currentTap.getPan());
    }

    private UserTrip getCompleteTrip(Tap currentTap, Tap nextTap) {
        return new CompletedTrip(currentTap.getDateTime(),
                nextTap.getDateTime(),
                currentTap.getStop(),
                nextTap.getStop(),
                this.tripCostSystem.calculateCostBetweenStops(currentTap.getStop(), nextTap.getStop()),
                currentTap.getCompanyId(),
                currentTap.getBusId(),
                currentTap.getPan());
    }

    private UserTrip getIncompleteTrip(Tap currentTap) {
        return new IncompleteTrip(
                currentTap,
                this.tripCostSystem.calculateCostBetweenStops(currentTap.getStop(), null)
        );
    }
}
