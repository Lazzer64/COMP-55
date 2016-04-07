import java.util.ArrayList;
import acm.graphics.*;
public abstract class Display implements Displayable{

    GraphicsApplication program;
    ArrayList<GObject> objects;

    Display(GraphicsApplication program){
        this.program = program;
        this.objects = new ArrayList<GObject>();
    }

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
