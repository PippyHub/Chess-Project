/**
 * Constructor for all chess pieces
 *
 * @author (Piper Inns Hall)
 * @version (19/06/2023)
 */
import java.io.IOException;
import java.util.LinkedList;
public class Piece {
    final int HEADER_OFFSET = 30;
    int pX; //piece x
    int pY; //piece y
    int x; //piece board pos x
    int y; //piece board pos y
    boolean isBlack;
    LinkedList<Piece> ps;
    String name;
    public Piece(int pX, int pY, boolean isBlack, String n, LinkedList<Piece> ps) {
        this.pX = pX;
        this.pY = pY;

        x = pX * 64;
        y = pY * 64 + HEADER_OFFSET;

        this.isBlack = isBlack;
        this.ps = ps;
        name = n;
        ps.add(this);
    }
    public void move(int pX, int pY) {
        ps.stream().filter((p) -> (p.pX == pX && p.pY == pY)).forEachOrdered((p) -> {
            p.kill();
        });
        this.pX = pX;
        this.pY = pY;

        x = pX * 64;
        y = pY * 64 + HEADER_OFFSET;
    }

    public void kill() {
        ps.remove(this);
    }
}
