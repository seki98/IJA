package src.game;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import src.commands.TurnCardPullStackCommand;
import src.ija2016.model.board.*;
import src.ija2016.model.cards.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
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
    private JLayeredPane lpane2 = new JLayeredPane();

    // Background
    private JLabel backgroundImage = new JLabel(new ImageIcon("src/img/background.jpg"));

    // Cards
    private JLabel WorkingStack[][] = new JLabel[7][13];
    private JLabel WorkingStackBack[] = new JLabel[7];
    private JLabel PullPackBackground = new JLabel();
    private JLabel PullPack[] = new JLabel[24];
    private JLabel TrashPackBackground = new JLabel();
    private JLabel TrashPack[] = new JLabel[24];
    private JLabel TargetC = new JLabel();
    private JLabel TargetD = new JLabel();
    private JLabel TargetH = new JLabel();
    private JLabel TargetS = new JLabel();

    //private CardStack WorkingStack[];
    private JLabel WorkingStack1Card[] = new JLabel[13];
    private JLabel WorkingStack2Card[] = new JLabel[13];
    private JLabel WorkingStack3Card[] = new JLabel[13];
    private JLabel WorkingStack4Card[] = new JLabel[13];
    private JLabel WorkingStack5Card[] = new JLabel[13];
    private JLabel WorkingStack6Card[] = new JLabel[13];
    private JLabel WorkingStack7Card[] = new JLabel[13];

    private int ShownPullPackCards = 0;
    private int ShownTrashPackCards = 0;
    private boolean MovingTrashStackCard = false;
    private Card MovingTrashStackCardC;

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

        TargetC = new JLabel(new ImageIcon("src/img/cards/empty_targetpack.png"));
        TargetD = new JLabel(new ImageIcon("src/img/cards/empty_targetpack.png"));
        TargetH = new JLabel(new ImageIcon("src/img/cards/empty_targetpack.png"));
        TargetS = new JLabel(new ImageIcon("src/img/cards/empty_targetpack.png"));

        TargetC.setBounds(380, 20, 100, 145);
        lpane.add(TargetC, 1, 0);

        TargetD.setBounds(500, 20, 100, 145);
        lpane.add(TargetD, 1, 0);

        TargetH.setBounds(620, 20, 100, 145);
        lpane.add(TargetH, 1, 0);

        TargetS.setBounds(740, 20, 100, 145);
        lpane.add(TargetS, 1, 0);

        PullPackBackground = new JLabel(new ImageIcon("src/img/cards/empty_workingpack.png"));
        PullPackBackground.setBounds(20, 20, 100, 145);
        lpane.add(PullPackBackground, 1, 0);
        PullPackBackground.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EmptyPullPackClicked();
            }
        });

        TrashPackBackground = new JLabel(new ImageIcon("src/img/cards/empty_workingpack.png"));
        TrashPackBackground.setBounds(140, 20, 100, 145);
        lpane.add(TrashPackBackground, 1, 0);

        for(int i = 0; i<7;i++) {
            WorkingStackBack[i] = new JLabel(new ImageIcon("src/img/cards/empty_workingpack.png"));
            WorkingStackBack[i].setBounds(20+(120*i), 200, 100, 145);
            lpane.add(WorkingStackBack[i], 1, 0);
        }

        Card c;
        for(int i=0; i<7; i++){
            for(int j=0;;j++){
                c = this.mygame.workingPack[i].get(j);
                if(c==null)
                    break;
                WorkingStack[i][j] = new JLabel(new ImageIcon(c.getFileName()));
                WorkingStack[i][j].setBounds(20+(120*i), 200 + (25 * j), 100, 145);
                lpane.add(WorkingStack[i][j], j+2, 0);
            }
        }

        iHelp = new JLabel(new ImageIcon("src/img/help-me.png"));
        iHelp.setBounds(260, 90, 96, 96);
        lpane.add(iHelp, 1, 0);

        lpane2.setBounds(0,0,860,665);
        lpane.add(lpane2, 30,0);

        paintPullStack();
        paintTrashStack();
        pack();
        setVisible(true);
    }

    private void EmptyPullPackClicked(){
        if(mygame.cmdManager.executeCommand(new TurnCardPullStackCommand(mygame.pullPack,mygame.trashPack))){
            paintPullStack();
            while(ShownTrashPackCards!=0){
                ShownTrashPackCards--;
                lpane.remove(TrashPack[ShownTrashPackCards]);
            }
            lpane.repaint();
        }
        else{
            System.out.print("Karta nebola presunuta."); // TODO!
        }
    }

    private void PullPackClicked(){
        if(mygame.cmdManager.executeCommand(new TurnCardPullStackCommand(mygame.pullPack,mygame.trashPack))){
            paintTrashStack();
            lpane.remove(PullPack[ShownPullPackCards-1]);
            ShownPullPackCards--;
            lpane.repaint();
        }
        else{
            System.out.print("Karta nebola presunuta."); // TODO!
        }
    }

    private void paintPullStack() {
        Card pullStackCard;
        for(int i = 0; ;i++) {
            pullStackCard = this.mygame.pullPack.get(i);
            if(pullStackCard == null) break;

            PullPack[i] = new JLabel(new ImageIcon(pullStackCard.getFileName()));
            PullPack[i].setBounds(20, 20, 100, 145);

            lpane.add(PullPack[i], i+2, 0);

            PullPack[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    PullPackClicked();
                }
            });

            ShownPullPackCards++;
        }
    }

    private void TrashStackCardClicked(Card c){
        MovingTrashStackCard = true;
        MovingTrashStackCardC = c;
    }

    private JLabel selectedCard = new JLabel();
    private JLayeredPane cardGameTable = null;
    private JPanel basePane = null;
    private int deltaX = 0;
    private int deltaY = 0;

    private void paintTrashStack(){
        Card TrashStackCard;
        TrashStackCard = mygame.trashPack.get(ShownTrashPackCards);
        if(TrashStackCard == null) return;

        MouseAdapter adapterik = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    selectedCard = TrashPack[ShownTrashPackCards-1];
                    lpane.remove(selectedCard);
                    lpane.revalidate();
                    lpane.repaint();

                    lpane2.add(selectedCard, 30, 0);
                    lpane2.revalidate();
                    lpane2.repaint();
                    deltaX = e.getXOnScreen() - selectedCard.getX();
                    deltaY = e.getYOnScreen() - selectedCard.getY();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    lpane2.remove(selectedCard);
                    lpane2.revalidate();
                    lpane2.repaint();

                    Component comp = lpane.findComponentAt(e.getPoint());

                    if(comp instanceof JLabel){
                        System.out.print(comp.getName());
                        System.exit(0);
                    }
                    selectedCard.setBounds(140,20,100,145);
                    lpane.add(selectedCard, ShownTrashPackCards+2,0);
                    lpane.revalidate();
                    lpane.repaint();
                    selectedCard = null;
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (selectedCard != null) {
                        int x = e.getXOnScreen() - deltaX;
                        int y = e.getYOnScreen() - deltaY;
                        selectedCard.setBounds(x,y,100,145);
                        lpane2.revalidate();
                        lpane2.repaint();
                    }
                }

        };

        TrashPack[ShownTrashPackCards] = new JLabel(new ImageIcon(TrashStackCard.getFileName()));
        TrashPack[ShownTrashPackCards].setBounds(140, 20, 100, 145);
        TrashPack[ShownTrashPackCards].addMouseListener(adapterik);
        TrashPack[ShownTrashPackCards].addMouseMotionListener(adapterik);
        /*TrashPack[ShownTrashPackCards].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(mygame.cmdManager.executeCommand(new TurnCardPullStackCommand(mygame.pullPack,mygame.trashPack))){
                    TrashStackCardClicked(TrashStackCard);
                }
                else{
                    System.out.print("Neco se nezdarilo.");
                }
            }
        });*/
        lpane.add(TrashPack[ShownTrashPackCards], ShownTrashPackCards+2, 0);
        ShownTrashPackCards++;
    }
}
