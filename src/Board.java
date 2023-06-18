/**
 * Prints out the chess board and pieces onto the frame
 *
 * @author (Piper Inns Hall)
 * @version (18/06/2023)
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Board extends JFrame{
    LinkedList<Piece> ps = new LinkedList<>(); //linked list of pieces
    Image[] images = new Image[12];
    final int HEADER_OFFSET = 30;
    public Board() throws IOException {
        BufferedImage all = ImageIO.read(new File("src/ChessPiecesArray.png"));
        int index = 0;
        for(int y = 0; y < 120; y += 60) {
            for(int x = 0; x < 360; x += 60) {
                images[index] = all.getSubimage(x, y, 60, 60).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                index++;
            }
        }

        Piece wKing = (new Piece(2, 2, true,"king", ps));

        JFrame frame = new JFrame();
        this.setBounds(10, 10, 512, 512 + HEADER_OFFSET);

        JPanel panel = new JPanel();
        frame.add(panel);

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
                    g.setColor(Color.black);
                }
                g.fillRect(boardX * 64, (boardY * 64) + HEADER_OFFSET, 64, 64);
                white=!white;
            }
            white=!white;

            for(Piece p: ps) {
                int index = 0;
                if(p.name.equalsIgnoreCase("queen")){
                    index = 0;
                }
                if(p.name.equalsIgnoreCase("king")){
                    index = 1;
                }
                if(p.name.equalsIgnoreCase("bishop")){
                    index = 2;
                }
                if(p.name.equalsIgnoreCase("knight")){
                    index = 3;
                }
                if(p.name.equalsIgnoreCase("rook")){
                    index = 4;
                }
                if(p.name.equalsIgnoreCase("pawn")){
                    index = 5;
                }
                if(!p.isWhite){
                    index+=5;
                }
                g.drawImage(images[index], p.pX * 64, p.pY * 64, this);
            }
        }
    }
}