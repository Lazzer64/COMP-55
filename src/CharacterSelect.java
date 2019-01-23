import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.*;

public class CharacterSelect extends GraphicsPane {

    public static final int PLAYER_HEALTH = 100;
    public static final int PLAYER_ATTACK = 2;
    public static final int PLAYER_DEFENSE = 3;
    public static final int PLAYER_ENERGY = 100;

    private Main program;
    private GLabel selectText = new GLabel("Select a Class!");
    private GImage background = new GImage("SpriteSheets/background2.jpg");

    GImage orangeWizard = new GImage(Animation.OrangePlayerIdle[0]);
    GImage blueWizard = new GImage(Animation.BluePlayerIdle[0]);
    GImage greenWizard = new GImage(Animation.GreenPlayerIdle[0]);
    GImage yellowWizard = new GImage(Animation.YellowPlayerIdle[0]);

    private GImage[] selectors = new GImage[]{orangeWizard, blueWizard, greenWizard, yellowWizard};

    public CharacterSelect(Main app) {
        program = app;

        selectText.setLocation(program.WINDOW_WIDTH/2 - 50, 80);
        selectText.setColor(Color.WHITE);
        for (int i = 0; i < selectors.length; i++) {
            selectors[i].setLocation(25 + i * 100, 100);
        }
    }

    public void initBackground() {
        background.setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        program.add(background);
    }

    public void showContents() {
        initBackground();

        for (GImage selector : selectors) {
            program.add(selector);
        }
        program.add(selectText);
    }

    public void hideContents() {
        program.remove(background);
        program.remove(selectText);

        for (GImage selector : selectors) {
            program.remove(selector);
        }
    }

    public void mousePressed(MouseEvent e) {
        Player selection = null;

        if (program.getElementAt(e.getX(), e.getY()) == orangeWizard) {
            selection = new OrangeWizard(PLAYER_HEALTH, PLAYER_ATTACK, PLAYER_DEFENSE, PLAYER_ENERGY);
        }
        else if (program.getElementAt(e.getX(), e.getY()) == blueWizard) {
            selection = new BlueWizard(PLAYER_HEALTH, PLAYER_ATTACK, PLAYER_DEFENSE, PLAYER_ENERGY);
        }
        else if (program.getElementAt(e.getX(), e.getY()) == greenWizard) {
            selection = new GreenWizard(PLAYER_HEALTH, PLAYER_ATTACK, PLAYER_DEFENSE, PLAYER_ENERGY);
        }
        else if (program.getElementAt(e.getX(), e.getY()) == yellowWizard) {
            selection = new YellowWizard(PLAYER_HEALTH, PLAYER_ATTACK, PLAYER_DEFENSE, PLAYER_ENERGY);
        }

        if (selection != null) {
            program.switchToScreen(new Game(program, selection));
        }
    }
}
