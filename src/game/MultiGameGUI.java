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
import java.io.File;

public class MultiGameGUI extends JLayeredPane {

    private Game mygame;
    private GUI mygui;
    private int layerindex;

    // Cards
    private JLabel WorkingStack[][] = new JLabel[7][21];
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
    private boolean MovingTrashPackCard = false;
    private boolean MovingWorkingPack = false;
    private int MovingWorkingPackPI;
    private int MovingWorkingPackCI;
    private boolean iSelected = false;

    // Command icons
    private JLabel iNew = new JLabel();
    private JLabel iLoad = new JLabel();
    private JLabel iSave = new JLabel();
    private JLabel iReload = new JLabel();
    private JLabel iTrash = new JLabel();
    private JLabel iHint = new JLabel();
    private JLabel iUndo = new JLabel();
    private JLabel iUnselect = new JLabel();

    // Constructor
    public MultiGameGUI(Game mygamein, GUI mygui, int index){
        this.mygame = mygamein;
        this.mygui = mygui;
        this.layerindex = index;
        makeGameLayout();
    }

    // GUI JLayeredPane initialization
    private void makeGameLayout(){
        // initialize number of shown WorkingStackCards and TargetPackCards
        for(int no=0; no<7; no++)
            ShownWorkingPackCards[no]=0;
        for(int no=0; no<4; no++)
            ShownTargetPackCards[no]=0;

        // paint TargetPacks background
        for(int i=0; i<4; i++){
            TargetPackBackground[i] = new JLabel(new ImageIcon("lib/img/cards-mini/empty_"+(i+1)+".png"));
            TargetPackBackground[i].setBounds(190+(60*i), 10, 50, 72);
            this.add(TargetPackBackground[i], 1, 0);
            final int tgpb = i;
            TargetPackBackground[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    TargetPackClicked(tgpb);
                }
            });
        }

        // paint PullPack background
        PullPackBackground = new JLabel(new ImageIcon("lib/img/cards-mini/empty_workingpack.png"));
        PullPackBackground.setBounds(10, 10, 50, 72);
        add(PullPackBackground, 1, 0);
        PullPackBackground.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EmptyPullPackClicked();
            }
        });

        // paint TrashPack background
        TrashPackBackground = new JLabel(new ImageIcon("lib/img/cards-mini/empty_workingpack.png"));
        TrashPackBackground.setBounds(70, 10, 50, 72);
        add(TrashPackBackground, 1, 0);

        // paint WorkingStacks background
        for(int i = 0; i<7;i++) {
            WorkingStackBack[i] = new JLabel(new ImageIcon("lib/img/cards-mini/empty_workingpack.png"));
            WorkingStackBack[i].setBounds(10+(60*i), 100, 50, 72);
            add(WorkingStackBack[i], 1, 0);
            final int wsbi = i;
            WorkingStackBack[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    EmptyWorkingStackCardClicked(wsbi);
                }
            });
        }

        // paint command icon NEW
        iNew = new JLabel(new ImageIcon("lib/img/mini/new.png"));
        iNew.setBounds(127, 10, 16, 16);
        add(iNew, 1, 0);
        iNew.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mygui.AddAnotherGame();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                iNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        // paint command icon LOAD
        iLoad = new JLabel(new ImageIcon("lib/img/mini/load.png"));
        iLoad.setBounds(147, 10, 16, 16);
        add(iLoad, 1, 0);
        iLoad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mygui.LoadGame(layerindex);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                iLoad.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        // paint command icon SAVE
        iSave = new JLabel(new ImageIcon("lib/img/mini/save.png"));
        iSave.setBounds(167, 10, 16, 16);
        add(iSave, 1, 0);
        iSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // paint command icon LOAD
                final JFileChooser fc = new JFileChooser();
                //In response to a button click:
                int returnVal = fc.showOpenDialog(new JFrame());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    System.out.println("Saving to: " + file.getName() + ".");
                    mygame.saveGame(file.getName());
                } else {
                    System.out.println("Save command cancelled by user.");
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                iSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        // paint command icon RELOAD
        iReload = new JLabel(new ImageIcon("lib/img/mini/reload.png"));
        iReload.setBounds(167, 30, 16, 16);
        add(iReload, 1, 0);
        iReload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mygui.ReLoadGame(layerindex);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                iReload.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });


        // paint Minion
        iHint = new JLabel(new ImageIcon("lib/img/mini/hint.png"));
        iHint.setBounds(130, 35, 32, 32);
        add(iHint, 1, 0);
        iHint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(new JFrame(), mygame.getHintMessage());
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                iHint.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        // paint TRASH
        iTrash = new JLabel(new ImageIcon("lib/img/mini/trash.png"));
        iTrash.setBounds(167, 50, 16, 16);
        add(iTrash, 1, 0);
        iTrash.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mygui.CloseGame(layerindex);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                iTrash.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        // paint Undo
        iUndo = new JLabel(new ImageIcon("lib/img/mini/undo.png"));
        iUndo.setBounds(167, 70, 16, 16);
        add(iUndo, 1, 0);
        iUndo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mygame.cmdManager.undo();
                paintPullPack();
                paintTrashPack();
                paintWorkingStacks();
                paintTargetPack();
                repaint();
            }
            @Override
            public void mouseEntered(MouseEvent e){
                iUndo.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        // paint UNSELECT
        iUnselect = new JLabel(new ImageIcon("lib/img/mini/unselect.png"));
        iUnselect.setBounds(135, 75, 16, 16);
        iUnselect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClearOperations();
            }
            @Override
            public void mouseEntered(MouseEvent e){
                iUnselect.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        // paint all cards
        paintWorkingStacks();
        paintPullPack();
        paintTrashPack();
        paintTargetPack();
    }

    // ========================================================
    // -------------------- HELP FUNCTIONS --------------------
    // ========================================================

    private void ClearOperations(){
        if(iSelected){
            remove(iUnselect);
            iSelected = false;
            repaint();
        }
        this.MovingWorkingPack=false;
        this.MovingTrashPackCard=false;
    }

    private boolean isTopCardOfWorkingStack(int WorkingStackIndex, int cardindex){
        return (cardindex == (mygame.workingPack[WorkingStackIndex].size()-1));
    }

    // ===============================================================
    // -------------------- CLICK-EVENTS AUTOMATA --------------------
    // ===============================================================

    private void EmptyWorkingStackCardClicked(int i){
        if(this.MovingTrashPackCard){
            if(this.mygame.cmdManager.executeCommand(new PullCardCommand(this.mygame.workingPack[i], this.mygame.trashPack))){
                paintTrashPack();
                paintPullPack();
                paintWorkingStacks();
                repaint();
            }
            else{
                System.out.print("Karta nemohla byt presunuta!\n");
            }
        }
        else if(this.MovingWorkingPack){
            if(this.mygame.cmdManager.executeCommand(new PutStackCommand(mygame.workingPack[MovingWorkingPackPI],mygame.workingPack[i],mygame.workingPack[MovingWorkingPackPI].get(MovingWorkingPackCI)))){
                paintWorkingStacks();
                repaint();
            }
            else{
                System.out.print("Karta nemohla byt presunuta!\n");
            }
        }
        ClearOperations();
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
                    System.out.print("Karta nemohla byt presunuta!\n");
                }
            }
            else{
                System.out.print("Nebolo kliknute na vrchnu kartu workingPacku!\n");
            }
            ClearOperations();
        }
        else if(this.MovingWorkingPack){
            // presun karty alebo stacku na danu kartu
            if(this.mygame.cmdManager.executeCommand(new PutStackCommand(mygame.workingPack[MovingWorkingPackPI],mygame.workingPack[i],mygame.workingPack[MovingWorkingPackPI].get(MovingWorkingPackCI)))){
                paintWorkingStacks();
                repaint();
            }
            else{
                System.out.print("Karta nemohla byt presunuta!\n");
            }
            ClearOperations();
        }
        else {
            this.MovingWorkingPack = true;
            this.MovingWorkingPackPI = i;
            this.MovingWorkingPackCI = j;
            add(iUnselect, 1, 0);
            iSelected = true;
            repaint();
        }
    }

    private void TargetPackClicked(int i){
        if(this.MovingWorkingPack){
            if(isTopCardOfWorkingStack(MovingWorkingPackPI,MovingWorkingPackCI)){
                if(mygame.cmdManager.executeCommand(new PutToTargetPackCommand(mygame.workingPack[MovingWorkingPackPI],mygame.targetPack[i]))){
                    if(mygame.targetPack[0].size() == 13 && mygame.targetPack[1].size() == 13 &&
                            mygame.targetPack[2].size() == 13 && mygame.targetPack[3].size() == 13) {
                        JLabel won = new JLabel(new ImageIcon("lib/img/leo.jpg"));
                        won.setBounds(0,0,860,660);
                        add(won,50,0);
                        won.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                remove(won);
                                repaint();
                            }
                        });
                        repaint();
                        System.out.println("DONE");
                    }

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
        if((!MovingWorkingPack)&&(!MovingTrashPackCard)){
            if(mygame.cmdManager.executeCommand(new TurnCardPullStackCommand(mygame.pullPack,mygame.trashPack))){
                paintPullPack();
                paintTrashPack();
                repaint();
            }
            else{
                System.out.print("Karty z trashpacku neboli presunute do pullpacku!\n"); // TODO!
            }
        }
        ClearOperations();
    }

    private void PullPackClicked(){
        if((!MovingWorkingPack)&&(!MovingTrashPackCard)){
            if(mygame.cmdManager.executeCommand(new TurnCardPullStackCommand(mygame.pullPack,mygame.trashPack))){
                paintPullPack();
                paintTrashPack();
                repaint();
            }
            else{
                System.out.print("Karta z pullpacku nebola presunuta do trashpacku!\n"); // TODO!
            }
        }
        ClearOperations();
    }

    private void TrashPackClicked(){
        if((MovingTrashPackCard) || (MovingWorkingPack)){
            ClearOperations();
        }
        else{
            MovingTrashPackCard = true;
            add(iUnselect, 1, 0);
            iSelected = true;
            repaint();
        }
    }

    // ========================================================
    // -------------------- PAINTING PACKS --------------------
    // ========================================================

    private void paintPullPack() {
        for(int delete=0; delete<this.ShownPullPackCards; delete++){
            remove(PullPack[delete]);
        }
        ShownPullPackCards = 0;

        Card pullStackCard;
        for(int i = 0; i<mygame.pullPack.size();i++) {
            pullStackCard = this.mygame.pullPack.get(i);
            if(pullStackCard == null) break;

            PullPack[i] = new JLabel(new ImageIcon("lib/img/cards-mini/"+pullStackCard.getFileName()));
            PullPack[i].setBounds(10, 10, 50, 72);

            add(PullPack[i], i+2, 0);

            PullPack[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    PullPackClicked();
                }
            });

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

            TrashPack[i] = new JLabel(new ImageIcon("lib/img/cards-mini/"+TrashStackCard.getFileName()));
            TrashPack[i].setBounds(70, 10, 50, 72);
            TrashPack[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    TrashPackClicked();
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

                TargetPack[tgp][i] = new JLabel(new ImageIcon("lib/img/cards-mini/"+c.getFileName()));
                TargetPack[tgp][i].setBounds(190+(60*tgp), 10, 50, 72);;
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

    private void paintWorkingStacks(){
        for(int wpi=0; wpi<7; wpi++){
            for(int wpj=0; wpj<ShownWorkingPackCards[wpi]; wpj++){
                remove(WorkingStack[wpi][wpj]);
            }
        }

        for (int delete=0; delete<7; delete++)
            ShownWorkingPackCards[delete] = 0;

        final Card c[][] = new Card[7][21];
        for(int i=0; i<7; i++){
            for(int j=0;j<mygame.workingPack[i].size();j++){
                c[i][j] = this.mygame.workingPack[i].get(j);
                if(c[i][j]==null)
                    break;
                final int mi = i;
                final int mj = j;
                WorkingStack[i][j] = new JLabel(new ImageIcon("lib/img/cards-mini/"+c[i][j].getFileName()));
                WorkingStack[i][j].setName(c[i][j].toString());
                WorkingStack[i][j].setBounds(10+(60*i), 100 + (12 * j), 50, 72);
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
