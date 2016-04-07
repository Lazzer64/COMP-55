import java.io.File;
//mark's territory
public class Player extends Unit{

    public Player( File image) { 
        super(HP, HP, 0, DEFENSE, image);
    }
    private static final int HP = 200;
    private static final int DEFENSE = 3;
}
