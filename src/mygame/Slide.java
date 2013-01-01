package mygame;

import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import mygame.Main.Axis;
import mygame.Main.Dir;

public class Slide {
    List<Coords> coords;
    private List<Coords> rotatedCoordsC;
    private List<Coords> rotatedCoordsCC;
    Axis axis;
    int side;
    Slide slide;

    int index;
    /****************************************************************************************************/
    boolean canRotate = true; //TODO map slide - canrotate
    boolean rotating = false;
    float currentAngleRad;
    Dir currentDir;
    List<Coords> currentRotatedCoords;
    List<Cube> slideCubes;
    boolean isInRightAngle;
    float whichRightAngle;
    Node pivot;
    Node slideNode;
    float thezone;
    /****************************************************************************************************/
    
    Slide(Axis axis, int number, int side, int index) {
        this(axis, number, 0, 0, side);

        pivot = new Node();
        this.index = index;
    }

    Slide(Axis axis, int number, int start1, int start2, int side) {
        this.axis = axis;
        this.side = side;
        coords = new ArrayList<Coords>();
       
        int x;
        int y;
        int z;

        if (axis == Axis.x) {
            x = number;
            System.out.println("X");
            if (side == 1) {
                coords.add(new Coords(x, start1, start2));
            } else {
            for (y = start1, z = start2; y < side; y++) { //behind (bottom to top)
                coords.add(new Coords(x, y, z));
            }
            y--;
            for (z = 1; z < side; z++) { //top (behind to front)
                coords.add(new Coords(x, y, z));
            }
            z--;
            for (--y; y >= 0; y--) { //front (top to bottom)
                coords.add(new Coords(x, y, z));
            }
            z--;
            for (y = 0; z > 0; z--) { //bottom (front to behind)
                coords.add(new Coords(x, y, z));
            }

            if (side - 2 > 0) {
                slide = new Slide(axis, number, start1 + 1, start2 + 1, side - 2);
            }}
        } else if (axis == Axis.y) {
            System.out.println("Y");
            y = number;
            if (side == 1) {
                coords.add(new Coords(start1, y, start2));
            } else {
            for (x = start1, z = start2; x < side; x++) { //behind (left to right)
                coords.add(new Coords(x, y, z));
            }
            x--;
            for (z = 1; z < side; z++) { //right side (behind to front)
                coords.add(new Coords(x, y, z));
            }
            z--;
            for (--x; x >= 0; x--) { //front (right to left)
                coords.add(new Coords(x, y, z));
            }
            z--;
            for (x = 0; z > 0; z--) { //left (front to behind)
                coords.add(new Coords(x, y, z));
            }

            if (side - 2 > 0) {
                slide = new Slide(axis, number, start1 + 1, start2 + 1, side - 2);
            }}
        } else if (axis == Axis.z) {
            System.out.println("Z");
            z = number;
            if (side == 1) {
                System.out.println("side==1 z, number:" + number + ", adding:" + new Coords(start2, start1, z));
                coords.add(new Coords(start2, start1, z));
            } else {
            for (y = start1, x = start2; y < side; y++) { //left (bottom to top)
                coords.add(new Coords(x, y, z));
            }
            y--;
            for (x = 1; x < side; x++) { //top (left to right)
                coords.add(new Coords(x, y, z));
            }
            x--;
            for (--y; y >= 0; y--) { //right (top to bottom)
                coords.add(new Coords(x, y, z));
            }
            x--;
            for (y = 0; x > 0; x--) { //bottom (right to left)
                coords.add(new Coords(x, y, z));
            }
            
            if (side - 2 > 0) {
                System.out.println("number:" + number + ", start1 + 1:" + (start1 + 1) +", start2 + 1:" + (start2 + 1) + ",side - 2:" + (side-2));
                slide = new Slide(axis, number, start1 + 1, start2 + 1, side - 2);
            }}
        } else {
            System.out.println("?");
        }
       
        rotatedCoordsC = new ArrayList<Coords>();
        rotatedCoordsCC = new ArrayList<Coords>();
        
        initRotatedCoordsC(rotatedCoordsC);
        initRotatedCoordsCC(rotatedCoordsCC);
    }

    private void initRotatedCoordsC(List<Coords> coordsList) {
        int coordsSize = coords.size();
        int offset = coordsSize - (side - 1);
//        rotatedCoordsC = new ArrayList<Coords>();
        for (int i = 0; i < coords.size(); i++) {
            coordsList.add(coords.get((i + offset) % coordsSize));
        }
        if (slide != null) {
            slide.initRotatedCoordsC(coordsList);
        }
    }

    private void initRotatedCoordsCC(List<Coords> coordsList) {
        int offset = side - 1;
        int coordsSize = coords.size();
//        rotatedCoordsCC = new ArrayList<Coords>();
        for (int i = 0; i < coords.size(); i++) {
            coordsList.add(coords.get((i + offset) % coordsSize));
        }
        if (slide != null) {
            slide.initRotatedCoordsCC(coordsList);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Slide around axis " + axis + "\ncoords: ");
        for (Coords c : coords) {
            sb.append(c.toString()).append(" ");
        }
        sb.append("\nrotatedCoordsC: ");
        for (Coords c : rotatedCoordsC) {
            sb.append(c.toString()).append(" ");
        }
        sb.append("\nrotatedCoordsCC: ");
        for (Coords c : rotatedCoordsCC) {
            sb.append(c.toString()).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    List<Coords> getCoords() {
        /**TODO INEFFICIENT *****************************************************************************************/
        List<Coords> copy = new ArrayList<Coords>();
        for (Coords c : coords) {
            copy.add(new Coords(c.x, c.y, c.z));
        }
        /*******************************************************************************************/
        if (slide != null) {
            copy.addAll(slide.getCoords());
        }
        return copy;
    }

    List<Coords> getRotatedCoordsC() {
        //quick fix
        if (axis == Axis.x) {
            return rotatedCoordsCC;
        }
        return rotatedCoordsC;
    }

    List<Coords> getRotatedCoordsCC() {
         //quick fix
        if (axis == Axis.x) {
            return rotatedCoordsC;
        }
        return rotatedCoordsCC;
    }
}
