package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import commands.PutCommand;
import commands.CommandManager;

public class GUISwing
{

  public static void createGUI()
  {
    JFrame frame = new JFrame("Solitaire Klondike");
    frame.setPreferredSize(new Dimension(500, 500));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();
    panel.setBackground(Color.green);
    panel.setLayout(null);

    JLabel lab1 = new JLabel("card 1");
    lab1.setBounds(50, 50, 100, 100); 
    lab1.addMouseListener(new MouseAdapter()
    {
        public void mousePressed(MouseEvent arg0)
        {
          lab1.setBounds(100,100,100,100);
        }
    });
    panel.add(lab1);
    frame.getContentPane().add(panel);

    frame.pack();
    frame.setVisible(true);
  }


}
