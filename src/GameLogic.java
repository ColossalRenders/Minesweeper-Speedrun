import processing.core.PApplet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameLogic {
    static ArrayList<MouseInteractionElement> interactionElements;
    static Board board;
    static GameRenderer renderer;
    private static boolean gameOver = false;
    public static String gameOverMessage = "Game Over";

    public static void setup(GameRenderer renderer) {
        gameOver = false;
        gameOverMessage = "Game Over";
        interactionElements = new ArrayList<>();
        int s = Math.max(1, askForIntInput("Size"));

        int m = askForIntInput("Number of Mines");

        m = Math.clamp(m, 0, s * s - 1);

        board = Board.ofDimensions(s, s).populate().scatterMines(m).update();
    }

    public static void registerInteractiveElement(MouseInteractionElement element) {
        interactionElements.addFirst(element);
    }

    private static int askForIntInput(String message){
        while (true) {
            try {
                return Integer.parseInt(JOptionPane.showInputDialog(message));
            } catch (Exception e) {

            }
        }
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public static void gameOver() {
        gameOver = true;
    }
}
