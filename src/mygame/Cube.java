package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ivan
 */
public class Cube extends Node {
    
    public Coords coords; //TODO just to debug, should be removed
    public Coords firstCoords;
    private AssetManager assetManager;
    Material transparent;
    boolean selected;
    
    Geometry face1;
    Geometry face2;
    Geometry face3;
    Geometry face4;
    Geometry face5;
    Geometry face6;
    
    Texture face1D;
    Texture face2D;
    Texture face3D;
    Texture face4D;
    Texture face5D;
    Texture face6D;
    Texture face1S;
    Texture face2S;
    Texture face3S;
    Texture face4S;
    Texture face5S;
    Texture face6S;
    
    Map<String, Texture[]> textures = new HashMap<String, Texture[]>();
    
    public void setCoords(int x, int y, int z) {
        coords.x = x;
        coords.y = y;
        coords.z = z;
    }
    
    public Cube(Vector3f origin, float size, AssetManager assetManager, Coords coords) {
        firstCoords = coords;
        this.coords = coords;
        this.assetManager = assetManager;
        //front
        Mesh m1 = new Mesh();
        
        Vector3f [] vertices = new Vector3f[4];
        
        vertices[0] = new Vector3f(0,0,size);
        vertices[1] = new Vector3f(size,0,size);
        vertices[2] = new Vector3f(0,size,size);
        vertices[3] = new Vector3f(size,size,size);
        
        Vector2f[] texCoord = new Vector2f[4];
        texCoord[0] = new Vector2f(0,0);
        texCoord[1] = new Vector2f(1,0);
        texCoord[2] = new Vector2f(0,1);
        texCoord[3] = new Vector2f(1,1);
        
        int [] indexes = { 2,0,1, 1,3,2 };
        
        m1.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        m1.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m1.setBuffer(Type.Index,    1, BufferUtils.createIntBuffer(indexes));
        m1.updateBound();
        
        face1 = new Geometry("OurMesh", m1);
        face1.setName("blue");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        
        face1D = assetManager.loadTexture("Textures/blue.png");
        face1S = assetManager.loadTexture("Textures/blues.png");
        mat.setTexture("ColorMap", face1D);
        textures.put("blue", new Texture[]{face1D, face1S});
    
        face1.setMaterial(mat);

        //right
        Mesh m2= new Mesh();
         
        Vector3f [] vertices2 = new Vector3f[4];
        vertices2[0] = new Vector3f(size,0,size);
        vertices2[1] = new Vector3f(size,0,0);
        vertices2[2] = new Vector3f(size,size,size);
        vertices2[3] = new Vector3f(size,size,0);
        
        m2.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices2));
        m2.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m2.setBuffer(Type.Index,    1, BufferUtils.createIntBuffer(indexes));
        m2.updateBound();
        
        face2 = new Geometry("OurMesh", m2);
        face2.setName("red");
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        
        face2D = assetManager.loadTexture("Textures/red.png");
        face2S = assetManager.loadTexture("Textures/reds.png");
        
        mat2.setTexture("ColorMap", face2D);
        textures.put("red", new Texture[]{face2D, face2S});
        face2.setMaterial(mat2);
        
        //behind
        Mesh m3= new Mesh();
         
        Vector3f [] vertices3 = new Vector3f[4];
        vertices3[1] = new Vector3f(0,0,0);
        vertices3[0] = new Vector3f(size,0,0);
        vertices3[3] = new Vector3f(0,size,0);
        vertices3[2] = new Vector3f(size,size,0);
        
        m3.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices3));
        m3.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m3.setBuffer(Type.Index,    1, BufferUtils.createIntBuffer(indexes));
        m3.updateBound();
        
        face3 = new Geometry("OurMesh", m3);
        face3.setName("yellow");
        Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        face3D = assetManager.loadTexture("Textures/yellow.png");
        face3S = assetManager.loadTexture("Textures/yellows.png");
        mat3.setTexture("ColorMap", face3D);
        textures.put("yellow", new Texture[]{face3D, face3S});
        face3.setMaterial(mat3);
        
        //left
        Mesh m4= new Mesh();
         
        Vector3f [] vertices4 = new Vector3f[4];
        vertices4[1] = new Vector3f(0,0,size);
        vertices4[0] = new Vector3f(0,0,0);
        vertices4[3] = new Vector3f(0,size,size);
        vertices4[2] = new Vector3f(0,size,0);

        m4.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices4));
        m4.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m4.setBuffer(Type.Index,    1, BufferUtils.createIntBuffer(indexes));
        m4.updateBound();
        
        face4 = new Geometry("OurMesh", m4);
        face4.setName("green");
        Material mat4 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        face4D = assetManager.loadTexture("Textures/green.png");
        face4S = assetManager.loadTexture("Textures/greens.png");
        mat4.setTexture("ColorMap", face4D);
        textures.put("green", new Texture[]{face4D, face4S});
        face4.setMaterial(mat4);
        
        //top
        Mesh m5= new Mesh();
         
        Vector3f [] vertices5 = new Vector3f[4];
        vertices5[0] = new Vector3f(0,size,size);
        vertices5[1] = new Vector3f(size,size,size);
        vertices5[2] = new Vector3f(0,size,0);
        vertices5[3] = new Vector3f(size,size,0);

        m5.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices5));
        m5.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m5.setBuffer(Type.Index,    1, BufferUtils.createIntBuffer(indexes));
        m5.updateBound();
        
        face5 = new Geometry("OurMesh", m5);
        face5.setName("lila");
        Material mat5 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        face5D = assetManager.loadTexture("Textures/lila.png");
        face5S = assetManager.loadTexture("Textures/lilas.png");
        mat5.setTexture("ColorMap", face5D);
        textures.put("lila", new Texture[]{face5D, face5S});
        face5.setMaterial(mat5);
        
        //bottom
        Mesh m6= new Mesh();
         
        Vector3f [] vertices6 = new Vector3f[4];
        vertices6[1] = new Vector3f(0,0,size);
        vertices6[0] = new Vector3f(size,0,size);
        vertices6[3] = new Vector3f(0,0,0);
        vertices6[2] = new Vector3f(size,0,0);

        m6.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices6));
        m6.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m6.setBuffer(Type.Index,    1, BufferUtils.createIntBuffer(indexes));
        m6.updateBound();
        
        face6 = new Geometry("OurMesh", m6);
        face6.setName("orange");
        Material mat6 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        face6D = assetManager.loadTexture("Textures/orange.png");
        face6S = assetManager.loadTexture("Textures/oranges.png");
        mat6.setTexture("ColorMap", face6D);
        face6.setMaterial(mat6);
        textures.put("orange", new Texture[]{face6D, face6S});
        /*
        Box b = new Box(new Vector3f(0,0,0), 0.5f, 0.5f, 0.5f);
        Geometry g = new Geometry("bla", b);
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setColor("Color", ColorRGBA.White);
        g.setMaterial(m);
        attachChild(g);
        Box b2 = new Box(new Vector3f(size,size,size), 0.5f, 0.5f, 0.5f);
        Geometry g2 = new Geometry("bla", b2);
        Material m0 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m0.setColor("Color", ColorRGBA.Pink);
        g2.setMaterial(m0);
        attachChild(g2);
        */
        attachChild(face1);
        attachChild(face2);
        attachChild(face3);
        attachChild(face4);
        attachChild(face5);
        attachChild(face6);
        
        setLocalTranslation(origin);
        
        transparent = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        transparent.setColor("m_Color", new ColorRGBA(5, 5, 1, 0.2f));
        transparent.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
        int index = selected ? 1 : 0;
        
        List<Spatial> faces = getChildren();
        for (Spatial face : faces) {
            ((Geometry)face).getMaterial().setTexture("ColorMap", textures.get(face.getName())[index]);
        }
    }
    
    public String toString() {
        return coords.toString();
    }
    
    private float bz = 0.2f; //bordersize
    
    //TODO
    private Spatial generateFace(Vector3f[] vertices, ColorRGBA color) {
        int [] indexes = { 2,0,1, 1,3,2 };
        
        Mesh backgroundMesh = new Mesh();
        
        Geometry backGeom = new Geometry("background", backgroundMesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(.2f, .2f, .2f, 0));
        backGeom.setMaterial(mat);
        
        backgroundMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        backgroundMesh.setBuffer(Type.Index,    1, BufferUtils.createIntBuffer(indexes));
        backgroundMesh.updateBound();
        
//         Vector3f [] vertices6 = new Vector3f[4];
//        vertices6[1] = new Vector3f(0,0,size);
//        vertices6[0] = new Vector3f(size,0,size);
//        vertices6[3] = new Vector3f(0,0,0);
//        vertices6[2] = new Vector3f(size,0,0);
        
        Vector3f [] faceVertices = new Vector3f[4];
        Vector3f v;
        Vector3f nv;
        for (int i = 0; i < vertices.length; i++) {
            v = vertices[i];
            nv = new Vector3f();
            nv.x = v.x == 0 ? v.x + bz : v.x - bz;
            nv.y = v.y == 0 ? v.y + bz : v.y - bz;
            nv.z = v.z == 0 ? v.z + bz : v.z - bz;
            faceVertices[i] = nv;
        }
        
        Mesh m1 = new Mesh();

        m1.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(faceVertices));
        m1.setBuffer(Type.Index,    1, BufferUtils.createIntBuffer(indexes));
        m1.updateBound();
        
        Geometry face1 = new Geometry("OurMesh", m1);
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        face1.setMaterial(mat);
        
        Node node = new Node();
        node.attachChild(backGeom);
        node.attachChild(face1);
        
        return node;
    }
}
