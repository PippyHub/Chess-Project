import javax.swing.*;
import java.awt.*;
public class Board extends JFrame{
    final int HEADER_OFFSET = 30;
    public Board() {
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