package Models;

import java.time.ZonedDateTime;

public class Trip {
    ZonedDateTime statedAt;
    ZonedDateTime finishedAt;
    int durationSecs;
    String fromStopId;
    String toStopId;
    float changeAmount;
    String companyId;
    String busId;
    String pan;
    TripStatus tripStatus;

    public Trip(ZonedDateTime statedAt, ZonedDateTime finishedAt, int durationSecs,
                String fromStopId, String toStopId, float changeAmount, String companyId,
                String busId, String pan, TripStatus tripStatus) {
        this.statedAt = statedAt;
        this.finishedAt = finishedAt;
        this.durationSecs = durationSecs;
        this.fromStopId = fromStopId;
        this.toStopId = toStopId;
        this.changeAmount = changeAmount;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
        this.tripStatus = tripStatus;
    }

    public void WriteToCSV() {
        //TODO write to file instead
        System.out.printf(statedAt + " " + finishedAt + " " + durationSecs + " " + fromStopId  + " " + toStopId  + " " + changeAmount  + " " + companyId
                + " " + busId  + " " + pan + " " + tripStatus);

    }
}
