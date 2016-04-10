//mark's territory

public class Boss extends Enemy{

    private static final int HP = 150;
    private static final int DEFENSE = 4;
    private static final int MINATTACK=1;
    private static final int MAXATTACK=8;

    public Boss(UnitType type) { 
        super(HP, DEFENSE, type);
    }
}
