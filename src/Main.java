import java.swing.*;
public class Main {
    public static void main(String[] args) {
        //new Board();
        Jframe frame = new JFrame();
        frame.setBounds(10, 10, 512, 512);
        JPanel panel = new JPanel()
        {

        };
        frame.add(panel);
        frame.setDefaultCloseOperation(3);
        frame.setVisible (true);
    }
}