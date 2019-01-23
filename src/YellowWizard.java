import java.awt.image.BufferedImage;

public class YellowWizard extends Player {

    public YellowWizard(int hp, int attack, int defense, int energy) {
        super(hp, attack, defense, energy);
    }

    public void attack(Unit target, Match match, int combo) {
        int damage = (int) (match.size() * this.getMultiplier(combo));

        if (match.getType() == TileType.YELLOW) {
            this.increaseEnergy(damage * 5);
        }
        else if (match.getType() == TileType.PINK) {
            this.heal(this.getMaxHp() * 2 * damage);
        }
        else {
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
