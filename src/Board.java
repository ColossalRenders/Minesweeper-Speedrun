public class Board implements Drawable {
    private BoardRenderer renderer;

    private final Tile[] tiles;
    public final int WIDTH;
    public final int HEIGHT;

    public static final Vec2i TL = new Vec2i(-1, -1);
    public static final Vec2i TM = new Vec2i( 0, -1);
    public static final Vec2i TR = new Vec2i( 1, -1);
    public static final Vec2i ML = new Vec2i(-1,  0);
    public static final Vec2i MM = new Vec2i( 0,  0);
    public static final Vec2i MR = new Vec2i( 1,  0);
    public static final Vec2i BL = new Vec2i(-1,  1);
    public static final Vec2i BM = new Vec2i( 0,  1);
    public static final Vec2i BR = new Vec2i( 1,  1);

    public static final Vec2i[] CHECKED_POSITIONS = {
            TL, TM, TR,
            ML,     MR,
            BL, BM, BR
    };

    /// Constructors, Factories, and Initializers

    protected Board(int w, int h){
        WIDTH = w;
        HEIGHT = h;
        tiles = new Tile[w * h];
    }

    public static Board ofDimensions(int w, int h){
        return new Board(w, h);
    }

    public Board populate(){
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                Tile t = Tile.of(x, y, false);
                tiles[x * WIDTH + y] = t;

                t.registerInteractiveElement(() -> {
                    if (t.reveal()) GameLogic.gameOver();
                    return true;
                });
            }
        }

        return this;
    }

    public Board scatterMines(int num){
        for(int i = 0; i < num; i++){
            int index = (int) (Math.random() * tiles.length);
            if(!tiles[index].isMine()) tiles[index].setMine(true);
            else continue;
        }

        return this;
    }

    public BoardRenderer initGraphics(int x, int y, int width, GameRenderer gameRenderer) {
        this.renderer = new BoardRenderer(x, y, width, gameRenderer);

        return renderer;
    }

    /// Tickers

    public void update(){
        for (Tile t : tiles){
            t.updateNum(this);
        }
    }

    /// Getters and Setters

    public Tile get(int x, int y){
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return null;

        return tiles[x * HEIGHT + y];
    }

    public Tile get(Vec2i pos){
        return get(pos.x, pos.y);
    }

    @Override
    public ElementRenderer RENDERER() {
        return renderer;
    }

    public class BoardRenderer implements StationaryElementRenderer {
        private final GameRenderer gameRenderer;
        private int x;
        private int y;
        private int width;

        BoardRenderer(int x, int y, int width, GameRenderer renderer){
            this.x = x;
            this.y = y;
            this.width = width;
            gameRenderer = renderer;

            float tileSize = (float) width / WIDTH;
            for (Tile t : tiles){
                t.initGraphics(32, Board.this, renderer);
            }
        }

        @Override
        public void draw() {
            for (Tile t : tiles){
                t.RENDERER().drawAt(x, y);
            }
        }

        public int getWidth(){
            return width;
        }

        @Override
        public void drawAt(float x, float y) {
            int xTemp = this.x;
            int yTemp = this.y;

            this.x = (int) x;
            this.y = (int) y;

            draw();

            this.x = xTemp;
            this.y = yTemp;
        }
    }
}
