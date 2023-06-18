/**
 * Main class
 *
 * @author (Piper Inns Hall)
 * @version (18/06/2023)
 */
import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        try {
            new Board();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}