import processing.core.PApplet;

import java.util.function.Function;
import java.util.function.Supplier;

public class Tile implements Drawable {
    private TileRenderer renderer;
    public final Vec2i pos;
    private boolean revealed;
    private boolean isMine;
    private boolean marked;
    private int num;
    private Tile(int x, int y){
        pos = new Vec2i(x, y);
    }
    private MouseInteractionElement interactionElement;

    public static Tile of(int x, int y, Boolean isMine){
        Tile t = new Tile(x, y);
        t.isMine = isMine;
        t.revealed = false;
        return t;
    }

    public Tile registerInteractiveElement(Function<Integer, Boolean> interaction){
        GameLogic.registerInteractiveElement(interactionElement = MouseInteractionElement.of(interaction));

        return this;
    }

    public TileRenderer initGraphics(int textSize, Board board, GameRenderer gameRenderer){
        renderer = new TileRenderer(textSize, board, gameRenderer);

        return renderer;
    }

    public int updateNum(Board board){
        num = 0;
        for (Vec2i v : Board.CHECKED_POSITIONS){
            Tile t = board.get(pos.add(v));
            if(t == null) continue;
            else if (t.isMine) num++;
        }

        return num;
    }

    public int getNum() {
        return num;
    }

    public boolean reveal() {
        revealed = true;

        return isMine;
    }

    public void toggleFlag() {
        marked = !marked;
    }

    public boolean isFlagged(){
        return marked;
    }

    public void setMine(boolean mine){
        isMine = mine;
    }

    public boolean isMine(){
        return isMine;
    }

    public boolean isRevealed(){
        return revealed;
    }

    @Override
    public ElementRenderer RENDERER() {
        return renderer;
    }

    public class TileRenderer implements ElementRenderer {
        private static final Color COVERED = Color.fromBrightness(1.0f);
        private static final Color REVEALED = Color.fromHSL(200, 1.0f, 0.5f);
        private static final Color MINE = Color.fromHSL(-15, 0.85f, 0.6f);
        private static final Color FLAG = Color.fromHSL(60, 0.45f, 0.6f);
        private final GameRenderer gameRenderer;
        private float textSize;
        private final Board board;

        TileRenderer(int textSize, Board board, GameRenderer renderer){
            gameRenderer = renderer;
            this.board = board;
            this.textSize = textSize;
        }

        public void setTextSize(int size) {
            textSize = size;
        }

        @Override
        public void drawAt(float x, float y) {
            Board.BoardRenderer boardRenderer = (Board.BoardRenderer) board.RENDERER();
            float boardTileSize = (float) boardRenderer.getWidth() / board.WIDTH;

            if (revealed) {
                if (!isMine) gameRenderer.fill(REVEALED);
                else gameRenderer.fill(MINE);
            } else {
                gameRenderer.fill(marked ? FLAG : COVERED);
            }

            //gameRenderer.stroke(0, 0);
            //gameRenderer.strokeWeight(0);
            float absolutePosX = x + pos.x * boardTileSize;
            float absolutePosY =  y + pos.y * boardTileSize;
            gameRenderer.rect(absolutePosX, absolutePosY, boardTileSize, boardTileSize);
            interactionElement.atPlacement((int) absolutePosX, (int) absolutePosY, (int) boardTileSize, (int) boardTileSize);

            if (revealed && num > 0 && !isMine) {
                gameRenderer.textSize(textSize);
                gameRenderer.fill(0);
                gameRenderer.textAlign(PApplet.CENTER, PApplet.CENTER);
                gameRenderer.text(num, absolutePosX + (boardTileSize / 2.0f), absolutePosY + (boardTileSize / 2.0f));
            }
        }
    }
}
