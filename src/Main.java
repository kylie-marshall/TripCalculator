import Models.Stop;
import Models.Tap;
import Models.UserTrip;
import Services.TapProcessor;
import Services.TripSystem;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static Services.CSVParser.openReader;
import static Services.CSVParser.parseCSV;
import static Services.CSVWriter.writeTripsToCSV;

public class Main {
    public static void main(String[] args) {
        TripSystem company1TripSystem = new TripSystem();

        Stop stop1 = company1TripSystem.addStop("Stop1");
        Stop stop2 = company1TripSystem.addStop("Stop2");
        Stop stop3 = company1TripSystem.addStop("Stop3");

        company1TripSystem.addTrip(stop1, stop2, 3.25);
        company1TripSystem.addTrip(stop2, stop3, 5.50);
        company1TripSystem.addTrip(stop1, stop3, 7.30);

        HashMap<String, List<Tap>> taps = null;
        try {
            taps = parseCSV(openReader("./src/taps.csv"));
        } catch (IOException e) {
            System.out.println("Unable to parse CSV file due to error:" + e.getMessage());
        }

        TapProcessor tapProcessor = new TapProcessor(company1TripSystem);
        List<UserTrip> trips = tapProcessor.processTaps(taps);

        writeTripsToCSV(trips);
    }
}