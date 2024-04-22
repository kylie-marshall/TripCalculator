package Services;

import Models.Tap;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class CSVParser {

    /**
     * Load csv file and parse taps into hashmap
     */
    public static HashMap<String, List<Tap>> parseCSV(BufferedReader reader){
        HashMap<String, List<Tap>> taps = new HashMap<>();
        try {
            //discard first line headings
            reader.readLine();

            String line =  reader.readLine();
            while (line != null) {
                // parse each line as a Tap
                Tap tap = new Tap(line);
                taps.putIfAbsent(tap.getPan(), new ArrayList<>());
                taps.get(tap.getPan()).add(tap);

                line = reader.readLine();
            }
        }
        catch(Exception e) {
            System.out.println("Unable to parse CSV file due to error:" + e.getMessage());
        }
        return taps;
    }

    public static BufferedReader openReader(String filename) throws IOException {
        FileReader file = new FileReader(filename);
        return new BufferedReader(file);
    }
}
