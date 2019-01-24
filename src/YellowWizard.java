import java.awt.image.BufferedImage;

public class YellowWizard extends Player {

    // The Yellow Wizard gains additional energy but does less damage

    public YellowWizard(int hp, int attack, int defense, int energy) {
        super(hp, attack, defense, energy);
    }

    public void attack(Unit target, Match match, int combo) {
        // Calculate a magnitude of the attack from the match size and current combo
        int damage = (int) (match.size() * this.getMultiplier(combo));

        if (match.getType() == TileType.YELLOW) {
            // The magnitude will be scaled if the match type is *yellow*
            // Gain energy equal to the magnitude scaled UP
            this.increaseEnergy(damage * 5);
        }
        else if (match.getType() == TileType.PINK) {
            // Heal for the magnitude scaled DOWN
            this.heal(this.getMaxHp() * 2 * damage);
        }
        else {
            // Damage the target equal to the magnitude scaled DOWN
            this.attack(target, (int) (damage * 0.5));
        }
    }

    public BufferedImage[] getAnimation() {
        switch(state) {
            case IDLE:
                return Animation.YellowPlayerIdle;
            case ATTACK:
                return Animation.YellowPlayerAttack;
            case DEATH:
                return Animation.YellowPlayerDie;
            default:
                return Animation.YellowPlayerIdle;
        }
    }
}
