public class Vec2i {
    public final int x;
    public final int y;

    public Vec2i(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vec2i add(Vec2i vec){
        return new Vec2i(this.x + vec.x, this.y + vec.y);
    }

    public Vec2i sub(Vec2i vec) {
        return new Vec2i(this.x - vec.x, this.y - vec.y);
    }

    @Override
    public String toString() {
        return "[ " + x + ", " + y + "]";
    }
}
