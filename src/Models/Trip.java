package Models;

import java.time.ZonedDateTime;

public class Trip {
    ZonedDateTime statedAt;
    ZonedDateTime finishedAt;
    int durationSecs;
    String fromStopId;
    String toStopId;
    double changeAmount;
    String companyId;
    String busId;
    String pan;
    TripStatus tripStatus;

    public Trip(ZonedDateTime statedAt, ZonedDateTime finishedAt, int durationSecs,
                String fromStopId, String toStopId, double changeAmount, String companyId,
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
        //TODO: format date display

        System.out.println(statedAt + ", " + finishedAt + ", " + durationSecs + ", " + fromStopId  + ", " + toStopId  + ", $" + changeAmount  + ", " +
                companyId + ", " + busId  + ", " + pan + ", " + tripStatus);

    }

    public ZonedDateTime getStatedAt() {
        return statedAt;
    }

    public void setStatedAt(ZonedDateTime statedAt) {
        this.statedAt = statedAt;
    }

    public ZonedDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(ZonedDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public int getDurationSecs() {
        return durationSecs;
    }

    public void setDurationSecs(int durationSecs) {
        this.durationSecs = durationSecs;
    }

    public String getFromStopId() {
        return fromStopId;
    }

    public void setFromStopId(String fromStopId) {
        this.fromStopId = fromStopId;
    }

    public String getToStopId() {
        return toStopId;
    }

    public void setToStopId(String toStopId) {
        this.toStopId = toStopId;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }
}
