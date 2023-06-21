/**
 * Constructor for all chess pieces
 *
 * @author (Piper Inns Hall)
 * @version (21/06/2023)
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
        System.out.print(pX + pY);
        System.out.print(" ");
        System.out.print(this.pX);


        int deltaX = pX - this.pX;
        int deltaY = pY - this.pY;

        System.out.print(deltaX);
        System.out.print(deltaY);

        if (legalMove()) {
            this.pX = pX;
            this.pY = pY;

            x = pX * 64;
            y = pY * 64;

            rules();
        }
    }

    public void rules() {
        //piece taking
        for (Piece piece : ps) {
            if (piece != this && piece.pX == pX && piece.pY == pY && piece.isBlack != isBlack) {
                piece.kill();
                break; // Exit the loop after taking one piece
            }
        }

        //promotion
        if (name.equalsIgnoreCase("pawn")) {
            if ((isBlack && pY == 7) || (!isBlack && pY == 0)) {
                name = "queen";
            }
        }
    }
    public boolean legalMove() {
        return true;
    }
    public void kill() {
        ps.remove(this);
    }
}