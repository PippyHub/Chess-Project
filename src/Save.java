/**
 * Saves chess position
 *
 * @author (Piper)
 * @version (21/02/2023)
 */
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
public class Save {
    public Save() {
        File file = new File ("src/position");
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