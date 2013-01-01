package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Main extends SimpleApplication {
    float offset;
    float size = 1;
    private int SIDE = 3;
    private Cube[][][] cubes = new Cube[SIDE][SIDE][SIDE];
    Node rubixPivot;
    
    enum Axis {
        x, y, z
    };

    enum Dir {
        cc, c
    }
    Slide currentSlide;
    private float[][] zones = {
        {0, 0.523598776f, 0},
        {1.047197551f, 2.094395102f, FastMath.PI / 2},
        {2.617993878f, 3.665191429f, FastMath.PI},
        {4.188790205f, 5.235987756f, FastMath.PI + (FastMath.PI / 2)},
        {5.759586532f, 6.283185307f, 0},
        {-0.523598776f, 0, 0},
        {-2.094395102f, -1.047197551f, -FastMath.PI / 2},
        {-3.665191429f, -2.617993878f, -FastMath.PI},
        {-5.235987756f, -4.188790205f, -(FastMath.PI + (FastMath.PI / 2))},
        {-6.283185307f, -5.759586532f, 0}
    };
    private Slide[] slides = new Slide[SIDE * 3];
    private Map<Axis, Integer> aOffsets = new HashMap<Axis, Integer>();
    Node rubixNode;
    List<Cube> selectedCubes = new ArrayList<Cube>();
    Axis selectedAxis = Axis.x; //axis of current selection
    Material transparent;
    private boolean shiftPressed = false;

    protected void initCrossHairs() {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
                settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    private void initCubes() {
//        for (int x = 0; x < 3; x++) { //left to right
//            for (int y = 0; y < 3; y++) { //bottom to top
//                for (int z = 0; z < 3; z++) { //behind to front
//                    //cubes[x][y][z] = new Cube("" + x + y + z);
//                    cubes[x][y][z] = new Cube(new Vector3f(x * CUBE_SIZE, y * CUBE_SIZE, z * CUBE_SIZE), CUBE_SIZE, assetManager);
//                }
//            }
//        }
        cubes[0][0][0] = new Cube(new Vector3f(0, 0, 0), size, assetManager, new Coords(0, 0, 0));
        cubes[1][0][0] = new Cube(new Vector3f(size, 0, 0), size, assetManager, new Coords(1, 0, 0));
        cubes[2][0][0] = new Cube(new Vector3f(size * 2, 0, 0), size, assetManager, new Coords(2, 0, 0));

        cubes[0][0][1] = new Cube(new Vector3f(0, 0, size), size, assetManager, new Coords(0, 0, 1));
        cubes[1][0][1] = new Cube(new Vector3f(size, 0, size), size, assetManager, new Coords(1, 0, 1));
        cubes[2][0][1] = new Cube(new Vector3f(size * 2, 0, size), size, assetManager, new Coords(2, 0, 1));

        cubes[0][0][2] = new Cube(new Vector3f(0, 0, size * 2), size, assetManager, new Coords(0, 0, 2));
        cubes[1][0][2] = new Cube(new Vector3f(size, 0, size * 2), size, assetManager, new Coords(1, 0, 2));
        cubes[2][0][2] = new Cube(new Vector3f(size * 2, 0, size * 2), size, assetManager, new Coords(2, 0, 2));

        cubes[0][1][0] = new Cube(new Vector3f(0, size, 0), size, assetManager, new Coords(0, 1, 0));
        cubes[1][1][0] = new Cube(new Vector3f(size, size, 0), size, assetManager, new Coords(1, 1, 0));
        cubes[2][1][0] = new Cube(new Vector3f(size * 2, size, 0), size, assetManager, new Coords(2, 1, 0));

        cubes[0][1][1] = new Cube(new Vector3f(0, size, size), size, assetManager, new Coords(0, 1, 1));
        cubes[1][1][1] = new Cube(new Vector3f(size, size, size), size, assetManager, new Coords(1, 1, 1));
        cubes[2][1][1] = new Cube(new Vector3f(size * 2, size, size), size, assetManager, new Coords(2, 1, 1));

        cubes[0][1][2] = new Cube(new Vector3f(0, size, size * 2), size, assetManager, new Coords(0, 1, 2));
        cubes[1][1][2] = new Cube(new Vector3f(size, size, size * 2), size, assetManager, new Coords(1, 1, 2)); //17
        cubes[2][1][2] = new Cube(new Vector3f(size * 2, size, size * 2), size, assetManager, new Coords(2, 1, 2));

        cubes[0][2][0] = new Cube(new Vector3f(0, size * 2, 0), size, assetManager, new Coords(0, 2, 0));
        cubes[1][2][0] = new Cube(new Vector3f(size, size * 2, 0), size, assetManager, new Coords(1, 2, 0));
        cubes[2][2][0] = new Cube(new Vector3f(size * 2, size * 2, 0), size, assetManager, new Coords(2, 2, 0));

        cubes[0][2][1] = new Cube(new Vector3f(0, size * 2, size), size, assetManager, new Coords(0, 2, 1));
        cubes[1][2][1] = new Cube(new Vector3f(size, size * 2, size), size, assetManager, new Coords(1, 2, 1));
        cubes[2][2][1] = new Cube(new Vector3f(size * 2, size * 2, size), size, assetManager, new Coords(2, 2, 1));

        cubes[0][2][2] = new Cube(new Vector3f(0, size * 2, size * 2), size, assetManager, new Coords(0, 2, 2));
        cubes[1][2][2] = new Cube(new Vector3f(size, size * 2, size * 2), size, assetManager, new Coords(1, 2, 2));
        cubes[2][2][2] = new Cube(new Vector3f(size * 2, size * 2, size * 2), size, assetManager, new Coords(2, 2, 2));
    }

    private Slide getSlide(Coords coords, Axis axis) {
        int offset = aOffsets.get(axis);
        //TODO axis -> integer, integer is used as position in coords (x=0, y=1, z=2) and side * integer = offset
        int value = 0;
        switch (axis) {
            case x:
                value = coords.x;
                break;
            case y:
                value = coords.y;
                break;
            case z:
                value = coords.z;
                break;
        }

        return slides[value + offset];
    }

    private void initSlides() {
        for (int i = 0; i < SIDE; i++) {
            slides[i] = new Slide(Axis.x, i, SIDE, i);
        }
        for (int i = 0; i < SIDE; i++) {
            slides[i + 3] = new Slide(Axis.y, i, SIDE, i + 3);
        }
        for (int i = 0; i < SIDE; i++) {
            slides[i + 6] = new Slide(Axis.z, i, SIDE, i + 6);
        }
    }

    public static void main(String[] args) {
        Main app = new Main();
        AppSettings setting= new AppSettings(true);
        setting.setTitle("Rubix");
        app.setShowSettings(false); 
        app.setSettings(setting);
        java.util.logging.Logger.getLogger("").setLevel(Level.INFO);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.White);
        
        aOffsets.put(Axis.x, 0);
        aOffsets.put(Axis.y, SIDE);
        aOffsets.put(Axis.z, SIDE * 2);

        transparent = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        transparent.setColor("m_Color", new ColorRGBA(.3f, .3f, 1, 0f));
        initCrossHairs();
        initCubes();
        initSlides();
        initCube();

        addInputKeys();
        
        rubixPivot.rotate(0,.5f,0);
        rubixPivot.rotate(.5f,0,0);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    private void setRotation(Dir dir) {
		//1. get cubes of slice (slice has coords)
		currentSlide.rotating = true;
		currentSlide.currentDir = dir;
		
//       attachCubesToCurrentSlice();

		float angle = currentSlide.currentAngleRad;

		float[] zone;
		currentSlide.isInRightAngle = false;
		for (int i = 0; i < zones.length; i++) {
			zone = zones[i];
			if (angle < zone[1] && angle > zone[0]) {
				currentSlide.thezone = zone[2];
			}
		}
    }

    private void rotateCurrent(float value) {
        if (currentSlide != null && currentSlide.rotating) {
            /**rotate visually the slice************************************************************************************************************************/
            float angleToRotate = value * speed * 5;
//            float angleToRotate = 0.005f;
            if (currentSlide.currentDir.equals(Dir.cc)) {
                angleToRotate *= -1;
            }
            if (currentSlide.axis == Axis.x) {
                currentSlide.pivot.rotate(angleToRotate, 0, 0);
            } else if (currentSlide.axis == Axis.y) {
                currentSlide.pivot.rotate(0, angleToRotate, 0);
            } else {
                currentSlide.pivot.rotate(0, 0, angleToRotate);
            }

            float currentRot = currentSlide.currentAngleRad;
            float result;

            if (currentSlide.currentDir.equals(Dir.c)) {
                result = (currentRot + angleToRotate);
                if (result > (FastMath.PI * 2)) {
                    result = result - (FastMath.PI * 2);
                }
                System.out.println("rotated to:" + result);
            } else {
                result = (currentRot + angleToRotate);
                if (result < -(FastMath.PI * 2)) {
                    result = FastMath.PI * 2 + result;
                }
            }
            currentSlide.currentAngleRad = result;

            /******************************************************************************************************************************************/
            //TODO if rotation is not in a zone canrotate == false for slides which intersect with this slide (!)
            /******************************************************************************************************************************************/
            /**find if angle is in right angle zone **********************************************************************************************************************/
            float[] zone;
            currentSlide.isInRightAngle = false;
            boolean refreshModel = false;
//                float absAngle = Math.abs(currentSlide.currentAngleDeg);
            for (int i = 0; i < zones.length && !currentSlide.isInRightAngle; i++) {
                zone = zones[i];
                if (currentSlide.currentAngleRad < zone[1] && currentSlide.currentAngleRad > zone[0]) {
                    currentSlide.isInRightAngle = true;
                    if (currentSlide.thezone != zone[2]) {
                        currentSlide.thezone = zone[2];
                        currentSlide.whichRightAngle = zone[2];
                        refreshModel = true;
                    } else {
                        System.out.println("same zone");
                    }
                }
            }

            /******************************************************************************************************************************************/
            /**refresh the model if it's in right angle zone **********************************************************************************************************************/
            if (refreshModel) {
                Coords c;
                Coords rc;
                Cube cube;
                if (currentSlide.currentDir == Dir.c) {
                    currentSlide.currentRotatedCoords = currentSlide.getRotatedCoordsC();
                } else {
                    currentSlide.currentRotatedCoords = currentSlide.getRotatedCoordsCC();
                }

                List<Coords> currentCoords = currentSlide.getCoords();
                List<Cube> copy = new ArrayList<Cube>();
                for (int i = 0; i < currentSlide.currentRotatedCoords.size(); i++) {
                    c = currentCoords.get(i);
                    copy.add(cubes[c.x][c.y][c.z]);
                }
                for (int i = 0; i < currentSlide.currentRotatedCoords.size(); i++) {
                    cube = copy.get(i);
                    rc = currentSlide.currentRotatedCoords.get(i);
                    cubes[rc.x][rc.y][rc.z] = cube;
                    cube.setCoords(rc.x, rc.y, rc.z);
                }
                System.out.println("#################################REFRESH MODEL END#############################");
            }
            /******************************************************************************************************************************************/
        }
    }
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            System.out.println("onAction:" + name);

            if (name.equals("rotate_test") && keyPressed) {
                cubes[0][0][2].detachAllChildren();

            } else if (name.equals("shift")) {
                shiftPressed = keyPressed;
            } 
            //picking******************************************************************************************************************************************************************/
            else if (name.equals("pick") && canSelectSlice() && !keyPressed) {
                CollisionResults results = new CollisionResults();
                Ray ray = new Ray(cam.getLocation(), cam.getDirection());
                rubixNode.collideWith(ray, results);

                if (results.size() > 0) {
                    CollisionResult closest = results.getClosestCollision();

                    Geometry geom = closest.getGeometry();
                    Cube cube = (Cube) geom.getParent();
                    Coords coords = cube.coords;

                    //if a selection is active (slide) and selected cube is in this slide,
                    if (!selectedCubes.isEmpty() && selectedCubes.contains(cube)) {
                        if (selectedAxis == Axis.x) {
                            selectedAxis = Axis.y;
                        } else if (selectedAxis == Axis.y) {
                            selectedAxis = Axis.z;
                        } else if (selectedAxis == Axis.z) {
                            selectedAxis = Axis.x;
                        }

                    } else {
                        selectedAxis = Axis.x; //default
                    }

                    //else: get slide for first axis for this cube
                    Slide slide = getSlide(coords, selectedAxis);

                    currentSlide = slide;

                    for (Cube cub : selectedCubes) {
                        cub.setSelected(false);
                    }

                    selectedCubes.clear();
                    
                    attachCubesToCurrentSlice();

                    for (Coords c : slide.getCoords()) {
                        Cube cub = cubes[c.x][c.y][c.z];
						selectedCubes.add(cub);
						cub.setSelected(true);
                    }
                }
            } 
            // end picking******************************************************************************************************************************************************************/
            
            else if (currentSlide != null && (name.equals("rotate_c") || name.equals("rotate_cc"))) {
                if (keyPressed) {   
                    if (name.equals("rotate_c")) {
                        setRotation(Dir.c);
                    } else if (name.equals("rotate_cc")) {
                        setRotation(Dir.cc);
                    }
                } else { //release 

                    currentSlide.rotating = false;

                    /**adjust graphically to right angle if is in right angle zone. The check is made in rotateCurrent()
                     ************************************************************************************************************************/
                    if (currentSlide.isInRightAngle) {
                        float rot = currentSlide.whichRightAngle - currentSlide.currentAngleRad;
                        currentSlide.currentAngleRad = currentSlide.whichRightAngle;

                        if (currentSlide.axis == Axis.x) {
                            Quaternion quat = new Quaternion();
                            quat.fromAngleAxis(currentSlide.currentAngleRad, new Vector3f(1, 0, 0));
                            currentSlide.pivot.setLocalRotation(quat);
                        } else if (currentSlide.axis == Axis.y) {
                            Quaternion quat = new Quaternion();
                            quat.fromAngleAxis(currentSlide.currentAngleRad, new Vector3f(0, 1, 0));
                            currentSlide.pivot.setLocalRotation(quat);
                        } else {
                            Quaternion quat = new Quaternion();
                            quat.fromAngleAxis(currentSlide.currentAngleRad, new Vector3f(0, 0, 1));
                            currentSlide.pivot.setLocalRotation(quat);
                        }
                    }
                }
            }
        }
    };

    private void attachCubesToCurrentSlice() {
        Node pivot = currentSlide.pivot;
        Node slideg = currentSlide.slideNode;
        List<Coords> slideCoords = currentSlide.getCoords();
        Cube cu;

		for (Coords c : slideCoords) {
			cu = cubes[c.x][c.y][c.z];

			Quaternion childRot = cu.getWorldRotation().clone();
			Quaternion slideRotInv = slideg.getWorldRotation().inverse();
			Vector3f newLocalTranslation = slideg.worldToLocal(cu.getWorldTranslation(), null);
			slideg.attachChild(cu);
			cu.setLocalTranslation(newLocalTranslation);
			Quaternion newRot = slideRotInv.mult(childRot);
			cu.setLocalRotation(newRot);
		}
		pivot.attachChild(slideg);
    }
    
    private boolean canSelectSlice() {
        return (currentSlide == null
                || /* slice == currentSlide.index ||*/ (Math.abs(currentSlide.currentAngleRad) == 0 || Math.abs(currentSlide.currentAngleRad) == FastMath.PI / 2
                || Math.abs(currentSlide.currentAngleRad) == FastMath.PI || Math.abs(currentSlide.currentAngleRad) == FastMath.PI + (FastMath.PI / 2)));
    }

    
    private void selectCube(Node slideNode, Cube cube) {
//        cube.setMaterial(transparent);
//        selectedCubes.add(cube);
        
//        Cube clon = (Cube) cube.clone();
//        clon.scale(1.5f);
//        clon.setMaterial(transparent);
//        clon.setQueueBucket(Bucket.Transparent);
//        cube.attachChild(clon);
//        selectedCubes.add(clon);
        
//        Vector3f centerCube = cube.getLocalTranslation().add(cube.getLocalScale().divide(2f));
//        Vector3f centerClon = clon.getLocalTranslation().add(clon.getLocalScale().divide(2f));
//        Vector3f diff = centerClon.subtract(centerCube).negate();
//
//        clon.move(diff.x, diff.y, diff.z);
    }
    
    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            float rotSpeed = value * speed * 5;
            if (name.equals("rotate_c")) {
                rotateCurrent(value);
            } else if (name.equals("rotate_cc")) {
                rotateCurrent(value);
            } else if (name.equals("rotate_all_x")) {
                if (shiftPressed) {
                    rubixPivot.rotate(rotSpeed, 0, 0);
                } else {
                    rubixPivot.rotate(-rotSpeed, 0, 0);
                }

            } else if (name.equals("rotate_all_y")) {
                if (shiftPressed) {
                    rubixPivot.rotate(0, rotSpeed, 0);
                } else {
                    rubixPivot.rotate(0, -rotSpeed, 0);
                }

            } else if (name.equals("rotate_all_z")) {
                if (shiftPressed) {
                    rubixPivot.rotate(0, 0, rotSpeed);
                } else {
                    rubixPivot.rotate(0, 0, -rotSpeed);
                }
            }
        }
    };

    private void initCube() {
        size = 1;

        offset = size * 3 / 2;

        List<Coords> coords;
        Node cube;
        Slide slide;
        Node slideNode;
        Node pivot;

        rubixNode = new Node();
        for (int i = 0; i < 9; i++) {
            slide = slides[i];
            coords = slide.getCoords();
            slideNode = new Node();
            for (Coords c : coords) {
                cube = cubes[c.x][c.y][c.z];

                slideNode.attachChild(cube);
            }
            pivot = new Node();
            pivot.attachChild(slideNode);
            rubixNode.attachChild(pivot);

            slide.pivot = pivot;
            slide.slideNode = slideNode;
        }

        rubixPivot = new Node();
        rubixPivot.attachChild(rubixNode);
        
        rubixNode.setLocalTranslation(new Vector3f(-SIDE/2f, -SIDE/2f, -SIDE/2f));
        rootNode.attachChild(rubixPivot);

        slides[0].slideNode.move(0, -offset, -offset); //left
        slides[0].pivot.move(0, offset, offset);
        slides[1].slideNode.move(0, -offset, -offset);
        slides[1].pivot.move(0, offset, offset);
        slides[2].slideNode.move(0, -offset, -offset); //right
        slides[2].pivot.move(0, offset, offset);
        slides[3].slideNode.move(-offset, 0, -offset);//bottom
        slides[3].pivot.move(offset, 0, offset);
        slides[4].slideNode.move(-offset, 0, -offset);//
        slides[4].pivot.move(offset, 0, offset);
        slides[5].slideNode.move(-offset, 0, -offset);//top
        slides[5].pivot.move(offset, 0, offset);
        slides[6].slideNode.move(-offset, -offset, 0); //behind
        slides[6].pivot.move(offset, offset, 0);
        slides[7].slideNode.move(-offset, -offset, 0);
        slides[7].pivot.move(offset, offset, 0);
        slides[8].slideNode.move(-offset, -offset, 0); //front
        slides[8].pivot.move(offset, offset, 0);
    }

    private void addInputKeys() {
//        inputManager.clearMappings();
        inputManager.deleteMapping("FLYCAM_StrafeLeft");
        inputManager.deleteMapping("FLYCAM_StrafeRight");
        inputManager.deleteMapping("FLYCAM_Forward");
        inputManager.deleteMapping("FLYCAM_Backward");
        inputManager.deleteMapping("FLYCAM_Rise");
        inputManager.deleteMapping("FLYCAM_Lower");

        inputManager.addMapping("rotate_c", new KeyTrigger(keyInput.KEY_Q));
        inputManager.addMapping("rotate_cc", new KeyTrigger(keyInput.KEY_W));

        inputManager.addMapping("rotate_all_x", new KeyTrigger(keyInput.KEY_X));
        inputManager.addMapping("rotate_all_y", new KeyTrigger(keyInput.KEY_Y));
        inputManager.addMapping("rotate_all_z", new KeyTrigger(keyInput.KEY_Z));
        inputManager.addMapping("rotate_test", new KeyTrigger(keyInput.KEY_I));

        inputManager.addMapping("shift", new KeyTrigger(keyInput.KEY_LSHIFT));

        inputManager.addMapping("pick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

        inputManager.addListener(actionListener, "Shoot");

        inputManager.addListener(analogListener, new String[]{"rotate_c", "rotate_cc", "rotate_all_x", "rotate_all_y", "rotate_all_z"});
        inputManager.addListener(actionListener, new String[]{"rotate_c", "rotate_cc", "rotate_all_x", "rotate_all_y", "rotate_all_z", "shift", "pick", "rotate_test"});
    }
}