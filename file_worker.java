import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class file_worker {
    public static float[] readFloatArray(String filePath, int linesCount) throws IOException {
        float[] values = new float[linesCount];

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            for (int i = 0; i < linesCount; i++) {
                String line = reader.readLine();
                values[i] = Float.parseFloat(line.trim());
            }
        }

        return values;
    }

    public static void writeFloatArray(String filePath, float[] values) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (float value : values) {
                writer.write(Float.toString(value));
                writer.newLine();
            }
        }
    }
}
