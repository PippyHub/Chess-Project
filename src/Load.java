/**
 * Loads chess savePosition
 *
 * @author (Piper)
 * @version (16/07/2023)
 */
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class Load {
    private static File load;
    static final int PIECE_AMOUNT = 32;
    static int[] pX;
    static int[] pY;
    static boolean[] isBlack;
    static String[] name;
    public Load() {
        pX = new int[PIECE_AMOUNT];
        pY = new int[PIECE_AMOUNT];
        isBlack = new boolean[PIECE_AMOUNT];
        name = new String[PIECE_AMOUNT];
    }
    public void loadFile(File file){
        load = file;
        fileRead();
    }
    public static void fileRead() {
        try {
            Scanner readFile = new Scanner(load);
            for (int piece = 0; piece < PIECE_AMOUNT; piece++) {
                String lineRead = readFile.nextLine();
                String[] parts = lineRead.split(",");
                pX[piece] = Integer.parseInt(parts[0]);
                pY[piece] = Integer.parseInt(parts[1]);
                isBlack[piece] = Boolean.parseBoolean(parts[2]);
                name[piece] = parts[3];
            }
            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
