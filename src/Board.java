import javax.swing.*;

public class Board extends JFrame{
    public Board() {
        JFrame frame = new JFrame();
        frame.setBounds(10, 10, 512, 512);
        JPanel panel = new JPanel()
        {

        };
        this.add(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible (true);
    }
}