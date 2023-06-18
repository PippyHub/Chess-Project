/**
 * Prints out the chess board and pieces onto the frame
 *
 * @author (Piper Inns Hall)
 * @version (19/06/2023)
 */
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Board extends JFrame {
    LinkedList<Piece> ps = new LinkedList<>(); //linked list of pieces
    Image[] images = new Image[12];
    final int HEADER_OFFSET = 30;
    public Board() throws IOException {
        BufferedImage all = ImageIO.read(new File("src/ChessPiecesArray.png"));
        int index = 0;
        for(int y = 0; y < 120; y += 60) {
            for(int x = 0; x < 360; x += 60) {
                images[index] = all.getSubimage(x, y, 60, 60)
                        .getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                index++;
            }
        }

        Piece bKing = (new Piece(4, 0, true,"king", ps));
        Piece bQueen = (new Piece(3, 0, true,"queen", ps));
        Piece bBishop1 = (new Piece(2, 0, true,"bishop", ps));
        Piece bBishop2 = (new Piece(5, 0, true,"bishop", ps));
        Piece bKnight1 = (new Piece(1, 0, true,"knight", ps));
        Piece bKnight2 = (new Piece(6, 0, true,"knight", ps));
        Piece bRook1 = (new Piece(0, 0, true,"rook", ps));
        Piece bRook2 = (new Piece(7, 0, true,"rook", ps));
        Piece bPawn1 = (new Piece(0, 1, true,"pawn", ps));
        Piece bPawn2 = (new Piece(1, 1, true,"pawn", ps));
        Piece bPawn3 = (new Piece(2, 1, true,"pawn", ps));
        Piece bPawn4 = (new Piece(3, 1, true,"pawn", ps));
        Piece bPawn5 = (new Piece(4, 1, true,"pawn", ps));
        Piece bPawn6 = (new Piece(5, 1, true,"pawn", ps));
        Piece bPawn7 = (new Piece(6, 1, true,"pawn", ps));
        Piece bPawn8 = (new Piece(7, 1, true,"pawn", ps));

        Piece wKing = (new Piece(4, 7, false,"king", ps));
        Piece wQueen = (new Piece(3, 7, false,"queen", ps));
        Piece wBishop1 = (new Piece(2, 7, false,"bishop", ps));
        Piece wBishop2 = (new Piece(5, 7, false,"bishop", ps));
        Piece wKnight1 = (new Piece(1, 7, false,"knight", ps));
        Piece wKnight2 = (new Piece(6, 7, false,"knight", ps));
        Piece wRook1 = (new Piece(0, 7, false,"rook", ps));
        Piece wRook2 = (new Piece(7, 7, false,"rook", ps));
        Piece wPawn1 = (new Piece(0, 6, false,"pawn", ps));
        Piece wPawn2 = (new Piece(1, 6, false,"pawn", ps));
        Piece wPawn3 = (new Piece(2, 6, false,"pawn", ps));
        Piece wPawn4 = (new Piece(3, 6, false,"pawn", ps));
        Piece wPawn5 = (new Piece(4, 6, false,"pawn", ps));
        Piece wPawn6 = (new Piece(5, 6, false,"pawn", ps));
        Piece wPawn7 = (new Piece(6, 6, false,"pawn", ps));
        Piece wPawn8 = (new Piece(7, 6, false,"pawn", ps));

        JFrame frame = new JFrame();
        this.setBounds(10, 10, 512, 512 + HEADER_OFFSET);

        JPanel panel = new JPanel();
        frame.add(panel);
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible (true);
    }
    public void paint(Graphics g){
        boolean white = true;
        for(int boardY = 0; boardY < 8; boardY++){
            for(int boardX = 0; boardX < 8; boardX++){
                if(white){
                    g.setColor(Color.white);
                }
                else {
                    g.setColor(Color.decode("#769656"));
                }
                g.fillRect(boardX * 64, (boardY * 64) + HEADER_OFFSET, 64, 64);
                white=!white;
            }
            white=!white;


        }

        for(Piece p: ps) {
            int index = 0;
            if(p.name.equalsIgnoreCase("queen")){
                index = 0;
            }
            if(p.name.equalsIgnoreCase("king")){
                index = 1;
            }
            if(p.name.equalsIgnoreCase("rook")){
                index = 2;
            }
            if(p.name.equalsIgnoreCase("knight")){
                index = 3;
            }
            if(p.name.equalsIgnoreCase("bishop")){
                index = 4;
            }
            if(p.name.equalsIgnoreCase("pawn")){
                index = 5;
            }
            if(!p.isBlack){
                index+=6;
            }
            g.drawImage(images[index], p.pX * 64, p.pY * 64 + HEADER_OFFSET, this);
        }
    }
}