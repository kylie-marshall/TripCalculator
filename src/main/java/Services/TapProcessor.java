package Services;

import Models.*;

import java.util.*;

public class TapProcessor {

    private final TripCostSystem tripCostSystem;

    public TapProcessor(TripCostSystem tripCostSystem) {
        this.tripCostSystem = tripCostSystem;
    }

    /**
     * For each user, process their taps individually
     */
    public List<UserTrip> processTaps(Map<String, List<Tap>> taps) {
        List<UserTrip> trips = new ArrayList<>();

        for(Map.Entry<String, List<Tap>> userTaps : taps.entrySet()) {
            List<UserTrip> userTrips = processUserTaps(userTaps.getValue());
            trips.addAll(userTrips);
        }
        return trips;
    }

    /**
     * Process each tap for a user, calculating their trips including cost
     */
    private List<UserTrip> processUserTaps(List<Tap> taps) {
        List<UserTrip> trips = new ArrayList<>();

        if(taps.isEmpty()) {
            return trips;
        }

        ListIterator<Tap> it = taps.listIterator();
        Tap currentTap = it.next();
        Tap nextTap;

        do {
            nextTap =  it.hasNext() ? it.next() : null;

            if(isTripIncomplete(currentTap, nextTap)) {
                trips.add(getIncompleteTrip(currentTap));
                currentTap = nextTap;
                continue;
            }

            if(isTripCancelled(currentTap, nextTap)) {
                trips.add(getCancelledTrip(currentTap, nextTap));
            }
            else {
                trips.add(getCompleteTrip(currentTap, nextTap));
            }

            currentTap = it.hasNext() ? it.next() : null;
        } while (currentTap != null);

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
        return currentTap.getTapType() == TapType.ON
                ? new IncompleteTapOnTrip(
                    currentTap,
                    this.tripCostSystem.calculateCostBetweenStops(currentTap.getStop()))
                : new IncompleteTapOffTrip(
                        currentTap,
                        this.tripCostSystem.calculateCostBetweenStops(currentTap.getStop()));
    }
}
