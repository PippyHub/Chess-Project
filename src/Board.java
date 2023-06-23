/**
 * Prints out the chess board and pieces onto the frame
 *
 * @author (Piper Inns Hall)
 * @version (24/06/2023)
 */

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, MouseListener {
    public static LinkedList<Piece> ps = new LinkedList<>(); //linked list of pieces
    public static Piece selectedPiece = null;
    private final Image[] images;
    boolean highlight;
    public Board() {
        Images img = new Images();
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
        for (int boardY = 0; boardY < 8; boardY++) {
            for (int boardX = 0; boardX < 8; boardX++) {
                if (white) {
                    g.setColor(Color.white);
                }
                else {
                    g.setColor(Color.decode("#769656"));
                }
                g.fillRect(boardX * 64, boardY * 64, 64, 64);
                white=!white;
            }
            white=!white;
        }

        if (highlight) {
            g.setColor(Color.yellow);
            //if ((!selectedPiece.isBlack && !selectedPiece.publicBlackTurn) || (selectedPiece.isBlack && selectedPiece.publicBlackTurn)) {
                g.fillRect(selectedPiece.pX * 64, selectedPiece.pY * 64, 64, 64);
            //}
        }


        for (Piece p: ps) {
             int index = 0;
             if (p.name.equalsIgnoreCase("queen")) {
                 index = 0;
             }
             if (p.name.equalsIgnoreCase("king")) {
                 index = 1;
             }
             if (p.name.equalsIgnoreCase("rook")) {
                 index = 2;
             }
             if (p.name.equalsIgnoreCase("knight")) {
                 index = 3;
             }
             if (p.name.equalsIgnoreCase("bishop")) {
                 index = 4;
             }
             if (p.name.equalsIgnoreCase("pawn")) {
                 index = 5;
             }
             if (!p.isBlack) {
                 index+=6;
             }
             g.drawImage(images[index], p.x, p.y, this);
         }
    }
    public static Piece getPiece(int x, int y) {
        int pX = x / 64;
        int pY = y / 64;

       for (Piece p: ps) {
            if(p.pX == pX && p.pY == pY) {
                return p;
            }
        }
        return null;
    }
    public void actionPerformed(ActionEvent e) {}
    public void mousePressed(MouseEvent e) {
        if(selectedPiece == null) {
            // No piece currently selected, attempt to select a piece
            selectedPiece = getPiece(e.getX(), e.getY());
            if(selectedPiece != null) {
                highlight = true;
            }
        } else {
            // A piece is already selected, attempt to move it
            selectedPiece.move(e.getX() / 64, e.getY() / 64);
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