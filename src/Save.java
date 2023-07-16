/**
 * Saves chess savePosition
 *
 * @author (Piper)
 * @version (16/07/2023)
 */
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
public class Save {
    public Save() {
        File save = new File ("src/Positions/savePosition");
        try {
            FileWriter writer = new FileWriter(save);
            writer.write("Save");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}