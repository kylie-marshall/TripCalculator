import Models.Tap;
import Models.TapType;
import Models.Trip;
import Services.TripProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //input data
        //TODO: get data from csv input file
        //TODO: parse string into Tap list
        HashMap<String, List<Tap>> taps = new HashMap<>() {};
        taps.putIfAbsent("5500005555555559", new ArrayList<Tap>() { });
        taps.get("5500005555555559").add(new Tap(
                1,
                "22-01-2023 13:00:00",
                TapType.ON,
                "Stop1",
                "Company1",
                "Bus37",
                "5500005555555559"
        ));

        taps.putIfAbsent("5500005555555559", new ArrayList<Tap>() { });
        taps.get("5500005555555559").add(new Tap(
                2,
                "22-01-2023 13:05:00",
                TapType.OFF,
                "Stop1",
                "Company1",
                "Bus37",
                "5500005555555559"
        ));

        ArrayList<Trip> trips = new ArrayList<>();
        for(Map.Entry<String, List<Tap>> entry : taps.entrySet()) { //TODO: fix format of for loop
            String user = entry.getKey();
            List<Tap> userTaps = entry.getValue();

            List<Trip> userTrips = new TripProcessor().processUserTrip(user, userTaps);
            trips.addAll(userTrips);
        }

        //TODO: write trips to CSV file
        trips.forEach(Trip::WriteToCSV);



    }
}