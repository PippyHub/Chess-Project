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
        if (legalMove()) {
            moveType();
            this.pieceMoved = true;
            this.pX = pX;
            this.pY = pY;
            this.x = pX * 64;
            this.y = pY * 64;
            if (!this.castling) switchTurn();
            this.castling = false;
        }
    }
    public void moveType() {
        kingCheck();
        //Taking pieces
        if (Board.getPiece(clickX * 64, clickY * 64) != null)
            Board.getPiece(clickX * 64, clickY * 64).kill();
        //Promoting pawns
        if (name.equalsIgnoreCase("pawn"))
            if ((this.isBlack && clickY == 7) || (!this.isBlack && clickY == 0)) name = "queen";
    }
    public static void switchTurn() {
        isBlackTurn = !isBlackTurn;
    }

    public boolean legalMove() {
        if (!checkTurn()) return false;
        if (ownSquareMove()) return false;
        if (ownPieceMove()) return false;
        if (!queenMove()) return false;
        if (!kingMove()) return false;
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
        return Board.getPiece(clickX * 64, clickY * 64) != null &&
                Board.getPiece(clickX * 64, clickY * 64).isBlack == isBlack;//cannot take own pieces
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
            return true; // castling logic
        }
        return Math.abs(deltaX) <= 1 && Math.abs(deltaY) <= 1; // king moves
    }
    public boolean rookMove() {
        if (!name.equalsIgnoreCase("rook")) return true;
        return deltaX == 0 || deltaY == 0;
    } //rook moves
    public boolean knightMove() {
        if (!name.equalsIgnoreCase("knight")) return true;
        return Math.abs(this.deltaX) == 2 && Math.abs(this.deltaY) == 1 ||
                Math.abs(this.deltaX) == 1 && Math.abs(this.deltaY) == 2;
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
        if (name.equalsIgnoreCase("knight")) {
            return true;
        }

        int startX = pX;
        int startY = pY;
        int targetX = clickX;
        int targetY = clickY;

        int dx = Math.abs(targetX - startX);
        int dy = Math.abs(targetY - startY);
        int sx = startX < targetX ? 1 : -1;
        int sy = startY < targetY ? 1 : -1;
        int err = dx - dy;
        int err2;

        // Check for diagonal movement
        if (dx == dy) {
            while (startX != targetX && startY != targetY) {
                if (startX != pX || startY != pY) {
                    if (Board.getPiece(startX * 64, startY * 64) != null) {
                        return castling; // Check if castling
                    }
                }

                err2 = 2 * err;
                if (err2 > -dy) {
                    err -= dy;
                    startX += sx;
                }
                if (err2 < dx) {
                    err += dx;
                    startY += sy;
                }
            }
        }
        // Check for horizontal/vertical movement
        else if (dx == 0 || dy == 0) {
            while (startX != targetX || startY != targetY) {
                if (startX != pX || startY != pY) {
                    if (Board.getPiece(startX * 64, startY * 64) != null) {
                        return castling; // Check if castling
                    }
                }

                if (dx == 0) {
                    startY += sy;
                } else {
                    startX += sx;
                }
            }
        } else {
            return false; // Invalid move (neither horizontal/vertical nor diagonal)
        }

        return true; // No obstruction found
    }

    public boolean kingCheck() {
        boolean check = false;
        Piece opponentKing = null;
        for (Piece p : ps) {
            if (p.name.equalsIgnoreCase("king") && p.isBlack != this.isBlack) {
                opponentKing = p;
                break;
            }
        }
        for (Piece p : ps) {
            if (p.isBlack == this.isBlack) {
                p.tempSave();

                if (p == Board.selectedPiece) {
                    p.pX = clickX;
                    p.pY = clickY;
                }
                if (opponentKing != null) {
                    p.deltaX = opponentKing.pX - p.clickX;
                    p.deltaY = opponentKing.pY - p.clickY;
                    p.clickX = opponentKing.pX;
                    p.clickY = opponentKing.pY;
                }
                p.tempLoad();
            }
        }
        return true;
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

    public void kill() {
        ps.remove(this);
    }
}