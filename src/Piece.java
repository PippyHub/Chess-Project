/**
 * Constructor for all chess pieces
 *
 * @author (Piper Inns Hall)
 * @version (23/06/2023)
 */
import java.util.LinkedList;
public class Piece {
    int pX; //piece x
    int pY; //piece y
    int legalMovePX;
    int legalMovePY;
    int x; //piece board pos x
    int y; //piece board pos y
    int deltaX; //change in x when moving piece
    int deltaY; //change in y when moving piece
    boolean isBlack;
    boolean pieceMoved;
    boolean castling;
    LinkedList<Piece> ps;
    String name;
    private static boolean isBlackTurn = false;
    public Piece(int pX, int pY, boolean isBlack, boolean pieceMoved, String n, LinkedList<Piece> ps) {
        this.pX = pX;
        this.pY = pY;

        x = pX * 64;
        y = pY * 64;

        this.isBlack = isBlack;
        this.pieceMoved = pieceMoved;
        this.ps = ps;
        name = n;
        ps.add(this);
    }
    public void move(int pX, int pY) {
        deltaX = pX - this.pX;
        deltaY = pY - this.pY;

        System.out.println(deltaX);
        System.out.println(deltaY);

        legalMovePX = pX;
        legalMovePY = pY;

        if (legalMove()) {
            taking();
            promotion();
            switchTurns();

            this.pieceMoved = true;

            this.pX = pX;
            this.pY = pY;

            x = pX * 64;
            y = pY * 64;
        }
    }
    public void taking() {
        if (Board.getPiece(legalMovePX * 64, legalMovePY * 64) != null) {
            Board.getPiece(legalMovePX * 64, legalMovePY * 64).kill();
        } //taking pieces
    }
    public void promotion() {
        if (name.equalsIgnoreCase("pawn")) {
            if ((isBlack && legalMovePY == 7) || (!isBlack && legalMovePY == 0)) {
                name = "queen";
            }
        } //promotion
    }
    public static void switchTurns() {
        isBlackTurn = !isBlackTurn;
    }
    public boolean legalMove() {
        if (deltaX == 0 && deltaY == 0) {
            return false;
        } //cannot move to own square
        if (Board.getPiece(legalMovePX * 64, legalMovePY * 64) != null) {
            if (Board.getPiece(legalMovePX * 64, legalMovePY * 64).isBlack == isBlack) {
                return false;
            }
        } //cannot take own pieces
        // Check if it's the turn of the current piece's color
        /*if ((isBlackTurn && !isBlack) || (!isBlackTurn && isBlack)) {
            return false;
        }*/
        if (!queenMove()) {
            return false;
        }
        if (!kingMove()) {
            return false;
        }
        if (!rookMove()) {
            return false;
        }
        if (!knightMove()) {
            return false;
        }
        if (!bishopMove()) {
            return false;
        }
        if (!pawnMove()) {
            return false;
        }
        // check for obstruction between current position and desired position
        return noObstruction();
    }
    public boolean queenMove() {
        if (name.equalsIgnoreCase("queen")) {
            int absDeltaX = Math.abs(deltaX);
            int absDeltaY = Math.abs(deltaY);
            return absDeltaX == absDeltaY || deltaX == 0 || deltaY == 0;
        } //queen moves
        return true;
    }
    public boolean kingMove() {
        if (name.equalsIgnoreCase("king")) {
            if (!pieceMoved && deltaY == 0 && Math.abs(deltaX) == 2) {
                int rookX = (deltaX > 0) ? 7 : 0; // Determine the rook's starting position
                int rookY = pY; // Rook stays in the same row

                Piece rook = Board.getPiece(rookX * 64, rookY * 64);
                if (rook != null && rook.name.equalsIgnoreCase("rook") && !rook.pieceMoved) {
                    castling = true;
                    // Move the rook
                    int newRookX = (deltaX > 0) ? pX + 1 : pX - 1;
                    int newRookY = pY;
                    rook.move(newRookX, newRookY);
                }
                return true;
            }
            return Math.abs(deltaX) <= 1 && Math.abs(deltaY) <= 1;
        }//king moves
        return true;
    }
    public boolean rookMove() {
        if (name.equalsIgnoreCase("rook")) {
            return deltaX == 0 || deltaY == 0;
        } //rook moves
        return true;
    }
    public boolean knightMove() {
        if (name.equalsIgnoreCase("knight")) {
            int absDeltaX = Math.abs(deltaX);
            int absDeltaY = Math.abs(deltaY);
            return (absDeltaX == 2 && absDeltaY == 1) || (absDeltaX == 1 && absDeltaY == 2);
        } //knight moves
        return true;
    }
    public boolean bishopMove() {
        if (name.equalsIgnoreCase("bishop")) {
            return deltaX == deltaY || deltaX == -deltaY; //move diagonally
        } //bishop moves
        return true;
    }
    public boolean pawnMove() {
        if (name.equalsIgnoreCase("pawn")) {
            //diagonal taking
            if (Board.getPiece(legalMovePX * 64, legalMovePY * 64) != null && deltaX < -1 || deltaX > 1) { return false;}
            //cant move to empty square diagonally
            if (Board.getPiece(legalMovePX * 64, legalMovePY * 64) == null && deltaX != 0) { return false; }
            //cant take non-diagonally
            if (Board.getPiece(legalMovePX * 64, legalMovePY * 64) != null && deltaX == 0) { return false; }

            if (!isBlack) { //if piece is white
                if (!pieceMoved && deltaX != 0 && deltaY < -1) { return false; } //can't move two squares to take
                if (!pieceMoved && deltaY < -2) { return false; } //can't move triple or more on first move
                if (pieceMoved && deltaY < -1) { return false; } //can't move double or more after first move
                return deltaY < 0; //can't move backward
            } else { //if piece is black
                if (!pieceMoved && deltaX != 0 && deltaY > 1) { return false; } //can't move two squares to take
                if (!pieceMoved && deltaY > 2) { return false; } //can't move triple or more on first move
                if (pieceMoved && deltaY > 1) { return false; } //can't move double or more after first move
                return deltaY > 0; //can't move backward
            }
        } //pawn moves
        return true;
    }
    public boolean noObstruction() {
        if (name.equalsIgnoreCase("knight")) {
            return true;
        } // Knights can jump over other pieces, so no obstruction check is needed
        // Check for obstruction based on the direction of movement
        int xDirection = Integer.signum(deltaX);
        int yDirection = Integer.signum(deltaY);
        int currentX = pX + xDirection;
        int currentY = pY + yDirection;

        while (currentX != legalMovePX || currentY != legalMovePY) {
            if (Board.getPiece(currentX * 64, currentY * 64) != null) {
                if (castling) {
                    castling = false;
                    return true;
                }
                return false; // Obstruction found
            }
            currentX += xDirection;
            currentY += yDirection;
        }
        return true; // No obstruction found
    }
    public void kill() {
        ps.remove(this);
    }
}