package raytracer;

import java.util.List;
import javafx.scene.paint.Color;
import raytracer.pigmentation.*;
import raytracer.raydefinition.*;

/**
 * The Sphere class
 *
 * This class implements the GeometricObject interface
 * A sphere is defined by a surface, a center point and its radius
 */
public class Sphere implements GeometricObject {
    Colour area;
    public Point3D center;
    public float radius;
    public float radSqr;

    /**
     * Constructor of a Sphere Object
     *
     * @param surface the surface of the sphere
     * @param center  the center point
     * @param radius  the length of the radius
     */
    public Sphere(Surface surface, Point3D center, float radius) {
        this.area = new Colour(surface);
        this.center = center;
        this.radius = radius;
        this.radSqr = radius*radius;
    }

    /**
     * This method checks if the ray intersects a Sphere object
     *
     * @param ray   the ray being examined
     * @return      a boolean value, True if the ray intersects the object ; False if not
     */
    @Override
    public boolean intersect(Ray ray) {
        Vector3D displacement = new Vector3D(center.subtract(ray.getOrigin()));
        float dot = ray.getDirection().dotProduct(displacement);

        // Check if an intersection might be closer than a previous one
        if (dot - radius > ray.getRoot())
            return false;

        // Test if ray intersects the sphere
        float t = radSqr + dot*dot - displacement.getPoint().getX()*displacement.getPoint().getX() - displacement.getPoint().getY()*displacement.getPoint().getY() - displacement.getPoint().getZ()*displacement.getPoint().getZ();
        if (t < 0) {
            return false;
        }
        // Test if the intersection is in the positive
        // ray direction and it is the closest so far
        t = dot - ((float) Math.sqrt(t));
        if ((t > ray.getRoot()) || (t < 0)) {
            return false;
        }

        ray.setRoot(t);
        ray.setObject(this);

        // if root value is positive, then there is an intersection between a ray and a surface
        return true;
    }

    /**
     * This method calculates the suitable values to be passed to the Shading
     * method of the Colour class to colour in the area of the surface where
     * the ray hits the surface of the Sphere object
     *
     * @param ray           the Ray Object
     * @param lights        a list of the different lights
     * @param objects       a list of the different shape objects
     * @param background    the colour to be applied to the surface of the shape
     * @return              a shape object that is coloured
     */
    @Override
    public Color Shade(Ray ray, List<Object> lights, List<Object> objects, Color background) {
        Vector3D intersectionPoint = new Vector3D(ray.getOrigin().add(ray.getDirection().getPoint().multiply(ray.getRoot())));
        Vector3D rayOrigin = new Vector3D(ray.getDirection().getPoint().multiply(-1));
        Vector3D surfaceNormal = new Vector3D(intersectionPoint.getPoint().subtract(center));
        surfaceNormal.normalize();

        return area.Shading(intersectionPoint, surfaceNormal, rayOrigin, lights, objects, background);
    }

    /**
     *
     * @return  a string that contains the center point and radius length of the sphere
     */
    public String toString() {
        return ("Sphere (center, radius): " + center + ", " + radius);
    }
}