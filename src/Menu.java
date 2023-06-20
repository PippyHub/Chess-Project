/**
 * Menu bar class
 *
 * @author (Piper Inns Hall)
 * @version (20/06/2023)
 */
import javax.swing.*;
import java.awt.*;
public class Menu extends JFrame {
    Board panel = new Board();
    public Menu() {
        setTitle("Chess");
        this.getContentPane().setPreferredSize(new Dimension(512, 512));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        contents();
        this.add(panel);

        this.pack();
        this.setVisible(true);
    }

    public void contents() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu;
        JMenuItem menuItem;

        menu = new JMenu("Menu");
        menuBar.add(menu);

        menuItem = new JMenuItem("menuItem");
        menu.add(menuItem);

        menuItem = new JMenuItem("menuItem2");
        menu.add(menuItem);

        this.setJMenuBar(menuBar);
    }
}