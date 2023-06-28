/**
 * Loads chess position
 *
 * @author (Piper)
 * @version (29/02/2023)
 */
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
public class Load {
    public Load() {
        File file = new File ("src/position");
        try{
            FileWriter writer = new FileWriter(file);
            writer.write("Load");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
