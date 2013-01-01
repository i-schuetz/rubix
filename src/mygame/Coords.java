package mygame;

/**
 *
 * @author Ivan
 */
public class Coords {
    public int x;
    public int y;
    public int z;
    
    public Coords(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
     
    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
}
