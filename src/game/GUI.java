package src.game;
import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
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
    private JLabel HelpStackFrom = new JLabel();
    private JLabel HelpStackTo = new JLabel();
    private JLabel TargetC = new JLabel();
    private JLabel TargetD = new JLabel();
    private JLabel TargetH = new JLabel();
    private JLabel TargetS = new JLabel();

    private void makeMyNiceGUI(){
        setTitle("Solitaire Klondike");
        setPreferredSize(new Dimension(860,725));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon webIcon = new ImageIcon("src/img/icon_big.png");
        setIconImage(webIcon.getImage());

        setLayout(new BorderLayout());
        add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, this.getWidth(), this.getHeight());

        HelpStackFrom = new JLabel(new ImageIcon("src/img/xx.png"));
        HelpStackTo = new JLabel(new ImageIcon("src/img/xx.png"));
        TargetC = new JLabel(new ImageIcon("src/img/xx.png"));
        TargetD = new JLabel(new ImageIcon("src/img/xx.png"));
        TargetH = new JLabel(new ImageIcon("src/img/xx.png"));
        TargetS = new JLabel(new ImageIcon("src/img/xx.png"));

        backgroundImage.setBounds(0,0,860,725);
        lpane.add(backgroundImage, 0, 0);

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

        Card c;
        int y = 0;
        for(CardStack stack : this.mygame.workingPack) {
            for(int i = 0;;i++) {
                c = stack.get(i);
                if (c == null) break;

                WorkingStack[i] = new JLabel(new ImageIcon(c.getFileName()));
                WorkingStack[i].setBounds(20+(120*y), 200 + (30 * i), 100, 145);
                lpane.add(WorkingStack[i], i+1, 0);
            }
            y++;
        }

        pack();
        setVisible(true);
    }

}