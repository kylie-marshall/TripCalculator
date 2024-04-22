package Models;

public class IncompleteTapOnTrip extends UserTrip {
    public IncompleteTapOnTrip(Tap tap, double chargeAmount) {

        super(tap.getDateTime(), null, tap.getStop(), null,
                chargeAmount, tap.getCompanyId(), tap.getBusId(), tap.getPan(),
                TripStatus.INCOMPLETE);
    }
}
