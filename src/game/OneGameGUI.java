package src.game;

import src.commands.PullCardCommand;
import src.commands.PutStackCommand;
import src.commands.PutToTargetPackCommand;
import src.commands.TurnCardPullStackCommand;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.CardDeck;
import src.ija2016.model.cards.WorkingPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OneGameGUI extends JLayeredPane {

    private Game mygame;

    // Cards
    private JLabel WorkingStack[][] = new JLabel[7][13];
    private JLabel WorkingStackBack[] = new JLabel[7];
    private JLabel PullPackBackground = new JLabel();
    private JLabel PullPack[] = new JLabel[24];
    private JLabel TrashPackBackground = new JLabel();
    private JLabel TrashPack[] = new JLabel[24];
    private JLabel TargetPack[][] = new JLabel[4][13];
    private JLabel TargetPackBackground[] = new JLabel[4];

    // Current number of shown cards
    private int ShownPullPackCards = 0;
    private int ShownTrashPackCards = 0;
    private int ShownWorkingPackCards[] = new int[7];
    private int ShownTargetPackCards[] = new int[4];

    // Automata
    private boolean TrashPackCardSelected = false;
    private boolean WorkingPackCardSelected = false;
    private boolean MovingTrashPackCard = false;
    private boolean MovingWorkingPack = false;
    private int MovingWorkingPackPI;
    private int MovingWorkingPackCI;

    // Command icons
    private JLabel iHelp = new JLabel();

    public OneGameGUI(Game mygamein){
        this.mygame = mygamein;
        makeGameFrame();
    }

    private void ClearOperations(){
        this.MovingWorkingPack=false;
        this.MovingTrashPackCard=false;
    }

    private void makeGameFrame(){
        for(int no=0; no<7; no++)
            ShownWorkingPackCards[no]=0;
        for(int no=0; no<4; no++)
            ShownTargetPackCards[no]=0;

        for(int i=0; i<4; i++){
            TargetPackBackground[i] = new JLabel(new ImageIcon("src/img/cards/empty_targetpack.png"));
            TargetPackBackground[i].setBounds(380+(120*i), 20, 100, 145);
            this.add(TargetPackBackground[i], 1, 0);
            final int tgpb = i;
            TargetPackBackground[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    TargetPackClicked(tgpb);
                }
            });
        }

        PullPackBackground = new JLabel(new ImageIcon("src/img/cards/empty_workingpack.png"));
        PullPackBackground.setBounds(20, 20, 100, 145);
        add(PullPackBackground, 1, 0);
        PullPackBackground.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EmptyPullPackClicked();
            }
        });

        TrashPackBackground = new JLabel(new ImageIcon("src/img/cards/empty_workingpack.png"));
        TrashPackBackground.setBounds(140, 20, 100, 145);
        add(TrashPackBackground, 1, 0);

        for(int i = 0; i<7;i++) {
            WorkingStackBack[i] = new JLabel(new ImageIcon("src/img/cards/empty_workingpack.png"));
            WorkingStackBack[i].setBounds(20+(120*i), 200, 100, 145);
            add(WorkingStackBack[i], 1, 0);
            final int wsbi = i;
            WorkingStackBack[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    WorkingStackCardClicked(wsbi, -1);
                }
            });
        }

        iHelp = new JLabel(new ImageIcon("src/img/help-me.png"));
        iHelp.setBounds(260, 90, 96, 96);
        add(iHelp, 1, 0);

        paintWorkingStacks();
        paintPullPack();
        paintTrashPack();
    }

    private void paintWorkingStacks(){
        for(int wpi=0; wpi<7; wpi++){
            for(int wpj=0; wpj<ShownWorkingPackCards[wpi]; wpj++){
                remove(WorkingStack[wpi][wpj]);
            }
        }

        for (int delete=0; delete<7; delete++)
            ShownWorkingPackCards[delete] = 0;

        final Card c[][] = new Card[7][13];
        for(int i=0; i<7; i++){
            for(int j=0;j<mygame.workingPack[i].size();j++){
                c[i][j] = this.mygame.workingPack[i].get(j);
                if(c[i][j]==null)
                    break;
                final int mi = i;
                final int mj = j;
                WorkingStack[i][j] = new JLabel(new ImageIcon(c[i][j].getFileName()));
                WorkingStack[i][j].setName(c[i][j].toString());
                WorkingStack[i][j].setBounds(20+(120*i), 200 + (25 * j), 100, 145);
                add(WorkingStack[i][j], j+2, 0);
                WorkingStack[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        WorkingStackCardClicked(mi, mj);
                    }
                });
                ShownWorkingPackCards[i]++;
            }
        }
    }

    private boolean isTopCardOfWorkingStack(int WorkingStackIndex, int cardindex){
        int top = -1;
        for(int i=0; ;i++){
            if(mygame.workingPack[WorkingStackIndex].get(i) == null)
                break;
            top++;
        }
        // System.out.print("->       top="+top+"\n");
        // System.out.print("-> cardindex="+cardindex+"\n");
        return (top == cardindex);
    }

    private void WorkingStackCardClicked(int i, int j){
        if(this.MovingTrashPackCard){
            // presun karty z trashpacku do workingpacku
            if(isTopCardOfWorkingStack(i, j)){
                if(this.mygame.cmdManager.executeCommand(new PullCardCommand(this.mygame.workingPack[i], this.mygame.trashPack))){
                    paintTrashPack();
                    paintPullPack();
                    paintWorkingStacks();
                    repaint();
                }
                else{
                    System.out.print("Karta nemohla byt presunuta!\n\n");
                }
            }
            else{
                System.out.print("Nebolo kliknute na vrchnu kartu workingPacku!\n\n");
            }
            this.MovingTrashPackCard = false;
        }
        else if(this.MovingWorkingPack){
            // presun karty alebo stacku na danu kartu
            if(this.mygame.cmdManager.executeCommand(new PutStackCommand(mygame.workingPack[MovingWorkingPackPI],mygame.workingPack[i],mygame.workingPack[MovingWorkingPackPI].get(MovingWorkingPackCI)))){
                paintWorkingStacks();
                repaint();
            }
            else{
                System.out.print("Karta nemohla byt presunuta!\n\n");
            }

            this.MovingWorkingPack = false;
        }
        else {
            this.MovingWorkingPack = true;
            this.MovingWorkingPackPI = i;
            this.MovingWorkingPackCI = j;
        }
    }

    private void TargetPackClicked(int i){
        if(this.MovingWorkingPack){
            if(isTopCardOfWorkingStack(MovingWorkingPackPI,MovingWorkingPackCI)){
                if(mygame.cmdManager.executeCommand(new PutToTargetPackCommand(mygame.workingPack[MovingWorkingPackPI],mygame.targetPack[i]))){
                    paintWorkingStacks();
                    paintTargetPack();
                    repaint();
                }
                else{
                    System.out.print("Nebolo mozne presunut kartu do cieloveho balicka!\n"); // TODO!
                }
            }
            else{
                System.out.print("Do cieloveho balicka je mozne presunut iba 1 kartu!\n"); // TODO!
            }

        }
        else if(this.MovingTrashPackCard){
            if(mygame.cmdManager.executeCommand(new PutToTargetPackCommand(mygame.trashPack,mygame.targetPack[i]))){
                paintTrashPack();
                paintTargetPack();
                repaint();
            }
            else{
                System.out.print("Nebolo mozne presunut kartu do cieloveho balicka!\n"); // TODO!
            }
        }
        ClearOperations();
    }

    private void EmptyPullPackClicked(){
        if(mygame.cmdManager.executeCommand(new TurnCardPullStackCommand(mygame.pullPack,mygame.trashPack))){
            paintPullPack();
            paintTrashPack();
            repaint();
        }
        else{
            System.out.print("Karty z trashpacku neboli presunute do pullpacku!"); // TODO!
        }
    }

    private void PullPackClicked(){
        if(mygame.cmdManager.executeCommand(new TurnCardPullStackCommand(mygame.pullPack,mygame.trashPack))){
            paintPullPack();
            paintTrashPack();
            repaint();
        }
        else{
            System.out.print("Karta z pullpacku nebola presunuta do trashpacku!"); // TODO!
        }
    }

    private void paintPullPack() {
        for(int delete=0; delete<this.ShownPullPackCards; delete++){
            remove(PullPack[delete]);
        }
        ShownPullPackCards = 0;

        // System.out.print("\n== print pull pack ==\n");
        Card pullStackCard;
        for(int i = 0; i<mygame.pullPack.size();i++) {
            pullStackCard = this.mygame.pullPack.get(i);
            if(pullStackCard == null) break;

            PullPack[i] = new JLabel(new ImageIcon(pullStackCard.getFileName()));
            PullPack[i].setBounds(20, 20, 100, 145);

            add(PullPack[i], i+2, 0);

            PullPack[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    PullPackClicked();
                }
            });
            /*
            System.out.print("-> card: "+pullStackCard.toString()+" ");
            if(pullStackCard.isTurnedFaceUp())
                System.out.print("FACE UP");
            System.out.print("\n");
            */
            ShownPullPackCards++;
        }
    }

    private void paintTrashPack(){
        for(int trash=0; trash<this.ShownTrashPackCards; trash++){
            remove(TrashPack[trash]);
        }
        ShownTrashPackCards = 0;

        for(int i=0; i<mygame.trashPack.size(); i++){
            Card TrashStackCard;
            TrashStackCard = mygame.trashPack.get(i);
            if(TrashStackCard == null) return;

            TrashPack[i] = new JLabel(new ImageIcon(TrashStackCard.getFileName()));
            TrashPack[i].setBounds(140, 20, 100, 145);
            TrashPack[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(MovingTrashPackCard){
                        MovingTrashPackCard = false;
                        System.out.print("TrashPack Card unselected!\n");
                    }
                    else if(MovingWorkingPack){
                        MovingWorkingPack = false;
                        System.out.print("WorkingPack unselected!\n");
                    }
                    else{
                        MovingTrashPackCard = true;
                    }
                }
            });
            add(TrashPack[i], ShownTrashPackCards+2, 0);
            ShownTrashPackCards++;
        }
    }

    private void paintTargetPack(){
        for(int i=0; i<4; i++){
            for(int j=0; j<this.ShownTargetPackCards[i]; j++){
                remove(TargetPack[i][j]);
            }
        }

        for(int delete=0; delete<4; delete++)
            ShownTargetPackCards[delete]=0;

        for(int tgp=0; tgp<4; tgp++){
            final int tgpc = tgp;
            for(int i=0; i<mygame.targetPack[tgp].size(); i++){
                Card c;
                c = mygame.targetPack[tgp].get(i);
                if(c == null) return;

                TargetPack[tgp][i] = new JLabel(new ImageIcon(c.getFileName()));
                TargetPack[tgp][i].setBounds(380+(120*tgp), 20, 100, 145);;
                TargetPack[tgp][i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        TargetPackClicked(tgpc);
                    }
                });
                add(TargetPack[tgp][i], ShownTargetPackCards[tgp]+2, 0);
                ShownTargetPackCards[tgp]++;
            }
        }
    }

    /*@Override
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MovingTrashPackCard = false;
                System.out.print("TrashPack Card unselected!\n");
            }
        });
    }*/
}