/**
 * Prints out the chess board and pieces onto the frame
 *
 * @author (Piper Inns Hall)
 * @version (16/07/2023)
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.LinkedList;
import javax.swing.*;
public class Board extends JPanel implements ActionListener, MouseListener {
    static final int SQR_SIZE = 64;
    static final int SQR_AMOUNT = 8;
    static final int BOARD_SIZE = SQR_SIZE * SQR_AMOUNT;
    public static LinkedList<Piece> ps = new LinkedList<>(); //linked list of pieces
    public static Piece selectedPiece = null;
    Images img = new Images();
    private final Image[] images;
    boolean highlight;
    public Board() {
        images = img.loadImages();
        addMouseListener(this);
    }
    public static void pieceList(int pX, int pY, boolean isBlack, String name) {
        new Piece(pX, pY, isBlack, false,name, ps);
    }
    public void paint(Graphics g) {
        boolean white = true;
        for (int boardY = 0; boardY < SQR_AMOUNT; boardY++) {
            for (int boardX = 0; boardX < SQR_AMOUNT; boardX++) {
                if(white) {
                    g.setColor(Color.white);
                }
                else {
                    g.setColor(Color.decode("#769656"));
                }
                g.fillRect(boardX * SQR_SIZE, boardY * SQR_SIZE, SQR_SIZE, SQR_SIZE);
                white=!white;
            }
            white=!white;
        }
        if (highlight) {
            g.setColor(Color.yellow);
            if(selectedPiece.checkTurn())
                g.fillRect(selectedPiece.pX * SQR_SIZE, selectedPiece.pY * SQR_SIZE, SQR_SIZE, SQR_SIZE);
        }
        for (Piece p: ps) {
             int index = 0;
             if(p.name.equalsIgnoreCase("queen")) index = 0;
             if(p.name.equalsIgnoreCase("king")) index = 1;
             if(p.name.equalsIgnoreCase("rook")) index = 2;
             if(p.name.equalsIgnoreCase("knight")) index = 3;
             if(p.name.equalsIgnoreCase("bishop")) index = 4;
             if(p.name.equalsIgnoreCase("pawn")) index = 5;
             if(!p.isBlack) index+=6;
             g.drawImage(images[index], p.x, p.y, this);
         }
        if (Piece.checkmated) {
            int squareX = 256;
            int squareY = 128;
            int centerX = (BOARD_SIZE - squareX) / 2;
            int centerY = (BOARD_SIZE - squareY) / 2;
            int cornerRadius = 10; // Adjust the corner radius as needed

            Color tintedColor = new Color(0, 0, 0, 100); // Adjust the transparency and color as needed
            g.setColor(tintedColor);
            g.fillRect(0, 0, BOARD_SIZE, BOARD_SIZE);// Draw the tinted background rectangle

            g.setColor(Color.white);
            g.fillRoundRect(centerX, centerY, squareX, squareY, cornerRadius, cornerRadius); // rounded corners

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            String message = "Checkmate";
            int messageWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, (BOARD_SIZE - messageWidth) / 2, centerY + squareY / 2 - 16);

            String winner = Piece.winner ? "black" : "white";
            message = winner + " has won";
            messageWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, (BOARD_SIZE - messageWidth) / 2, centerY + squareY / 2);

            message = "File > New to play again";
            messageWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, (BOARD_SIZE - messageWidth) / 2, centerY + squareY / 2 + 16);
        }
    }
   public static Piece getPiece(int x, int y) {
        int pX = x / SQR_SIZE;
        int pY = y / SQR_SIZE;
        for (Piece p: ps) {
            if(p.pX == pX && p.pY == pY) return p;
        }
        return null;
    }
    public void actionPerformed(ActionEvent e) {}
    public void mousePressed(MouseEvent e) {
        if (!Piece.checkmated) {
            if (selectedPiece == null) {
                // No piece currently selected, attempt to select a piece
                selectedPiece = getPiece(e.getX(), e.getY());
                if (selectedPiece != null) highlight = true;
            } else {
                // A piece is already selected, attempt to move it
                selectedPiece.move(e.getX() / SQR_SIZE, e.getY() / SQR_SIZE);
                highlight = false;
                selectedPiece = null; // Deselect the piece after moving
            }
            this.repaint();
        }
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
}