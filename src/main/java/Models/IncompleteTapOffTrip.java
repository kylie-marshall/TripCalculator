package Models;

public class IncompleteTapOffTrip extends UserTrip {

    public IncompleteTapOffTrip(Tap tap, double chargeAmount) {
        super(null, tap.getDateTime(), null, tap.getStop(),
                chargeAmount, tap.getCompanyId(), tap.getBusId(), tap.getPan(),
                TripStatus.INCOMPLETE);
    }
}
