package game;

import ija2016.model.cards.Card;
import ija2016.model.cards.CardDeck;
import ija2016.model.cards.CardStack;
import ija2016.model.cards.TargetPack;
import ija2016.model.cards.WorkingPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import commands.PutStackCommand;
import commands.PullCardCommand;
import commands.TurnCardPullStackCommand;
import commands.CommandManager;
import game.Game;

public class Main {
    public static void main(String[] args) {

        game.Game newGame = new Game();
        newGame.showStacks();
        newGame.cmdManager.executeCommand(new TurnCardPullStackCommand( newGame.pullPack, newGame.trashPack) );
        newGame.showStacks();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI(newGame);
            }
        });


    }

    public static void createGUI(Game game)
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
            for(int i = 0;;i++) {
                c = stack.get(i);
                if (c == null) break;
                if(c.isTurnedFaceUp())
                    lab1[i] = new JLabel(c.toString());
                else
                    lab1[i] = new JLabel("xxxx");
                lab1[i].setBounds(50*y, 20 * i, 60*y, 30*i);
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
    }
}
