package src.game;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import src.commands.TurnCardPullStackCommand;
import src.ija2016.model.board.*;
import src.ija2016.model.cards.*;

import java.awt.*;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.awt.event.*;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI extends JFrame {
    private Game mygame;

    private JLayeredPane backgroundPane = new JLayeredPane();
    private JLayeredPane oneGamePane = new JLayeredPane();
    private JLayeredPane multipleGamesPane[] = new JLayeredPane[4];

    // Background
    private JLabel backgroundImage = new JLabel(new ImageIcon("src/img/background.jpg"));

    public GUI(Game mygamein) {
        this.mygame = mygamein;

        setTitle("Solitaire Klondike");
        setPreferredSize(new Dimension(860,660));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon webIcon = new ImageIcon("src/img/icon_big.png");
        setIconImage(webIcon.getImage());

        setLayout(new BorderLayout());
        add(backgroundPane, BorderLayout.CENTER);

        backgroundImage.setBounds(0,0,860,660);
        backgroundPane.add(backgroundImage, 0, 0);

        oneGamePane = new OneGameGUI(this.mygame);
        oneGamePane.setBounds(0,0,860,660);
        backgroundPane.add(oneGamePane, 1, 0);

        /*
        multipleGamesPane[0] = new MultiGameGUI(this.mygame);
        multipleGamesPane[1] = new MultiGameGUI(this.mygame);
        multipleGamesPane[2] = new MultiGameGUI(this.mygame);
        multipleGamesPane[3] = new MultiGameGUI(this.mygame);
        multipleGamesPane[0].setBounds(0,0,430,330);
        multipleGamesPane[1].setBounds(430,0,430,330);
        multipleGamesPane[2].setBounds(0,330,430,330);
        multipleGamesPane[3].setBounds(430,330,430,330);
        backgroundPane.add(multipleGamesPane[0], 1, 0);
        backgroundPane.add(multipleGamesPane[1], 1, 0);
        backgroundPane.add(multipleGamesPane[2], 1, 0);
        backgroundPane.add(multipleGamesPane[3], 1, 0);
        */
        pack();
        setVisible(true);
    }
}
