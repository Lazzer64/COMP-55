import java.io.File;
//mark's territory
public class Boss extends Enemy{

    public Boss( File image) { 
        super(HP, DEFENSE, image);
        generateAttack(MINATTACK, MAXATTACK);
    }
    private static final int HP = 150;
    private static final int DEFENSE = 4;
    private static final int MINATTACK=1;
    private static final int MAXATTACK=8;
}
