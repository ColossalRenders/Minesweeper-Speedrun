public class Color {
    public final float r;
    public final float g;
    public final float b;
    public final float a;
    public Color(float r, float g, float b, float alpha){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = alpha;
    }

    public Color(int r, int g, int b){
        new Color(r, g, b, 1.0f);
    }

    public int R(){
        return (int) (r * 255);
    }

    public int G(){
        return (int) (g * 255);
    }

    public int B(){
        return (int) (b * 255);
    }
}
