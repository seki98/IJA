package src.game;
import src.ija2016.model.board.*;
import src.ija2016.model.cards.*;

import java.awt.*;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI extends JFrame {
    private Game mygame;

    public GUI(Game mygamein) {
        this.mygame = mygamein;
        makeMyNiceGUI();
    }

    private JLayeredPane lpane = new JLayeredPane();

    // Background
    private JLabel backgroundImage = new JLabel(new ImageIcon("src/img/background.jpg"));

    // Cards
    private JLabel WorkingStack[] = new JLabel[52];
    private JLabel WorkingStackBack[] = new JLabel[7];
    private JLabel HelpStackFrom = new JLabel();
    private JLabel HelpStackTo = new JLabel();
    private JLabel TargetC = new JLabel();
    private JLabel TargetD = new JLabel();
    private JLabel TargetH = new JLabel();
    private JLabel TargetS = new JLabel();

    // Command icons
    private JLabel iHelp = new JLabel();

    private void makeMyNiceGUI(){
        setTitle("Solitaire Klondike");
        setPreferredSize(new Dimension(860,665));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon webIcon = new ImageIcon("src/img/icon_big.png");
        setIconImage(webIcon.getImage());

        setLayout(new BorderLayout());
        add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, this.getWidth(), this.getHeight());

        backgroundImage.setBounds(0,0,860,665);
        lpane.add(backgroundImage, 0, 0);

        HelpStackFrom = new JLabel(new ImageIcon("src/img/cards/xx.png"));
        HelpStackTo = new JLabel(new ImageIcon("src/img/cards/empty_workingpack.png"));
        TargetC = new JLabel(new ImageIcon("src/img/cards/empty_targetpack.png"));
        TargetD = new JLabel(new ImageIcon("src/img/cards/empty_targetpack.png"));
        TargetH = new JLabel(new ImageIcon("src/img/cards/empty_targetpack.png"));
        TargetS = new JLabel(new ImageIcon("src/img/cards/empty_targetpack.png"));

        HelpStackFrom.setBounds(20, 20, 100, 145);
        lpane.add(HelpStackFrom, 1, 0);

        HelpStackTo.setBounds(140, 20, 100, 145);
        lpane.add(HelpStackTo, 1, 0);

        TargetC.setBounds(380, 20, 100, 145);
        lpane.add(TargetC, 1, 0);

        TargetD.setBounds(500, 20, 100, 145);
        lpane.add(TargetD, 1, 0);

        TargetH.setBounds(620, 20, 100, 145);
        lpane.add(TargetH, 1, 0);

        TargetS.setBounds(740, 20, 100, 145);
        lpane.add(TargetS, 1, 0);

        for(int i = 0; i<7;i++) {
            WorkingStackBack[i] = new JLabel(new ImageIcon("src/img/cards/empty_workingpack.png"));
            WorkingStackBack[i].setBounds(20+(120*i), 200, 100, 145);
            lpane.add(WorkingStackBack[i], 1, 0);
        }

        Card c;
        int y = 0;
        for(CardStack stack : this.mygame.workingPack) {
            for(int i = 0;;i++) {
                c = stack.get(i);
                if (c == null) break;

                WorkingStack[i] = new JLabel(new ImageIcon(c.getFileName()));
                WorkingStack[i].setBounds(20+(120*y), 200 + (25 * i), 100, 145);
                lpane.add(WorkingStack[i], i+2, 0);
            }
            y++;
        }

        iHelp = new JLabel(new ImageIcon("src/img/help-me.png"));
        iHelp.setBounds(260, 90, 96, 96);
        lpane.add(iHelp, 1, 0);

        pack();
        setVisible(true);
    }

}
