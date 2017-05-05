package src.game;

import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.CardDeck;
import src.ija2016.model.cards.CardStack;
import src.ija2016.model.cards.TargetPack;
import src.ija2016.model.cards.WorkingPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.commands.PutStackCommand;
import src.commands.PullCardCommand;
import src.commands.TurnCardPullStackCommand;
import src.commands.CommandManager;
import src.game.Game;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        Game newGame = new Game();

        EventQueue.invokeLater(() -> {
            GUI ex = new GUI(newGame);
            ex.setVisible(true);
        });
        /*javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI(newGame);
            }
        });*/

    }

    /* public static void createGUI(Game game)
    {
        JFrame frame = new JFrame("Solitaire Klondike");
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.green);
        panel.setLayout(null);

        JLabel lab1[] = new JLabel[52];
        Card c;
        int y = 0;
        for( CardStack stack : game.workingPack) {
            for(int i = 0;i < stack.size();i++) {

                c = stack.get(i);
                if (c == null) break;
                if(c.isTurnedFaceUp())
                    lab1[i] = new JLabel(c.toString());
                else
                    lab1[i] = new JLabel("xxxx");
                lab1[i].setBounds(60*y, 25 * i, 60, 30);
                panel.add(lab1[i]);
            }
            y++;
        }
        //lab1.addMouseListener(new MouseAdapter()
        //{
        //    public void mousePressed(MouseEvent arg0)
        //    {
        //        lab1.setBounds(100,100,100,100);
        //    }
        //});
        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }*/
}
