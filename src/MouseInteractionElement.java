import java.util.ArrayList;
import java.util.function.Supplier;

public class MouseInteractionElement {
    private int x;
    private int y;
    private int w;
    private int h;
    private final Supplier<Boolean> function;

    private MouseInteractionElement(Supplier<Boolean> behavior){
        function = behavior;
    }

    public static MouseInteractionElement of(Supplier<Boolean> behavior){
        return new MouseInteractionElement(behavior);
    }

    public MouseInteractionElement atPlacement(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        return this;
    }

    public boolean isInBound(int mouseX, int mouseY){
        return (mouseX >= x && mouseX < x + w) && (mouseY >= y && mouseY < y + h);
    }

    public boolean interact(){
        return function.get();
    }
}
