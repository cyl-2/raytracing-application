package raytracer.raydefinition;

import java.util.List;
import javafx.scene.paint.Color;
import raytracer.GeometricObject;

/**
 * The Ray class
 *
 * A ray is defined by an origin point and a direction vector
 */
public class Ray {
    static final float MAX_T = Float.MAX_VALUE;
    private Point3D origin;
    private Vector3D direction;
    private float root; // the value of where the ray intersects the shape
    private GeometricObject object;

    /**
     * Constructor of a Ray object
     *
     * @param origin    the origin point
     * @param direction the direction vector
     */
    public Ray(Point3D origin, Vector3D direction) {
        this.origin = origin;
        this.direction = Vector3D.normalize(direction);
    }

    /**
     * This method takes a list of objects and check if the ray intersects with the objects
     *
     * @param objects   a list of objects
     * @return          a boolean value that determines if the ray intersects the objects or not
     */
    public boolean trace(List<Object> objects) {
        root = MAX_T;
        object = null;
        for (Object objList : objects) {
            GeometricObject object = (GeometricObject) objList;
            object.intersect(this);
        }
        return (object != null);
    }

    /**
     * This method passes the required parameters to the Shade
     * method of the object to be coloured
     *
     * @param lights        a list of the different lights
     * @param objects       a list of the different shape objects
     * @param background    the colour to be applied to the surface of the shape
     * @return              a shape object that is coloured
     */
    public final Color Shade(List<Object> lights, List<Object> objects, Color background) {
        return object.Shade(this, lights, objects, background);
    }

    /**
     * Gets the origin point
     *
     * @return  the origin point
     */
    public Point3D getOrigin() {
        return origin;
    }

    /**
     * Gets the direction vector
     *
     * @return  the direction vector
     */
    public Vector3D getDirection() {
        return direction;
    }

    /**
     * Gets the root value
     *
     * @return  the root value
     */
    public float getRoot() {
        return root;
    }

    /**
     * Sets the root value
     *
     * @param root  the new root value to be set
     */
    public void setRoot(float root) {
        this.root = root;
    }

    /**
     * Sets the new geometric object
     *
     * @param object    the new object to be assigned
     */
    public void setObject(GeometricObject object) {
        this.object = object;
    }

    /**
     *
     * @return  a string that contains the ray origin, ray direction and the root value
     */
    public String toString() {
        return ("Ray origin = " + origin + " , direction = " + direction + "  root value = " + root);
    }
}

