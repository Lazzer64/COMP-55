import java.awt.image.BufferedImage;
import java.awt.Color;
import acm.graphics.*;

public class CombatDisplay extends Display{

    public static final double PLAYER_X = 100;
    public static final double PLAYER_Y = 125;
    public static final double ENEMY_X = 250;
    public static final double ENEMY_Y = PLAYER_Y;

    public static final Color HP_BAR_FILLED_COLOR = Color.GREEN;
    public static final Color HP_BAR_EMPTY_COLOR = Color.RED;
    public static final int HP_BAR_BUFFER = 5;
    public static final int HP_BAR_HEIGHT = 10;

    public CombatDisplay(GraphicsApplication program){
        super(program);
    }
    
    public void displayCombatField(Unit player, Unit enemy) {
        clean();
        displayUnit(PLAYER_X, PLAYER_Y, player);
        displayUnit(ENEMY_X, ENEMY_Y, enemy);
        showContents();
    }
    
    //FIXME replace with a real implementation
    public static final BufferedImage[] TEST_FRAMES = new BufferedImage[] {
        new SpriteSheet("SpriteSheets/AnimationSpritesheet.png", 32, 32).getSprite(0,0)
    };

    public void displayUnit(double x, double y, Unit unit) {

        //TODO change to get actual animation when implemented
        GObject u = new Animation(TEST_FRAMES,2);
        u.setLocation(x,y);

        double width = u.getWidth(), height = u.getHeight();

        int unitHp = unit.getHp();
        int unitMaxHp = unit.getMaxHp();
        double percentHp = 1.0*unitHp/unitMaxHp;

        double hpY = y + height + HP_BAR_BUFFER;

        GRect hpMax = new GRect(x, hpY, width, HP_BAR_HEIGHT);
        hpMax.setFilled(true);
        hpMax.setColor(HP_BAR_EMPTY_COLOR);

        GRect hp = new GRect(x, hpY, width*percentHp, HP_BAR_HEIGHT);
        hp.setFilled(true);
        hp.setColor(HP_BAR_FILLED_COLOR);

        objects.add(u);
        objects.add(hpMax);
        objects.add(hp);
    }
}
