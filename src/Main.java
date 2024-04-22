import Models.Stop;
import Models.Tap;
import Models.UserTrip;
import Services.TapProcessor;
import Services.TripCostSystem;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static Services.CSVParser.openReader;
import static Services.CSVParser.parseCSV;
import static Services.CSVWriter.openWriter;
import static Services.CSVWriter.writeTripsToCSV;

public class Main {
    public static void main(String[] args) throws IOException {
        TripCostSystem company1TripCostSystem = new TripCostSystem();

        Stop stop1 = company1TripCostSystem.addStop("Stop1");
        Stop stop2 = company1TripCostSystem.addStop("Stop2");
        Stop stop3 = company1TripCostSystem.addStop("Stop3");

        company1TripCostSystem.addTrip(stop1, stop2, 3.25);
        company1TripCostSystem.addTrip(stop2, stop3, 5.50);
        company1TripCostSystem.addTrip(stop1, stop3, 7.30);

        HashMap<String, List<Tap>> taps =  parseCSV(openReader("./src/taps.csv"));

        TapProcessor tapProcessor = new TapProcessor(company1TripCostSystem);
        List<UserTrip> trips = tapProcessor.processTaps(taps);

        writeTripsToCSV(openWriter("./src/trips.csv"), trips);
    }
}