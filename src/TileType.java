import java.awt.Color;
import java.util.Random;
public enum TileType {
    RED, BLUE, GREEN;

    public static Color getColor(TileType t){
        switch(t){
            case RED:
                return Color.RED;
            case BLUE:
                return Color.BLUE;
            case GREEN:
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }

    public static TileType randomType() {

        Random r = new Random();
        int roll = r.nextInt(3);

        if(roll == 0) return TileType.RED;
        if(roll == 1) return TileType.BLUE;
        return TileType.GREEN;
    }


}

