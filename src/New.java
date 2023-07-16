import java.io.File;

/**
 * Starts new chess game
 *
 * @author (Piper)
 * @version (16/07/2023)
 */
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
        Load load = new Load();
        load.loadFile(file);
        for (int piece = 0; piece < Load.PIECE_AMOUNT; piece++) {
            System.out.println(Load.pX[piece]);
            Board.pieceList(Load.pX[piece], Load.pY[piece], Load.isBlack[piece], Load.name[piece]);
        } // Access the loaded values from the arrays
    }
}