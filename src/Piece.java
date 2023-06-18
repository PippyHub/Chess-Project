/**
 * Write a description of class Piece here.
 *
 * @author (Piper Inns Hall)
 * @version (18/06/2023)
 */
import java.util.LinkedList;
public class Piece {
    int pX;
    int pY;
    boolean isWhite;
    LinkedList<Piece> ps;
    public Piece(int pX, int pY, boolean isWhite, LinkedList<Piece> ps) {
        this.pX = pX;
        this.pY = pY;
        this.isWhite = isWhite;
        this.ps = ps;
        ps.add(this);
    }
    public void move(int pX, int pY) {
        for(Piece p: ps){
            if(p.pX == pX && p.pY == pY) {
                p.kill();
            }
        }
        this.pX = pX;
        this.pY = pY;

    }

    public void kill() {
        ps.remove(this);
    }

}
