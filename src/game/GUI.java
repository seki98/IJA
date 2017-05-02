package src.game;

/**
 * Created by mirec on 2.5.2017.
 */
import java.awt.EventQueue;
import javax.swing.JFrame;

public class GUI extends JFrame {
    private Game mygame;

    public GUI(Game mygame) {
        initGUI();
    }

    private void initGUI() {

        setTitle("Solitaire");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}