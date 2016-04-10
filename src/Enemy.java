import java.util.Random;
//mark's territory

public class Enemy extends Unit{

    private Random rng;
    private static final int HP = 100;
    private static final int DEFENSE = 2;
    private static final int MINATTACK=1;
    private static final int MAXATTACK=5;

    public Enemy(int hp, int defense, UnitType type){
    	super(hp, hp, 0, defense, type);
    }

    public Enemy(UnitType type) { 
        super(HP, HP, 0, DEFENSE, type);
        rng = new Random(System.currentTimeMillis());
        generateAttack(MINATTACK, MAXATTACK);
    }

    public void generateAttack(int min, int max){
    	
    setAttack(min + rng.nextInt(max-min+1));
    }
    
}
