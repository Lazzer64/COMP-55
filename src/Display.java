import java.util.ArrayList;
import acm.graphics.*;
public abstract class Display implements Displayable{

    GraphicsApplication program;
    ArrayList<GObject> objects;
    ArrayList<Animation> animations;

    double x_adj = 0, y_adj = 0;

    Display(GraphicsApplication program){
        this.program = program;
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

    public void update(){
        clean();
        for(Animation a: animations) {
            a.update();
            addObject(a);
        }
        repaint();
        showContents();
    }

    public void setLocation(double x, double y) {
        x_adj = x;
        y_adj = y;
        for(GObject o: objects) o.move(x,y);
    }

    public void repaint(){ }

    public void showContents(){
        for(GObject o: objects) program.add(o);
    }
    
    public void hideContents(){
        for(GObject o: objects) program.remove(o);
    }

    public void clean(){
        hideContents();
        objects = new ArrayList<GObject>();
    }
    
}
