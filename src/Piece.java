/**
 * Constructor for all chess pieces
 *
 * @author (Piper Inns Hall)
 * @version (19/06/2023)
 */
import java.util.LinkedList;
public class Piece {
    int pX;
    int pY;
    boolean isBlack;
    LinkedList<Piece> ps;
    String name;
    public Piece(int pX, int pY, boolean isBlack, String n, LinkedList<Piece> ps) {
        this.pX = pX;
        this.pY = pY;
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

    }

    public void kill() {
        ps.remove(this);
    }
}
