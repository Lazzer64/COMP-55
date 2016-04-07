import java.awt.Color;
import acm.graphics.*;

public class CombatDisplay extends Display{

    public static final Color HP_BAR_FILLED_COLOR = Color.GREEN;
    public static final Color HP_BAR_EMPTY_COLOR = Color.RED;
    public static final int HP_BAR_HEIGHT = 25;

    public CombatDisplay(GraphicsApplication program){
        super(program);
    }
    
    public void displayCombatField(Unit player, Unit enemy) {
        clean();
        int x = 50, y = 50;
        displayUnit(x,y,player);
        displayUnit(Main.WINDOW_WIDTH - x - 75,y,enemy);
        showContents();
    }
    
    public void displayUnit(int x, int y, Unit unit) {

        int width = 75, height = 150; // TODO change rectangle to display unit's image
        int unitHp = unit.getHp();
        int unitMaxHp = unit.getMaxHp();

        GRect u = new GRect(x,y,width,height);

        GRect hpMax = new GRect(x, y+height, width, HP_BAR_HEIGHT);
        hpMax.setFilled(true);
        hpMax.setColor(HP_BAR_EMPTY_COLOR);

        GRect hp = new GRect(x, y+height, width*(1.0*unitHp/unitMaxHp), HP_BAR_HEIGHT);
        hp.setFilled(true);
        hp.setColor(HP_BAR_FILLED_COLOR);

        objects.add(u);
        objects.add(hpMax);
        objects.add(hp);
    }
}
