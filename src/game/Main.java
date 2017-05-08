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
}
