/**
 * Menu bar class
 *
 * @author (Piper Inns Hall)
 * @version (19/06/2023)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Menu extends JFrame implements ActionListener{
    Board board = new Board();
    JFrame frame = new JFrame();
    public Menu(){

        this.setBounds(10, 10, 512, 512 + board.HEADER_OFFSET);

        menuBar();

        frame.setTitle("Chess");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e)
    {
        String cmd=e.getActionCommand();
        switch(cmd)
        {
            case "Quit" : System.exit(0);
                break;
            case "Save" : System.out.println("save");//save();
                break;
            case "Load" : System.out.println("load");//load();
                break;
            default : System.out.println("Invalid input");
        }
    }
    public void menuBar()
    {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("File");
        menuBar.add(menu);

        menuItem = new JMenuItem("Quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Load");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        frame.setJMenuBar(menuBar);
    }
}
