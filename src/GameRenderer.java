import processing.core.PApplet;

import java.util.Iterator;

public class GameRenderer extends PApplet {
    private boolean updateGraphics;
    int frame;

    @Override
    public void setup() {
        GameLogic.setup(this);
        int screenSize = Math.min(width, height);
        int boardWidth = Math.min(screenSize, 1000);
        GameLogic.board.initGraphics((width - boardWidth) / 2, 50, boardWidth, this);
        updateGraphics = true;
        frame = 0;
    }

    @Override
    public void settings() {
        fullScreen();
    }

    @Override
    public void draw() {
        textAlign(CENTER, CENTER);
        fill(Color.fromHSL(0, 1.0f, 0.5f));
        textSize(64);
        if (GameLogic.isGameOver()) {
            textSize(128);
            text("GAME OVER", (float) width / 2, (float) height / 2);
            return;
        }

        if (mousePressed) {
            Iterator<MouseInteractionElement> iter = GameLogic.interactionElements.iterator();

            while (iter.hasNext()) {
                MouseInteractionElement element = iter.next();

                if (element.isInBound(mouseX, mouseY)) {
                    if (element.interact()) iter.remove();

                    update();
                    break;
                }
            }
        }

        if (updateGraphics) {
            updateGraphics = false;
            background(0);
            frame ++;
            //color(16);
            ((StationaryElementRenderer) GameLogic.board.RENDERER()).draw();
        }
    }

    public void fill(Color color){
        fill(color.R, color.G, color.B, color.A);
    }

    private void update(){
        GameLogic.board.update();
        updateGraphics = true;
    }

    @Override
    public void mouseClicked() {
        System.out.println("e");
    }
}
