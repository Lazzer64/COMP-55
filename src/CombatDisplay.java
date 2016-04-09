import java.awt.image.BufferedImage;
import java.awt.Color;
import acm.graphics.*;

public class CombatDisplay extends Display{

    public static final Color HP_BAR_FILLED_COLOR = Color.GREEN;
    public static final Color HP_BAR_EMPTY_COLOR = Color.RED;
    public static final int HP_BAR_BUFFER = 5;
    public static final int HP_BAR_HEIGHT = 10;
    public static final int HP_BAR_WIDTH = 50;
    public static final int DISTANCE = 150;

    Player player;
    Enemy enemy;

    public CombatDisplay(GraphicsApplication program, Player player, Enemy enemy){
        super(program);
        this.player = player;
        this.enemy = enemy;
        displayUnit(0, 0, player);
        displayUnit(DISTANCE, 0, enemy);
    }

    public void updateEnemy(Enemy enemy){
        this.enemy = enemy;
    }

    public void repaint(){
        clean();
        for(Animation a: animations) addObject(a);
        drawHP(player, -HP_BAR_WIDTH/4, 32 + HP_BAR_BUFFER);
        drawHP(enemy, DISTANCE-HP_BAR_WIDTH/4, 32 + HP_BAR_BUFFER);
        showContents();
    }

    public void drawHP(Unit unit, double x, double y){

        int width = HP_BAR_WIDTH;
        int unitHp = unit.getHp();
        int unitMaxHp = unit.getMaxHp();
        double percentHp = 1.0*unitHp/unitMaxHp;

        GRect hpMax = new GRect(x, y, width, HP_BAR_HEIGHT);
        hpMax.setFilled(true);
        hpMax.setColor(HP_BAR_EMPTY_COLOR);

        GRect hp = new GRect(x, y, width*percentHp, HP_BAR_HEIGHT);
        hp.setFilled(true);
        hp.setColor(HP_BAR_FILLED_COLOR);

        addObject(hpMax);
        addObject(hp);
    }

    //FIXME replace with a real implementation
    public static final BufferedImage[] TEST_FRAMES = new BufferedImage[] {
        new SpriteSheet("SpriteSheets/AnimationSpritesheet.png", 32, 32).getSprite(0,0),
        new SpriteSheet("SpriteSheets/AnimationSpritesheet.png", 32, 32).getSprite(1,0),
        new SpriteSheet("SpriteSheets/AnimationSpritesheet.png", 32, 32).getSprite(2,0),
        new SpriteSheet("SpriteSheets/AnimationSpritesheet.png", 32, 32).getSprite(1,0)
    };

    public void displayUnit(double x, double y, Unit unit) {
        //TODO change to get actual animation when implemented
        Animation u = new Animation(TEST_FRAMES,20);
        u.setLocation(x,y);
        addAnimation(u);
    }
}
