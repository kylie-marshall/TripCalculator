package Models;

import java.time.ZonedDateTime;

import static Services.TripSystem.CANCELLED_TRIP_COST;

public class CancelledTrip extends UserTrip {
    public CancelledTrip(ZonedDateTime startedAt, ZonedDateTime finishedAt, Stop fromStop, Stop toStop,String companyId, String busId, String pan) {
        super(startedAt, finishedAt, fromStop, toStop, CANCELLED_TRIP_COST, companyId, busId, pan, TripStatus.CANCELLED);
    }
}
