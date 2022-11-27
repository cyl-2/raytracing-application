package raytracer.raydefinition;

/**
 * The Point3D class
 *
 * Specifies the x, y and z coordinates of a point
 */
public class Point3D {

    private float x, y, z;

    /**
     * Constructor of a Point3D object
     *
     * @param x     coordinate on the x axis
     * @param y     coordinate on the y axis
     * @param z     coordinate on the z axis
     */
    public Point3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * This method adds the coordinates of the 2 points
     *
     * @param point     another point from the Point3D class
     * @return          a new computed Point3D object
     */
    public Point3D add(Point3D point) {
        return new Point3D(x + point.x, y + point.y, z + point.z);
    }

    /**
     * This method multiplies the coordinates of a point by a floating number
     *
     * @param number    a floating value
     * @return          a new computed Point3D object
     */
    public Point3D multiply(float number) {
        return new Point3D(x * number, y * number, z * number);
    }

    /**
     * This method subtracts the coordinates of the 2 points
     *
     * @param point     another point from the Point3D class
     * @return          a new computed Point3D object
     */
    public Point3D subtract(Point3D point) {
        return new Point3D(x - point.x, y - point.y, z - point.z);
    }

    /**
     * Gets the coordinate x
     *
     * @return  the coordinate x
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the coordinate y
     *
     * @return  the coordinate y
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the coordinate z
     *
     * @return  the coordinate z
     */
    public float getZ() {
        return z;
    }

    /**
     *
     * @return  a string that contains the coordinates of the Point3D object
     */
    public String toString() {
        return "Point coordinates: [" + x + ", " + y + ", " + z + "]";
    }
}
