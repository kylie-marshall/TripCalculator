package Models;

import java.time.ZonedDateTime;

public class CompletedTrip extends UserTrip {

    public CompletedTrip(ZonedDateTime startedAt, ZonedDateTime finishedAt, Stop fromStop, Stop toStop, double chargeAmount,  String companyId, String busId, String pan) {
        super(startedAt, finishedAt, fromStop, toStop, chargeAmount, companyId, busId, pan, TripStatus.COMPLETED);
    }
}
