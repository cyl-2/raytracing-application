package raytracer.raydefinition;

/**
 * The Vector3D Class
 *
 * Specifies a direction from a single point as a vector
 */
public class Vector3D {
    private Point3D point;

    /**
     * Constructor of a Vector3D object using coordinate values
     * The values are then passed into the Point3D class to construct a point
     *
     * @param x     coordinate on the x axis
     * @param y     coordinate on the y axis
     * @param z     coordinate on the z axis
     */
    public Vector3D(float x, float y, float z) {
        this.point = new Point3D(x, y, z);
    }

    /**
     * Alternative constructor of a Vector3D object using a point from the Point3D class
     *
     * @param point     a point from the Point3D class
     */
    public Vector3D(Point3D point) {
        this.point = point;
    }

    /**
     * This method finds the dot product of two vectors
     *
     * @param vector    another vector from the Vector3D class
     * @return          the dot product
     */
    public float dotProduct(Vector3D vector) {
        return (point.getX()*vector.point.getX() + point.getY()*vector.point.getY() + point.getZ()*vector.point.getZ());
    }

    /**
     * This method finds the cross product of two vectors
     *
     * @param vector    another vector from the Vector3D class
     * @return          the cross product, which is a new Vector3D object
     */
    public Vector3D crossProduct(Vector3D vector) {
        return new Vector3D(((point.getY() * vector.point.getZ()) - (point.getZ() * vector.point.getY())),
                ((point.getZ() * vector.point.getX()) - (point.getX() * vector.point.getZ())),
                ((point.getX() * vector.point.getY()) - (point.getY() * vector.point.getX())));
    }

    /**
     * This static method normalizes two different vectors to find the direction of the vector
     *
     * @param vector    defines a raytracer.raydefinition.Vector3D object
     * @return          a new Vector3D object as a result of the normalization
     */
    public static Vector3D normalize(Vector3D vector) {
        float magnitude = magnitude(vector);
        if (magnitude != 0 && magnitude != 1) magnitude = (float) (1 / Math.sqrt(magnitude));
        return new Vector3D(vector.point = vector.point.multiply(magnitude));
    }

    /**
     * Normalizes the current Vector3D object
     */
    public void normalize() {
        float magnitude = magnitude(this);
        if (magnitude != 0 && magnitude != 1) magnitude = (float) (1 / Math.sqrt(magnitude));
        new Vector3D(this.point = this.point.multiply(magnitude));
    }

    /**
     * This method finds the magnitude of a vector
     *
     * @param vector    the vector that needs to know its magnitude
     * @return          the magnitude of the vector
     */
    public static float magnitude(Vector3D vector) {
        return (vector.dotProduct(vector));
    }

    /**
     * Gets the point object
     *
     * @return  the point object
     */
    public Point3D getPoint() {
        return point;
    }

    /**
     * Sets a new point
     *
     * @param point     the new point to be assigned
     */
    public void setPoint(Point3D point) {
        this.point = point;
    }
}
