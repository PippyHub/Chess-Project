/**
 * Saves chess savePosition
 *
 * @author (Piper)
 * @version (16/07/2023)
 */
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
public class New {
    public New() {
        File file = new File ("src/savePosition");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("Save");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}