import processing.core.PApplet;

import java.util.Iterator;

public class GameRenderer extends PApplet {
    private boolean updateGraphics;
    int frame;

    @Override
    public void setup() {
        GameLogic.setup(this);
        int screenSize = Math.min(width, height);
        int boardWidth = Math.min(screenSize - 100, 1000);
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
            text(GameLogic.gameOverMessage, (float) width / 2, (float) height / 2);
            return;
        }

        if (mousePressed && mouseButton == PApplet.LEFT) {
            Iterator<MouseInteractionElement> iter = GameLogic.interactionElements.iterator();

            while (iter.hasNext()) {
                MouseInteractionElement element = iter.next();

                if (element.isInBound(mouseX, mouseY)) {
                    if (element.interact(mouseButton)) iter.remove();

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

            fill(255);
            textAlign(RIGHT);
            text(GameLogic.board.getNumRemaining(), width - 50, 50);
        }
    }

    @Override
    public void mouseReleased() {
        if(mouseButton == PApplet.RIGHT) {
            Iterator<MouseInteractionElement> iter = GameLogic.interactionElements.iterator();

            while (iter.hasNext()) {
                MouseInteractionElement element = iter.next();

                if (element.isInBound(mouseX, mouseY)) {
                    if (element.interact(mouseButton)) iter.remove();

                    update();
                    break;
                }
            }
        }
    }

    @Override
    public void keyReleased() {
        if(key == ENTER && GameLogic.isGameOver()){
            setup();
            updateGraphics = true;
        }
    }

    public void fill(Color color){
        fill(color.R, color.G, color.B, color.A);
    }

    private void update(){
        GameLogic.board.tick();
        updateGraphics = true;
    }

    @Override
    public void mouseClicked() {
        System.out.println("e");
    }
}
