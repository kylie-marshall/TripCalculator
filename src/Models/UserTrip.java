package Models;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static Models.Constants.DateFormat;

public class UserTrip {
    ZonedDateTime statedAt;
    ZonedDateTime finishedAt;
    long durationSecs;
    Stop fromStopId;
    Stop toStopId;
    double changeAmount;
    String companyId;
    String busId;
    String pan;
    TripStatus tripStatus;

    public UserTrip(ZonedDateTime statedAt, ZonedDateTime finishedAt,
                    Stop fromStop, Stop toStop, double changeAmount, String companyId,
                    String busId, String pan, TripStatus tripStatus) {
        this.statedAt = statedAt;
        this.finishedAt = finishedAt;
        this.durationSecs = calculateTripDuration();
        this.fromStopId = fromStop;
        this.toStopId = toStop;
        this.changeAmount = changeAmount;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
        this.tripStatus = tripStatus;
    }

    public void WriteToCSV(BufferedWriter bufferedWriter) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateFormat);
        String startDate = statedAt == null ? "null" : statedAt.format(dateTimeFormatter);
        String finishedDate = finishedAt == null ? "null" : finishedAt.format(dateTimeFormatter);

        String output = String.join(", ", startDate, finishedDate, Long.toString(durationSecs),
                fromStopId ==  null ? null : fromStopId.toString(),
                toStopId ==  null ? null : toStopId.toString(),
                "$" + changeAmount, companyId, busId, pan, tripStatus.toString());

        System.out.println(output);

        bufferedWriter.write(output);
        bufferedWriter.newLine();
    }

    public ZonedDateTime getStatedAt() {
        return statedAt;
    }

    public ZonedDateTime getFinishedAt() {
        return finishedAt;
    }

    public long getDurationSecs() {
        return durationSecs;
    }

    public String getFromStopId() {
        return fromStopId ==  null ? null : fromStopId.toString();
    }

    public String getToStopId() {
        return toStopId ==  null ? null : toStopId.toString();
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
        if(statedAt == null || finishedAt == null) {
            return 0;
        }
        return Duration.between(statedAt, finishedAt).toSeconds();
    }
}
