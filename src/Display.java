import java.util.ArrayList;
import acm.graphics.*;
public abstract class Display extends GCompound {

    ArrayList<Updatable> updatables;

    Display() {
        this.updatables = new ArrayList<Updatable>();
    }

    public void resize(double x) {
        scale(x);
    }

    public void addObject(GObject x){
        this.add(x);
    }

    public void removeObject(GObject x){
        this.remove(x);
    }

    public void addUpdatable(Updatable x){
        if(updatables.contains(x)) return;
        ArrayList<Updatable> ups = (ArrayList<Updatable>) updatables.clone();
        ups.add(x);
        updatables = ups;
    }

    public void removeUpdatable(Updatable x){
        if(!updatables.contains(x)) return;
        ArrayList<Updatable> ups = (ArrayList<Updatable>) updatables.clone();
        ups.remove(x);
        updatables = ups;
    }

    public void update(){
        for(Updatable u: updatables) u.update();
    }

}
