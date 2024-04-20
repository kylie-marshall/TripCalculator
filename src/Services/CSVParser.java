package Services;

import Models.Tap;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class CSVParser {
    public static HashMap<String, List<Tap>> parseCSV(String filename){

        HashMap<String, List<Tap>> taps = new HashMap<>();
        try (FileReader in = new FileReader(filename);
             BufferedReader reader = new BufferedReader(in)) {

            String line;
            //discard first line
            reader.readLine();
            // Read each line from the file until end of file
            while ((line = reader.readLine()) != null) {
                // parse each line as a Tap
                Tap tap = new Tap(line);
                taps.putIfAbsent(tap.getPan(), new ArrayList<>());
                taps.get(tap.getPan()).add(tap);
            }
        }
        catch(Exception e) {
            System.out.println("Unable to parse CSV file");
        }
        return taps;
    }
}
