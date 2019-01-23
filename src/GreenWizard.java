import java.awt.image.BufferedImage;

public class GreenWizard extends Player {

    public GreenWizard(int hp, int attack, int defense, int energy) {
        super(hp, attack, defense, energy);
    }

    @Override
    public void attack(Unit target, Match match, int combo) {
        if (combo < 2) return;
        super.attack(target, match, combo);
    }

    @Override
    public double getMultiplier(int combo) {
        if (combo < 2) return 0;
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
