package Models;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static Models.Constants.DateFormat;

public abstract class UserTrip {
    ZonedDateTime startedAt;
    ZonedDateTime finishedAt;
    long durationSecs;
    Stop fromStop;
    Stop toStop;
    double changeAmount;
    String companyId;
    String busId;
    String pan;
    TripStatus tripStatus;

    public UserTrip(ZonedDateTime startedAt, ZonedDateTime finishedAt,
                    Stop fromStop, Stop toStop, double chargeAmount, String companyId,
                    String busId, String pan, TripStatus tripStatus) {
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.durationSecs = calculateTripDuration();
        this.fromStop = fromStop;
        this.toStop = toStop;
        this.changeAmount = chargeAmount;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
        this.tripStatus = tripStatus;
    }

    public void WriteToCSV(BufferedWriter bufferedWriter) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormat);
        String startDate = startedAt == null ? "null" : startedAt.format(dateTimeFormatter);
        String finishedDate = finishedAt == null ? "null" : finishedAt.format(dateTimeFormatter);

        String output = String.join(", ", startDate, finishedDate, Long.toString(durationSecs),
                fromStop ==  null ? null : fromStop.toString(),
                toStop ==  null ? null : toStop.toString(),
                "$" + changeAmount, companyId, busId, pan, tripStatus.toString());

        System.out.println(output);

        bufferedWriter.write(output);
        bufferedWriter.newLine();
    }

    public ZonedDateTime getStartedAt() {
        return startedAt;
    }

    public ZonedDateTime getFinishedAt() {
        return finishedAt;
    }

    public long getDurationSecs() {
        return durationSecs;
    }

    public String getFromStopId() {
        return fromStop ==  null ? null : fromStop.toString();
    }

    public String getToStopId() {
        return toStop ==  null ? null : toStop.toString();
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getBusId() {
        return busId;
    }

    public String getPan() {
        return pan;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    private long calculateTripDuration() {
        if(startedAt == null || finishedAt == null) {
            return 0;
        }
        return Duration.between(startedAt, finishedAt).toSeconds();
    }
}
