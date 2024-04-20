import Models.Tap;
import Models.Trip;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Services.CSVParser.parseCSV;
import static Services.CSVWriter.openWriter;
import static Services.TripProcessor.processUserTrip;

public class Main {
    public static void main(String[] args) {
        HashMap<String, List<Tap>> taps = parseCSV("./src/taps.csv");

        ArrayList<Trip> trips = new ArrayList<>();

        for(Map.Entry<String, List<Tap>> entry : taps.entrySet()) {
            List<Trip> userTrips = processUserTrip(entry.getValue());
            trips.addAll(userTrips);
        }

        try (BufferedWriter bufferedWriter = openWriter("./src/trips.csv")) {
            bufferedWriter.write("Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status");
            bufferedWriter.newLine();

            for (Trip trip : trips) {
                trip.WriteToCSV(bufferedWriter);
            }
        } catch (Exception e) {
            System.out.println("Unable to output to file due to error: " + e.getMessage());
        }
    }
}