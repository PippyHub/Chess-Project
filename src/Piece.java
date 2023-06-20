/**
 * Constructor for all chess pieces
 *
 * @author (Piper Inns Hall)
 * @version (20/06/2023)
 */
import java.util.LinkedList;
public class Piece {
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
        y = pY * 64;

        this.isBlack = isBlack;
        this.ps = ps;
        name = n;
        ps.add(this);
    }
    public void move(int pX, int pY) {
        if(Board.getPiece(pX * 64, pY * 64) != null) {
            if(Board.getPiece(pX * 64, pY * 64).isBlack != isBlack) {
                Board.getPiece(pX * 64, pY * 64).kill();
            }
        }

        this.pX = pX;
        this.pY = pY;

        x = pX * 64;
        y = pY * 64;
    }
    public void kill() {
        ps.remove(this);
    }
}