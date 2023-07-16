/**
 * Starts new chess game
 *
 * @author (Piper)
 * @version (16/07/2023)
 */
import java.io.File;
public class New {
    public New() {
        emptyBoardList();
        resetVariables();
        resetPieces();
        Menu.panel.repaint();
    }
    public void emptyBoardList() {
        while (!Board.ps.isEmpty()) {
            Board.ps.removeFirst();
        }
    }
    public void resetVariables() {
        Piece.checkmated = false;
        Piece.resetTurn();
    }
    public static void resetPieces(){
        File file = new File("src/Positions/defaultPosition");
        Loader loader = new Loader();
        loader.loadFile(file);
        for (int piece = 0; piece < Loader.PIECE_AMOUNT; piece++) {
            Board.pieceList(Loader.pX[piece], Loader.pY[piece], Loader.isBlack[piece], false, Loader.name[piece]);
        } // Access the loaded values from the arrays
    }
}