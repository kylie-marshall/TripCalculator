package Services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public final class CSVWriter {
    public static BufferedWriter openWriter(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        return new BufferedWriter(writer);
    }
}
