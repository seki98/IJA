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
    private Game myonegame;
    private Game mygames[] = new Game[4];

    private JLayeredPane backgroundPane = new JLayeredPane();
    private JLayeredPane oneGamePane = new JLayeredPane();
    private JLayeredPane multipleGamesPane[] = new JLayeredPane[4];

    private boolean layeron[] = new boolean[4];
    private int layerson = 0;

    // Background
    private JLabel backgroundImage = new JLabel(new ImageIcon("src/img/background.jpg"));

    public GUI() {
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

        MakeOneGame();

        pack();
        setVisible(true);
    }

    public void MakeOneGame(){
        this.myonegame = new Game();
        oneGamePane = new OneGameGUI(this.myonegame, this);
        oneGamePane.setBounds(0,0,860,660);
        backgroundPane.add(oneGamePane, 1, 0);
        layerson++;
    }

    public void FromOneToMultiple(){
        this.mygames[0] = myonegame;
        this.mygames[1] = new Game();

        multipleGamesPane[0] = new MultiGameGUI(this.mygames[0], this, 0);
        multipleGamesPane[1] = new MultiGameGUI(this.mygames[1], this, 1);

        multipleGamesPane[0].setBounds(0,0,430,330);
        multipleGamesPane[1].setBounds(430,0,430,330);

        oneGamePane.removeAll();
        backgroundPane.remove(oneGamePane);
        backgroundPane.add(multipleGamesPane[0], 1, 0);
        backgroundPane.add(multipleGamesPane[1], 1, 0);

        layerson++;
        layeron[0]=true;
        layeron[1]=true;
        repaint();
    }

    public void CloseGame(int layerid){
        if(layerson==2){
            multipleGamesPane[layerid].removeAll();
            backgroundPane.remove(multipleGamesPane[layerid]);
            layeron[layerid]=false;
            layerson--;

            int stillon = 0;
            for(int i=0; i<4; i++){
                if(layeron[i]){
                    break;
                }
                stillon++;
            }

            myonegame = mygames[stillon];

            multipleGamesPane[stillon].removeAll();
            backgroundPane.remove(multipleGamesPane[stillon]);
            layeron[stillon] = false;

            oneGamePane = new OneGameGUI(myonegame,this);
            oneGamePane.setBounds(0,0,860,660);
            backgroundPane.add(oneGamePane, 1,0);
            repaint();
        }
        else{
            multipleGamesPane[layerid].removeAll();
            backgroundPane.remove(multipleGamesPane[layerid]);
            layeron[layerid] = false;
            layerson--;
            repaint();
        }
    }

    public void AddAnotherGame(){
        if(layerson==4){
            JOptionPane.showMessageDialog(new JFrame(),"Maximum number of games is 4!");
        }
        else{
            int freeplace = 0;
            for(int f=0;f<4;f++){
                if(!layeron[f]){
                    freeplace=f;
                    break;
                }
            }

            mygames[freeplace] = new Game();
            multipleGamesPane[freeplace] = new MultiGameGUI(mygames[freeplace],this,freeplace);
            if(freeplace == 0){
                multipleGamesPane[0].setBounds(0,0,430,330);
            }
            else if (freeplace == 1){
                multipleGamesPane[1].setBounds(430,0,430,330);
            }
            else if (freeplace == 2){
                multipleGamesPane[2].setBounds(0,330,430,330);
            }
            else if (freeplace == 3){
                multipleGamesPane[3].setBounds(430,330,430,330);
            }
            backgroundPane.add(multipleGamesPane[freeplace], 1, 0);
            repaint();

            layeron[freeplace] = true;
            layerson++;
        }
    }
}
