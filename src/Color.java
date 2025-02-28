public class Color {
    public final float r;
    public final float g;
    public final float b;
    public final float a;

    public final int R;
    public final int G;
    public final int B;
    public final int A;

    protected Color(float r, float g, float b, float alpha){
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = alpha;
        R = R();
        G = G();
        B = B();
        A = A();
    }

    public static Color fromDecimal(float r, float g, float b){
        return new Color(r, g, b, 1.0f);
    }

    public static Color fromDecimal(float r, float g, float b, float alpha){
        return new Color(r, g, b, alpha);
    }

    public static Color fromHSL(float h, float s, float l, float a) {
        // Normalize hue to be in the range [0, 1]
        h = h % 360; // Hue is in degrees, but we need to handle cases where it's larger than 360
        h = h / 360.0f;

        // Ensure saturation and lightness are clamped to [0, 1]
        s = Math.max(0.0f, Math.min(1.0f, s));
        l = Math.max(0.0f, Math.min(1.0f, l));

        // Compute RGB from HSL
        float r, g, b;

        if (s == 0) {
            // If saturation is 0, the color is a shade of gray
            r = g = b = l;
        } else {
            float temp2 = l < 0.5f ? l * (1 + s) : (l + s) - (l * s);
            float temp1 = 2 * l - temp2;

            r = hueToRGB(temp1, temp2, h + 1.0f / 3.0f);
            g = hueToRGB(temp1, temp2, h);
            b = hueToRGB(temp1, temp2, h - 1.0f / 3.0f);
        }

        return new Color(r, g, b, a);
    }

    public static Color fromHSL(float h, float s, float l) {
        return fromHSL(h, s, l, 1.0f);
    }

    public static Color fromBrightness(float l){
        return fromDecimal(1.0f, 1.0f, 1.0f);
    }

    private static float hueToRGB(float t1, float t2, float t3) {
        if (t3 < 0) t3 += 1;
        if (t3 > 1) t3 -= 1;
        if (6 * t3 < 1) return t1 + (t2 - t1) * 6 * t3;
        if (2 * t3 < 1) return t2;
        if (3 * t3 < 2) return t1 + (t2 - t1) * (2.0f / 3.0f - t3) * 6;
        return t1;
    }

    private int R(){
        return Math.round(r * 255);
    }

    private int G(){
        return Math.round(g * 255);
    }

    private int B(){
        return Math.round(b * 255);
    }

    private int A(){
        return Math.round(a * 255);
    }
}
