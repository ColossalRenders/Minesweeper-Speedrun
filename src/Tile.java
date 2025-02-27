public class Tile implements Drawable {
    private final Vec2i pos;
    private boolean revealed;
    private boolean isMine;
    private int num;
    private Tile(int x, int y){
        pos = new Vec2i(x, y);
    }

    public static Tile of(int x, int y, Boolean isMine){
        Tile t = new Tile(x, y);
        t.isMine = isMine;
        t.revealed = false;
        return t;
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

    public void setMine(boolean mine){
        isMine = mine;
    }

    public boolean isMine(){
        return isMine;
    }

    @Override
    public void draw(GameRenderer renderer) {

    }
}
