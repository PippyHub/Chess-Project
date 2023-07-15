/**
 * Prints out the chess board and pieces onto the frame
 *
 * @author (Piper Inns Hall)
 * @version (29/06/2023)
 */

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, MouseListener {
    static final int SQUARE_SIZE = 64;
    static final int SQUARE_AMOUNT = 8;
    public static LinkedList<Piece> ps = new LinkedList<>(); //linked list of pieces
    public static Piece selectedPiece = null;
    Images img = new Images();
    private final Image[] images;
    boolean highlight;
    public Board() {
        images = img.loadImages();
        pieceList();
        addMouseListener(this);
    }
    public void pieceList() {
        Piece bQueen = new Piece(3, 0, true, false,"queen", ps);
        Piece wQueen = new Piece(3, 7, false,false,"queen", ps);

        Piece bKing = new Piece(4, 0, true,false,"king", ps);
        Piece wKing = new Piece(4, 7, false,false,"king", ps);

        for (int r = 0; r < 8; r+=7) { //rooks in link list
            Piece bRook = new Piece(r, 0, true,false,"rook", ps);
            Piece wRook = new Piece(r, 7, false,false,"rook", ps);

        }
        for (int k = 1; k < 7; k+=5) { //knights in link list
            Piece bKnight = new Piece(k, 0, true,false,"knight", ps);
            Piece wKnight = new Piece(k, 7, false,false,"knight", ps);
        }
        for (int b = 2; b < 6; b+=3) { //bishops in link list
            Piece bBishop = new Piece(b, 0, true,false,"bishop", ps);
            Piece wBishop = new Piece(b, 7, false,false,"bishop", ps);
        }
        for (int p = 0; p < 8; p++) { //pawns in link list
            Piece bPawn = new Piece(p, 1, true,false,"pawn", ps);
            Piece wPawn = new Piece(p, 6, false,false,"pawn", ps);
        }
    }
    public void paint(Graphics g) {
        boolean white = true;
        for (int boardY = 0; boardY < SQUARE_AMOUNT; boardY++) {
            for (int boardX = 0; boardX < SQUARE_AMOUNT; boardX++) {
                if(white) {
                    g.setColor(Color.white);
                }
                else {
                    g.setColor(Color.decode("#769656"));
                }
                g.fillRect(boardX * SQUARE_SIZE, boardY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                white=!white;
            }
            white=!white;
        }
        if (highlight) {
            g.setColor(Color.yellow);
            if(selectedPiece.checkTurn())
                g.fillRect(selectedPiece.pX * SQUARE_SIZE, selectedPiece.pY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
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
            int cornerRadius = 10; // Adjust the corner radius as needed

            // Calculate the center position for the square
            int centerX = (getWidth() - squareX) / 2;
            int centerY = (getHeight() - squareY) / 2;

            // Draw the tinted background rectangle
            Color tintedColor = new Color(0, 0, 0, 100); // Adjust the transparency and color as needed
            g.setColor(tintedColor);
            g.fillRect(0, 0, SQUARE_SIZE * SQUARE_AMOUNT, SQUARE_SIZE * SQUARE_AMOUNT);

            g.setColor(Color.white);
            g.fillRoundRect(centerX, centerY, squareX, squareY, cornerRadius, cornerRadius); // Use fillRoundRect for rounded corners

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 24)); // Adjust the font size as needed
            String message = "Checkmate";
            int messageWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, (getWidth() - messageWidth) / 2, centerY + squareY / 2 - 16);

            String winner = Piece.winner ? "black" : "white";
            message = winner + " has won";
            messageWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, (getWidth() - messageWidth) / 2, centerY + squareY / 2);

            message = "File > New to play again";
            messageWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, (getWidth() - messageWidth) / 2, centerY + squareY / 2 + 16);
        }
    }
    public static Piece getPiece(int x, int y) {
        int pX = x / SQUARE_SIZE;
        int pY = y / SQUARE_SIZE;
        for (Piece p: ps) {
            if(p.pX == pX && p.pY == pY) return p;
        }
        return null;
    }
    public void actionPerformed(ActionEvent e) {}
    public void mousePressed(MouseEvent e) {
        if(selectedPiece == null) {
            // No piece currently selected, attempt to select a piece
            selectedPiece = getPiece(e.getX(), e.getY());
            if(selectedPiece != null) highlight = true;
        } else {
            // A piece is already selected, attempt to move it
            selectedPiece.move(e.getX() / SQUARE_SIZE, e.getY() / SQUARE_SIZE);
            highlight = false;
            selectedPiece = null; // Deselect the piece after moving
        }
        this.repaint();
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
}