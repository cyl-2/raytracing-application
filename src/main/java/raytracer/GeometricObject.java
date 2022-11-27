package raytracer;

import java.util.List;
import javafx.scene.paint.Color;
import raytracer.raydefinition.*;

/**
 * The GeometricObject Interface
 *
 * All shapes must implement the GeometricObject interface so that the shape can be ray-traced
 */
public interface GeometricObject {
    boolean intersect(Ray ray);
    Color Shade(Ray ray, java.util.List<Object> lights, List<Object> objects, Color bgnd);
    String toString();
}
