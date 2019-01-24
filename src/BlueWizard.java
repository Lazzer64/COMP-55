import java.awt.image.BufferedImage;

public class BlueWizard extends Player {

    // The Blue Wizard can only do damage.

    public BlueWizard(int hp, int attack, int defense, int energy) {
        super(hp, attack, defense, energy);
    }

    public void attack(Unit target, Match match, int combo) {
        // Calculate a magnitude of the attack from the match size and current combo
        int damage = (int) (match.size() * this.getMultiplier(combo));
        // Damage the target equal to the magnitude
        this.attack(target, damage);
    }

    public BufferedImage[] getAnimation() {
        switch(state) {
            case IDLE:
                return Animation.BluePlayerIdle;
            case ATTACK:
                return Animation.BluePlayerAttack;
            case DEATH:
                return Animation.BluePlayerDie;
            default:
                return Animation.BluePlayerIdle;
        }
    }
}
