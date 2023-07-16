/**
 * Saves chess savePosition
 *
 * @author (Piper)
 * @version (16/07/2023)
 */
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;

public class Save {
    public Save(LinkedList<Piece> ps) {
        File save = new File("src/Positions/savePosition");
        try {
            FileWriter writer = new FileWriter(save);
            for (Piece piece : ps) {
                String line = piece.pX + "," + piece.pY + "," + piece.isBlack + "," + piece.name + "\n";
                writer.write(line);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
