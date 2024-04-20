import Models.Tap;
import Models.Trip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Services.CSVParser.parseCSV;
import static Services.TripProcessor.processUserTrip;

public class Main {
    public static void main(String[] args) {
        HashMap<String, List<Tap>> taps = parseCSV("./src/taps.csv");

        ArrayList<Trip> trips = new ArrayList<>();
        for(Map.Entry<String, List<Tap>> entry : taps.entrySet()) { //TODO: fix format of for loop
            String user = entry.getKey();
            List<Tap> userTaps = entry.getValue();

            List<Trip> userTrips = processUserTrip(userTaps);
            trips.addAll(userTrips);
        }

        //TODO: write trips to CSV file
        trips.forEach(Trip::WriteToCSV);



    }
}