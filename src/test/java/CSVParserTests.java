import Models.Tap;
import Models.TapType;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import static Services.CSVParser.parseCSV;

public class CSVParserTests {
    @Test
    public void parseCSV_should_return_taps_for_multiple_users() {

        BufferedReader bufferedReader = new BufferedReader(new StringReader("ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN\n" +
                "1, 22-01-2023 13:00:00, ON, Stop1, Company1, Bus1, 5500005555555559\n" +
                "2, 22-01-2023 13:05:00, OFF, Stop2, Company2, Bus2, 122000000000003\n" +
                "3, 22-01-2023 14:00:00, OFF, Stop1, Company1, Bus1, 5500005555555559\n"));

        HashMap<String, List<Tap>> taps = parseCSV(bufferedReader);

        Assert.assertEquals(2, taps.keySet().size());
        Assert.assertEquals(2, taps.get("5500005555555559").size());
        Tap tap1 = taps.get("5500005555555559").getFirst();

        Assert.assertEquals(TapType.ON, tap1.getTapType());
        Assert.assertEquals("Company1", tap1.getCompanyId());
        Assert.assertEquals("Stop1", tap1.getStop().toString());
        Assert.assertEquals("Bus1", tap1.getBusId());
        Assert.assertEquals("5500005555555559", tap1.getPan());
        Assert.assertNotNull(tap1.getDateTime());

        Tap tap2 = taps.get("122000000000003").getFirst();

        Assert.assertEquals(TapType.OFF, tap2.getTapType());
        Assert.assertEquals("Company2", tap2.getCompanyId());
        Assert.assertEquals("Stop2", tap2.getStop().toString());
        Assert.assertEquals("Bus2", tap2.getBusId());
        Assert.assertEquals("122000000000003", tap2.getPan());
        Assert.assertNotNull(tap2.getDateTime());

    }

    @Test
    public void parseCSV_no_trips_should_return_empty_hash_map() {

        BufferedReader bufferedReader = new BufferedReader(new StringReader("ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN\n"));

        HashMap<String, List<Tap>> taps = parseCSV(bufferedReader);

        Assert.assertEquals(0, taps.keySet().size());
    }

}
