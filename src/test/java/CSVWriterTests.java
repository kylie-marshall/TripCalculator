import Models.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static Services.CSVWriter.writeTripsToCSV;

public class CSVWriterTests {

    @Test
    public void writeTripsToCSV_should_write_trips() {
        ZonedDateTime today = ZonedDateTime.of(2024, 4,1,13, 0, 0, 0, ZoneOffset.UTC);
        StringWriter writer = new StringWriter();
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        List<UserTrip > trips = new ArrayList<>();
        trips.add(new CompletedTrip(today, today.plusHours(1), new Stop("Stop1"), new Stop("Stop2"), 3.25,"Company1", "Bus1", "34343434343434"));
        trips.add(new CancelledTrip(today, today.plusMinutes(1), new Stop("Stop1"), new Stop("Stop1"), "Company1", "Bus1", "34343434343434"));
        trips.add(new IncompleteTapOnTrip(new Tap(1, "01-04-2024 13:00:00", TapType.ON, "Stop1", "Company1", "Bus1", "34343434343434"), 7.3));
        trips.add(new IncompleteTapOffTrip(new Tap(1, "01-04-2024 13:00:00", TapType.OFF, "Stop1", "Company1", "Bus1", "34343434343434"), 7.3));

        writeTripsToCSV(bufferedWriter, trips);

        Assert.assertEquals("Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status\r\n" +
                "01-04-2024 13:00:00, 01-04-2024 14:00:00, 3600, Stop1, Stop2, $3.25, Company1, Bus1, 34343434343434, COMPLETED\r\n" +
                "01-04-2024 13:00:00, 01-04-2024 13:01:00, 60, Stop1, Stop1, $0.0, Company1, Bus1, 34343434343434, CANCELLED\r\n" +
                "01-04-2024 13:00:00, null, 0, Stop1, null, $7.3, Company1, Bus1, 34343434343434, INCOMPLETE\r\n" +
                "null, 01-04-2024 13:00:00, 0, null, Stop1, $7.3, Company1, Bus1, 34343434343434, INCOMPLETE\r\n", writer.toString());

    }
}
