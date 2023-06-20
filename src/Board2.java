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

public class Board2 extends JPanel{
    public Board2() {

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
    }
}
