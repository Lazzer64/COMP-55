import java.awt.image.BufferedImage;

public class GreenWizard extends Player {

    // The Green Wizard will only do damage after combo 2

    public GreenWizard(int hp, int attack, int defense, int energy) {
        super(hp, attack, defense, energy);
    }

    @Override
    public void attack(Unit target, Match match, int combo) {
        // Do nothing if the combo is less than 2
        if (combo < 2) return;
        // follow normal player behavior
        super.attack(target, match, combo);
    }

    @Override
    public double getMultiplier(int combo) {
        // Do nothing if the combo is less than 2
        if (combo < 2) return 0;
        // return a multiplier that is higher than normal players
        return 1 + combo;
    }

    public BufferedImage[] getAnimation() {
        switch(state) {
            case IDLE:
                return Animation.GreenPlayerIdle;
            case ATTACK:
                return Animation.GreenPlayerAttack;
            case DEATH:
                return Animation.GreenPlayerDie;
            default:
                return Animation.GreenPlayerIdle;
        }
    }
}
