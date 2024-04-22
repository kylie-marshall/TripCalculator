package Models;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static Models.Constants.DATE_FORMAT;

public class Tap {
    private final int id;
    private final ZonedDateTime dateTime;
    private final TapType tapType;
    private final Stop stop;
    private final String companyId;
    private final String busId;
    private final String pan;

    public Tap(int id, String dateTime, TapType tapType, String stopId, String companyId, String busId, String pan) {
        this.id = id;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneOffset.UTC);
        this.dateTime = ZonedDateTime.parse(dateTime, dateTimeFormatter);
        this.tapType = tapType;
        this.stop = new Stop(stopId);
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
    }

    public Tap(String commaSeparatedInput) {
        String[] fields = commaSeparatedInput.split(", ");

        this.id = Integer.parseInt(fields[0]);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneOffset.UTC);
        this.dateTime = ZonedDateTime.parse(fields[1], dateTimeFormatter);
        this.tapType = TapType.valueOf(fields[2].toUpperCase());
        this.stop = new Stop(fields[3]);
        this.companyId = fields[4];
        this.busId = fields[5];
        this.pan = fields[6];
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public TapType getTapType() {
        return tapType;
    }

    public Stop getStop() {
        return stop;
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
