import processing.core.PApplet;

import java.util.ArrayList;

public class GameLogic {
    static ArrayList<MouseInteractionElement> interactionElements;
    static Board board;
    static GameRenderer renderer;
    private static boolean gameOver = false;

    public static void setup(GameRenderer renderer) {
        interactionElements = new ArrayList<>();
        board = Board.ofDimensions(20, 20).populate().scatterMines(30);
    }

    public static void registerInteractiveElement(MouseInteractionElement element) {
        interactionElements.addFirst(element);
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public static void gameOver() {
        gameOver = true;
    }
}
