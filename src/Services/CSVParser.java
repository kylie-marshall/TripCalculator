package Services;

import Models.Tap;
import Models.TapType;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class CSVParser {
    public static HashMap<String, List<Tap>> parseCSV(String filename){

        HashMap<String, List<Tap>> taps = new HashMap<>();
        try (FileInputStream stream = new FileInputStream(new File(filename).getAbsolutePath());
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {

            String line;
            //discard first line
            reader.readLine();
            // Read each line from the file until end of file
            while ((line = reader.readLine()) != null) {
                // parse each line into Tap
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
