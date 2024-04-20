package Models;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Tap {
    int id;
    ZonedDateTime dateTime;
    TapType tapType;
    String stopId;
    String companyId;
    String busId;
    String pan;


    public Tap(int id, String dateTime, TapType tapType, String stopId, String companyId, String busId, String pan) {
        this.id = id;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").withZone(ZoneOffset.UTC);
        // TODO: UTC format?
        this.dateTime = ZonedDateTime.parse(dateTime, dateTimeFormatter);
        this.tapType = tapType;
        this.stopId = stopId;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
    }
    public Tap(String commaSeparatedInput) {
        String[] fields = commaSeparatedInput.split(", ");

        this.id = Integer.parseInt(fields[0]);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").withZone(ZoneOffset.UTC);
        // TODO: UTC format?
        this.dateTime = ZonedDateTime.parse(fields[1], dateTimeFormatter);
        this.tapType = TapType.valueOf(fields[2].toUpperCase());
        this.stopId = fields[3];
        this.companyId = fields[4];
        this.busId = fields[5];
        this.pan = fields[6];
    }

    public int getId() {
        return id;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public TapType getTapType() {
        return tapType;
    }

    public String getStopId() {
        return stopId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getPan() {
        return pan;
    }

    public String getBusId() {
        return busId;
    }
}
