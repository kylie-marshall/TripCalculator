import Models.Stop;
import Models.Tap;
import Models.UserTrip;
import Services.TapProcessor;
import Services.TripSystem;

import java.util.HashMap;
import java.util.List;

import static Services.CSVParser.parseCSV;
import static Services.CSVWriter.writeTripsToCSV;

public class Main {
    public static void main(String[] args) {
        TripSystem tripSystem = new TripSystem();

        Stop stop1 = tripSystem.addStop("Stop1");
        Stop stop2 = tripSystem.addStop("Stop2");
        Stop stop3 = tripSystem.addStop("Stop3");

        tripSystem.addTrip(stop1, stop2, 3.25);
        tripSystem.addTrip(stop2, stop3, 5.50);
        tripSystem.addTrip(stop1, stop3, 7.30);

        HashMap<String, List<Tap>> taps = parseCSV("./src/taps.csv");

        TapProcessor tapProcessor = new TapProcessor(tripSystem);
        List<UserTrip> trips = tapProcessor.processTaps(taps);

        writeTripsToCSV(trips);
    }
}