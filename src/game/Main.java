package game;


public class Main {
    public static void main(String[] args) {

        game.GUISwing gui = new GUISwing();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.createGUI();
            }
        });


        game.Game newGame = new Game();
    }
}
