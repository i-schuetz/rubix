package mygame;

import java.util.List;
import mygame.Main.Dir;

/**
 *
 * @author Ivan
 */
public class SliceState {
    
    boolean canRotate = true; //TODO map slide - canrotate
    boolean rotating = false;
    float angleDeg;
    float currentAngle;
    Dir currentDir;
    List<Coords> currentRotatedCoords;
    List<Cube> slideCubes;
    boolean isInRightAngle;
    int whichRightAngle;
    int[] lastZoneChecked = new int[2];
}
