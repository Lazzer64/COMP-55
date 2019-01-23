import java.awt.image.BufferedImage;

public class OrangeWizard extends Player {

    public OrangeWizard(int hp, int attack, int defense, int energy) {
        super(hp, attack, defense, energy);
    }

    public BufferedImage[] getAnimation() {
        switch(state) {
            case IDLE:
                return Animation.OrangePlayerIdle;
            case ATTACK:
                return Animation.OrangePlayerAttack;
            case DEATH:
                return Animation.OrangePlayerDie;
            default:
                return Animation.OrangePlayerIdle;
        }
    }
}
