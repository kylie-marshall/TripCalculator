import Models.Tap;
import Models.Trip;

import java.util.HashMap;
import java.util.List;

import static Services.CSVParser.parseCSV;
import static Services.CSVWriter.writeTripsToCSV;
import static Services.TripProcessor.processTaps;

public class Main {
    public static void main(String[] args) {
        HashMap<String, List<Tap>> taps = parseCSV("./src/taps.csv");

        List<Trip> trips = processTaps(taps);

        writeTripsToCSV(trips);
    }
}