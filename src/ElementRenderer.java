public class ElementRenderer {
    private int x;
    private int y;
    private int w;
    private int h;
    private GameRenderer renderer;

    public ElementRenderer(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void rect(float x, float y, float w, float h){
        renderer.color
        renderer.rect(x, y, w, h);
    }

    public ElementRenderer withRenderer(GameRenderer renderer){
        this.renderer = renderer;
        return this;
    }
}
