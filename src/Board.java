/**
 * Prints out the chess board and pieces onto the frame
 *
 * @author (Piper Inns Hall)
 * @version (20/06/2023)
 */

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, MouseListener {
    public static LinkedList<Piece> ps = new LinkedList<>(); //linked list of pieces
    public static Piece selectedPiece = null;
    private Image[] images;
    public Board() {
        Images img = new Images();
        images = img.loadImages();

        pieceList();

        addMouseListener(this);
    }
    public void pieceList() {
        Piece bQueen = new Piece(3, 0, true,"queen", ps);
        Piece wQueen = new Piece(3, 7, false,"queen", ps);

        Piece bKing = new Piece(4, 0, true,"king", ps);
        Piece wKing = new Piece(4, 7, false,"king", ps);

        for (int r = 0; r < 8; r+=7) { //rooks in link list
            Piece bRook1 = new Piece(r, 0, true,"rook", ps);
            Piece wRook1 = new Piece(r, 7, false,"rook", ps);

        }
        for (int k = 1; k < 7; k+=5) { //knights in link list
            Piece bKnight1 = new Piece(k, 0, true,"knight", ps);
            Piece wKnight1 = new Piece(k, 7, false,"knight", ps);
        }
        for (int b = 2; b < 6; b+=3) { //bishops in link list
            Piece bBishop = new Piece(b, 0, true,"bishop", ps);
            Piece wBishop = new Piece(b, 7, false,"bishop", ps);
        }

        for(int p = 0; p < 8; p++) { //pawns in link list
            Piece bPawn = new Piece(p, 1, true,"pawn", ps);
            Piece wPawn = new Piece(p, 6, false,"pawn", ps);
        }
    }
    public void paint(Graphics g) {
        boolean white = true;
        for(int boardY = 0; boardY < 8; boardY++) {
            for(int boardX = 0; boardX < 8; boardX++) {
                if(white) {
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
         for(Piece p: ps) {
             int index = 0;
             if(p.name.equalsIgnoreCase("queen")) {
                 index = 0;
             }
             if(p.name.equalsIgnoreCase("king")) {
                 index = 1;
             }
             if(p.name.equalsIgnoreCase("rook")) {
                 index = 2;
             }
             if(p.name.equalsIgnoreCase("knight")) {
                 index = 3;
             }
             if(p.name.equalsIgnoreCase("bishop")) {
                 index = 4;
             }
             if(p.name.equalsIgnoreCase("pawn")) {
                 index = 5;
             }
             if(!p.isBlack) {
                 index+=6;
             }
             g.drawImage(images[index], p.x, p.y, this);
         }
    }
    public static Piece getPiece(int x, int y) {
        int pX = x / 64;
        int pY = y / 64;
        for(Piece p: ps) {
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
        } else {
            // A piece is already selected, attempt to move it
            selectedPiece.move(e.getX() / 64, e.getY() / 64);
            selectedPiece = null; // Deselect the piece after moving
        }

        this.repaint();
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
}