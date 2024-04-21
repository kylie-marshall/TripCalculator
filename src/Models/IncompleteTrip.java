package Models;

public class IncompleteTrip extends UserTrip {
    public IncompleteTrip(Tap tap, double chargeAmount) {

        super(tap.getTapType() == TapType.ON ? tap.getDateTime() : null,
                tap.getTapType() == TapType.ON ? null : tap.getDateTime(),
                tap.getTapType() == TapType.ON ? tap.getStop() : null,
                tap.getTapType() == TapType.ON ? null : tap.getStop(),
                chargeAmount, tap.getCompanyId(), tap.getBusId(), tap.getPan(),
                TripStatus.INCOMPLETE);
    }
}
