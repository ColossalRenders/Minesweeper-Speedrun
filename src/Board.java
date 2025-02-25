public class Board {
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

    private Board(int w, int h){
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
                tiles[x * y] = Tile.of(x, y, false);
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

    public Tile get(int x, int y){
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return null;

        return tiles[x * HEIGHT + y];
    }

    public Tile get(Vec2i pos){
        return get(pos.x, pos.y);
    }
}
