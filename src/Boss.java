import java.awt.image.BufferedImage;
//mark's territory

public class Boss extends Enemy{

    public Boss(int hp, int attack, int defense) { 
        super("Boss", hp, attack, defense);
    }


    public BufferedImage[] getAnimation() {
        switch(state){
            case IDLE:
                return Animation.boss1Idle;
            case ATTACK:
                return Animation.boss1Attack;
            case DEATH:
                return Animation.boss1Die;
            default:
                return Animation.boss1Idle;
        }
    }

}
