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
    final int HEADER_OFFSET = 30;
    public Board() throws IOException {
        LinkedList<Piece> ps = new LinkedList<>(); //linked list of pieces
        BufferedImage all = ImageIO.read(new File("src/Pieces.png"));
        Image[] imgs = new Image[12];
        int ind = 0;
        for(int y = 0; y < 400; y += 200) {
            for(int x = 0; x < 1200; x += 200) {
                imgs[ind] = all.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                ind++;
            }
        }

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
        }
    }
}