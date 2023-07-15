/**
 * Constructor for all chess pieces
 *
 * @author (Piper Inns Hall)
 * @version (29/06/2023)
 */
import java.util.LinkedList;

public class Piece {
    int x; //piece board pos x
    int y; //piece board pos y
    int pX; //piece x
    int pY; //piece y
    int clickX; //where mouse is clicked
    int clickY; //where mouse is clicked
    int deltaX; //change in x when moving piece
    int deltaY; //change in y when moving piece
    int tempPX, tempPY, tempDX, tempDY , tempCX, tempCY; //temporary values
    boolean isBlack;
    boolean pieceMoved;
    private static boolean isBlackTurn;
    String name;
    boolean castling;
    LinkedList<Piece> ps;
    public Piece(int pX, int pY, boolean isBlack, boolean pieceMoved, String n, LinkedList<Piece> ps) {
        this.x = pX * 64;
        this.y = pY * 64;
        this.pX = pX;
        this.pY = pY;
        this.isBlack = isBlack;
        this.pieceMoved = pieceMoved;
        this.ps = ps;
        this.name = n;
        ps.add(this);
    }
    public void move(int pX, int pY) {
        deltaX = pX - this.pX;
        deltaY = pY - this.pY;
        clickX = pX;
        clickY = pY;

        System.out.println(checkmate());

        if (legalMove(true)) {
            moveType();
            this.castling = false;
            this.pieceMoved = true;
            this.pX = pX;
            this.pY = pY;
            this.x = pX * 64;
            this.y = pY * 64;
        }
    }
    public void moveType() {
        if (!this.castling) switchTurn();
        pieceTake();
        pawnPromote();
    }
    public static void switchTurn() {
        isBlackTurn = !isBlackTurn; //switching turns
    }
    public void pieceTake() {
        Piece take = Board.getPiece(clickX * 64, clickY * 64);
        if (take != null) take.kill(); //Taking pieces
    }
    public void kill() {
        ps.remove(this);
    }
    public void pawnPromote() {
        if (name.equalsIgnoreCase("pawn"))
            if ((this.isBlack && clickY == 7) || (!this.isBlack && clickY == 0)) name = "queen"; //Promoting pawns
    }
    public boolean legalMove(boolean realMove) {
        if (realMove && !checkTurn()) return false;
        if (ownSquareMove()) return false;
        if (ownPieceMove()) return false;
        if (!queenMove()) return false;
        if (!kingMove()) return false;
        if (realMove && !resolveCheck()) return false;
        if (!rookMove()) return false;
        if (!knightMove()) return false;
        if (!bishopMove()) return false;
        if (!pawnMove()) return false;
        if (!boardBoundary()) return false;
        return noObstruction();
    }
    public boolean checkTurn() {
        return (isBlackTurn && this.isBlack) || (!isBlackTurn && !this.isBlack);
    }
    public boolean ownSquareMove() {
        return this.deltaX == 0 && this.deltaY == 0; //cannot move to own square
    }
    public boolean ownPieceMove() {
        Piece clickedPiece = Board.getPiece(clickX * 64, clickY * 64);
        return clickedPiece != null && clickedPiece.isBlack == isBlack; // cannot take own pieces
    }
    public boolean queenMove() {
        if (!name.equalsIgnoreCase("queen")) return true;
        return Math.abs(this.deltaX) == Math.abs(this.deltaY) || deltaX == 0 || deltaY == 0;
    } //queen moves
    public boolean kingMove() {
        if (!name.equalsIgnoreCase("king")) return true;
        if (!pieceMoved && deltaY == 0 && Math.abs(deltaX) == 2) {
            int rookX = (deltaX > 0) ? 7 : 0; // Determine the rook's starting position
            int rookY = pY; // Rook stays in the same row
            Piece rook = Board.getPiece(rookX * 64, rookY * 64);
            if (rook != null && rook.name.equalsIgnoreCase("rook") && !rook.pieceMoved) {
                this.castling = true;
                int newRookX = (deltaX > 0) ? pX + 1 : pX - 1;
                int newRookY = pY;
                rook.move(newRookX, newRookY);
            }
            return true;
        }
        return Math.abs(deltaX) <= 1 && Math.abs(deltaY) <= 1; // king moves
    }
    public boolean resolveCheck() {
        this.tempSave();
        Piece attackedPiece = Board.getPiece(clickX * 64, clickY * 64);
        boolean canCaptureAttacker = (attackedPiece != null && attackedPiece.isBlack != this.isBlack);
        this.pX = this.clickX;
        this.pY = this.clickY;
        if (kingInCheck() && !canCaptureAttacker) {
            this.tempLoad();
            return false;
        }
        this.tempLoad();
        return true;
    }
    public boolean kingInCheck() {
        Piece myKing = kingPos();
        return check(myKing, myKing.pX, myKing.pY);
    }
    public Piece kingPos() {
        for (Piece p : ps) {
            if (p.name.equalsIgnoreCase("king") && p.isBlack == this.isBlack) {
                return p;
            }
        }
        return null; // Return null if the king is not found
    }
    public boolean check(Piece myKing, int kingPosX, int kingPosY) {
        for (Piece p : ps) {
            if (this.isBlack != p.isBlack) {
                p.tempSave();
                if (myKing != null) {
                    p.deltaX = kingPosX - p.pX;
                    p.deltaY = kingPosY - p.pY;
                    p.clickX = kingPosX;
                    p.clickY = kingPosY;
                }
                if (p.legalMove(false)) {
                    p.tempLoad();
                    return true;
                }
                p.tempLoad();
            }
        }
        return false;
    }
    public boolean checkmate() {
        if (!kingInCheck()) return false; // Not in check, not checkmate
        for (Piece p : ps) {
            if (p.isBlack == isBlackTurn) { // Check moves for the current player's pieces
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        p.tempSave();
                        p.deltaX = x - p.pX;
                        p.deltaY = y - p.pY;
                        p.clickX = x;
                        p.clickY = y;
                        if (p.legalMove(true)) {
                            p.tempLoad();
                            return false; // At least one legal move is available
                        }
                        p.tempLoad();
                    }
                }
            }
        }
        return true; // No legal moves available, it's checkmate
    }
    public boolean rookMove() {
        if (!name.equalsIgnoreCase("rook")) return true;
        return deltaX == 0 || deltaY == 0;
    } //rook moves
    public boolean knightMove() {
        if (name.equalsIgnoreCase("knight")) {
        return Math.abs(this.deltaX) == 2 && Math.abs(this.deltaY) == 1 ||
                Math.abs(this.deltaX) == 1 && Math.abs(this.deltaY) == 2;
        }
        return true;
    } //knight moves
    public boolean bishopMove() {
        if (!name.equalsIgnoreCase("bishop")) return true;
        return this.deltaX == deltaY || deltaX == -deltaY;
    } //bishop moves
    public boolean pawnMove() {
        if (!name.equalsIgnoreCase("pawn")) return true;
        if (Board.getPiece(clickX * 64, clickY * 64) != null) {
            if (deltaX < -1 || deltaX > 1) return false; // diagonal taking
            if (deltaX == 0) return false; // can't take non-diagonally
        } else {
            if (deltaX != 0) return false; // can't move to empty square diagonally
        }
        if (!isBlack) { // if piece is white
            if (!pieceMoved && deltaX != 0 && deltaY < -1) return false;
            if ((!pieceMoved && deltaY < -2) || (pieceMoved && deltaY < -1)) return false;
            return deltaY < 0; // can't move backward
        } else { // if piece is black
            if (!pieceMoved && deltaX != 0 && deltaY > 1) return false;
            if ((!pieceMoved && deltaY > 2) || (pieceMoved && deltaY > 1)) return false;
            return deltaY > 0; // can't move backward
        }
    }
    public boolean boardBoundary() {
        return clickX <= 7 && clickY <= 7 && clickX >= 0 && clickY >= 0;
    }
    public boolean noObstruction() {
        if (name.equalsIgnoreCase("knight")) return true;
        int currentX = this.pX + Integer.signum(this.deltaX);
        int currentY = this.pY + Integer.signum(this.deltaY);
        while (Math.abs(currentX - clickX) > 0 || Math.abs(currentY - clickY) > 0) {
            if (Board.getPiece(currentX * 64, currentY * 64) != null) return this.castling; // Check if castling
            currentX += Integer.signum(this.deltaX);
            currentY += Integer.signum(this.deltaY);
        }
        return true; // No obstruction found
    }
    public void tempSave() {
        this.tempPX = this.pX;
        this.tempPY = this.pY;
        this.tempDX = this.deltaX;
        this.tempDY = this.deltaY;
        this.tempCX = this.clickX;
        this.tempCY = this.clickY;
    }
    public void tempLoad() {
        this.pX = this.tempPX;
        this.pY = this.tempPY;
        this.deltaX = this.tempDX;
        this.deltaY = this.tempDY;
        this.clickX = this.tempCX;
        this.clickY = this.tempCY;
    }
}