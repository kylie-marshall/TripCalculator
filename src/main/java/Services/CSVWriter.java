package Services;

import Models.UserTrip;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public final class CSVWriter {

    /**
     * Open new csv file and write headings and trips, one per line
     */
    public static void writeTripsToCSV(BufferedWriter bufferedWriter, List<UserTrip> trips) {
        try {
            bufferedWriter.write("Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status");
            bufferedWriter.newLine();

            for (UserTrip trip : trips) {
                trip.WriteToCSV(bufferedWriter);
            }
            
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Unable to output to file due to error: " + e.getMessage());
        }
    }
    public static BufferedWriter openWriter(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        return new BufferedWriter(writer);
    }
}
