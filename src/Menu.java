/**
 * Menu bar class
 *
 * @author (Piper Inns Hall)
 * @version (16/07/2023)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Menu extends JFrame implements ActionListener {
    public static Board panel = new Board();
    public Menu() {
        setTitle("Chess");
        this.getContentPane().setPreferredSize(new Dimension(512, 512));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        contents();
        this.add(panel);

        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
    public void contents() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu;

        menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem menuItem;

        menuItem = new JMenuItem("New");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Load");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        this.setJMenuBar(menuBar);
    }
    public void actionPerformed(ActionEvent e) {
        String cmd=e.getActionCommand();
        switch (cmd) {
            case "New" -> new New();
            case "Save" -> new Save(Board.ps);
            case "Load" -> System.exit(0);//new Load();
            default -> System.out.println("Invalid input");
        }
    }
}