package raytracer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import raytracer.pigmentation.*;
import raytracer.raydefinition.*;

/**
 * The Raytracer API Class
 *
 */
public class RayTracerAPI {
    //want to set up frame or display
    JFrame frame;
    int frameHeight, frameWidth;
    float horizontal;
    Point3D origin;
    Vector3D lookat, up;
    BufferedImage canvas;
    final static int CHUNKSIZE = 100;
    Color background;
    Vector3D Du, Dv, Vp;
    List<Object> objectList, lightList;
    Surface currentSurface;

    /**
     * Constructor for RayTracerAPI
     *
     * @param height - will represent the height of the output window
     * @param width - will represent the width of the output window
     *
     * */
    public RayTracerAPI(int height, int width){
        this.frameHeight = height;
        this.frameWidth = width;
        this.horizontal = 0;
        this.canvas = new BufferedImage(this.frameWidth,this.frameHeight,BufferedImage.TYPE_INT_ARGB);
        objectList = new ArrayList<>(CHUNKSIZE);
        lightList = new ArrayList<>(CHUNKSIZE);
        currentSurface = new Surface(0.8f,0.2f,0.9f,0.2f,0.4f, 0.4f, 10.0f, 0f, 0f,1f);
    }

    /**
     * Method to set-up the output window/frame
     *
     * */
    public void setupFrame(){
        Dimension displaySize = new Dimension(this.frameWidth, this.frameHeight);

        frame = new JFrame("Ray Tracing Demonstration");
        frame.setSize(this.frameWidth,this.frameHeight);
        frame.setPreferredSize(displaySize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new BufferedImage(this.frameWidth,this.frameHeight,BufferedImage.TYPE_INT_ARGB);
        this.horizontal = 30;

        // Default values
        if (origin == null) origin = new Point3D(0,0,10);
        if (lookat == null) lookat = new Vector3D(0,0,0);
        if (up == null) up = new Vector3D(0,1,0);
        if (background ==null) background =new Color(0,0,0, 1);

        this.setupViewingMatrix();
    }
    /**
     * Creates a matrix for viewing that is needed to setup the frame/window
     *
     * Should only be called internally
     * */
    private void setupViewingMatrix(){
        Vector3D look = new Vector3D(lookat.getPoint().subtract(origin));
        Du = Vector3D.normalize(look.crossProduct(up));
        Dv = Vector3D.normalize(look.crossProduct(Du));
        float fl = (float)(this.frameWidth / (2*Math.tan((0.5*horizontal)*Math.PI/180)));
        Vp = Vector3D.normalize(look);
        Vp.setPoint(Vp.getPoint().multiply(fl).subtract(Du.getPoint().multiply(this.frameWidth).add(Dv.getPoint().multiply(this.frameHeight)).multiply(0.5f)));
    }

    /**
     * Method will actually display object to the GUI and save the rendered result as a PNG file
     *
     * Should only be called after setupFrame
     * */
    public void renderFrame() throws IOException {
        ImagePanel image = new ImagePanel("resources/Placeholder-01.png");

        frame.add(image);
        frame.pack();
        frame.setVisible(true);

        long time = System.currentTimeMillis();
        for (int j=0; j< this.frameHeight; j+=1){
            for (int i =0; i<this.frameWidth; i+=1){
                this.renderPixel(i,j);
            }
        }
        image.updateImage(this.canvas);
        time = System.currentTimeMillis() - time;
        Log.info("Rendered in " +(time/60000)+ " minutes: "+((time%60000)*0.001)+" seconds" );
        image.saveAsPNG(this.canvas);
    }
    /**
     * A method to render/create and fill in the individual pixels in our scene
     *
     * @param i - value for horizontal component of a vector
     * @param j - value for vertical component of a vector
     * Should only be called internally
     * */
    private void renderPixel(int i, int j){
        Vector3D direction= new Vector3D(Du.getPoint().multiply(i).add(Dv.getPoint().multiply(j)).add(Vp.getPoint()));
        Ray ray = new Ray(origin,direction);
        Color pixelColour;

        if (ray.trace(this.objectList)){
            Color bg = background;
            pixelColour = ray.Shade(lightList, objectList, bg);
        }
        else{
            pixelColour = background;
        }
        canvas.setRGB(i, j, Colour.getRGB(pixelColour));
    }

    /**
     * Creates a sphere to be displayed in our image/scene
     *
     * @param x - x coordinate of the center point
     * @param y - y coordinate of the center point
     * @param z - z coordinate of the center point
     * @param radius - represents the radius of the sphere
     * */
    public void createSphere(float x, float y, float z, float radius){
        Point3D v = new Point3D(x,y,z);
        objectList.add(new Sphere(currentSurface,v,radius));
    }

    /**
     * Sets the origin point of the scene
     *
     * @param x - value for x coordinate
     * @param y - value for y coordinate
     * @param z - value for z coordinate
     *
     * */
    public void setOrigin (float x, float y, float z){
        origin = new Point3D(x,y,z);
    }

    /**
     * Set the position point of where the rendered shapes will be view from
     *
     * @param x - value for x coordinate
     * @param y - value for y coordinate
     * @param z - value for z coordinate
     *
     * */
    public void setLookat(float x, float y, float z){
        lookat = new Vector3D(x, y,z);
    }

    /**
     * Sets the original vector to display to the frame/window
     *
     * @param x - value for x coordinate
     * @param y - value for y coordinate
     * @param z - value for z coordinate
     *
     * */
    public void setUp(float x, float y, float z){
        up = new Vector3D(x, y, z);
    }

    /**
     * Sets the lighting conditions of the scene
     *
     * @param red - float value for red pigmentation
     * @param green - float value for green pigmentation
     * @param blue - float value for blue pigmentation
     * @param typeOfLight - dictates the type of light to be displayed
     *
     * */
    public void setlight( float red, float green, float blue, String typeOfLight){
        Color intensity = new Color(red, green, blue, 1);
        typeOfLight = typeOfLight.toLowerCase();
        if (typeOfLight.equals("ambient")){
            lightList.add(new Light(LightType.AMBIENT, null, intensity));
        }
    }

    /**
     * SetLight with 4 parameters, will override the other setLight
     *
     * @param red - amount of red in the colour to be used
     * @param green - amount of green in the colour to be used
     * @param blue -  amount of blue in the colour to be used
     * @param typeOfLight - dictates the type of light to be displayed
     * @param x - x coordinate of vector
     * @param y - y coordinate of vector
     * @param z - z coordinate of vector
     * */
    public void setlight(float red, float green, float blue, String typeOfLight, float x, float y, float z){
        Color intensity = new Color(red, green, blue, 1);
        typeOfLight = typeOfLight.toLowerCase();
        if (typeOfLight.equals("directional")) {
            Vector3D v = new Vector3D(x, y, z);
            lightList.add(new Light(LightType.DIRECTIONAL, v, intensity));
        } else if (typeOfLight.equals("point")) {
            Vector3D v = new Vector3D(x, y, z);
            lightList.add(new Light(LightType.POINT, v, intensity));
        } else {
            Log.error("incorrect input");
        }
    }

    /**
     * Set the surface of the scene
     *
     * @param red - amount of red in the colour to be used
     * @param green - amount of green in the colour to be used
     * @param blue -  amount of blue in the colour to be used
     * @param ambientReflection - incoming light component that is identical everywhere in a scene
     * @param diffuseReflection - the reflection of light from a surface such that an incident ray is reflected at many angles
     * @param specularReflection -  mirror-like reflection of light from the surface
     * @param phong - an interpolation technique for surface shading
     * @param transmission - value for transmission
     * @param index - index of surface
     * */
    public void setSurface(float red, float green, float blue, float ambientReflection, float diffuseReflection, float specularReflection, float phong, float adaptiveDepth, float transmission, float index){
        currentSurface = new Surface(red, green, blue,ambientReflection, diffuseReflection, specularReflection, phong, adaptiveDepth, transmission, index);
    }
}