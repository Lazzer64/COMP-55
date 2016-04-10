import java.util.ArrayList;
import acm.graphics.*;
public abstract class Display {

    GraphicsApplication program;
    ArrayList<GObject> objects;
    ArrayList<Animation> animations;

    double x_adj = 0, y_adj = 0;
    boolean moved = true;

    Display(){
        this.objects = new ArrayList<GObject>();
        this.animations = new ArrayList<Animation>();
    }

    public void addObject(GObject x){
        if(objects.contains(x)) return;
        if(!animations.contains(x)) x.move(x_adj, y_adj);
        objects.add(x);
    }

    public void addAnimation(Animation x){
        if(animations.contains(x)) return;
        animations.add(x);
        addObject(x);
    }

    public abstract void update();

    public ArrayList<GObject> getObjects(){
        return objects;
    }

    public void setLocation(double x, double y) {
        for(GObject o: objects) o.move(x-x_adj,y-y_adj);
        x_adj = x;
        y_adj = y;
    }

}
