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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        // TODO: UTC format?
        this.dateTime = ZonedDateTime.parse(dateTime, dateTimeFormatter);
        this.tapType = tapType;
        this.stopId = stopId;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
    }

}
