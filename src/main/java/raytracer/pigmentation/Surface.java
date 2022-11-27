package raytracer.pigmentation;

/**
 * The Surface class
 */
public class Surface {

    private float ir, ig, ib;        // surface's intrinsic color
    private float ambientReflection, diffuseReflection, specularReflection;
    private float phong;    // constants for phong model
    private float transmission;
    private float adaptiveDepth;
    private float index;
    private static final float I255 = 0.00392156f;  // 1/255

    /**
     * Constructor for a Surface object
     *
     * @param rval - value to specify intrinsic colour
     * @param gval - value to specify intrinsic colour
     * @param bval - value ot specify intrinsic colour
     * @param ambientReflection - incoming light component that is identical everywhere in a scene
     * @param diffuseReflection - the reflection of light from a surface such that an incident ray is reflected at many angles
     * @param specularReflection -  mirror-like reflection of light from the surface
     * @param phong - an interpolation technique for surface shading
     * @param maxContribution - maxContribution for adaptive Depth
     * @param transmission - value for transmission
     * @param index - index of surface
     */
    public Surface(float rval, float gval, float bval, float ambientReflection, float diffuseReflection, float specularReflection, float phong, float maxContribution, float transmission, float index) {
        this.ir = rval;
        this.ig = gval;
        this.ib = bval;
        this.ambientReflection = ambientReflection ;
        this.diffuseReflection = diffuseReflection;
        this.specularReflection = specularReflection;
        this.phong = phong;
        this.transmission = maxContribution*I255;
        this.adaptiveDepth = transmission;
        this.index = index;
    }

    /**
     * Gets the red colour intensity
     *
     * @return      the red colour intensity
     */
    public float getIr() {
        return this.ir;
    }

    /**
     * Gets the green colour intensity
     *
     * @return      the green colour intensity
     */
    public float getIg() {
        return this.ig;
    }

    /**
     * Gets the blue colour intensity
     *
     * @return      the blue colour intensity
     */
    public float getIb() {
        return this.ib;
    }

    /**
     * Gets the Ambient Reflection
     *
     * @return      the ambient reflection
     */
    public float getAmbientReflection() {
        return this.ambientReflection;
    }

    /**
     * Gets the diffused reflection
     *
     * @return      the diffused reflection
     */
    public float getDiffuseReflection() {
        return this.diffuseReflection;
    }

    /**
     * Gets the specular reflection
     *
     * @return      the specular reflection
     */
    public float getSpecularReflection() {
        return this.specularReflection;
    }

    /**
     * Gets the adaptive Depth
     *
     * @return      the adaptive depth
     */
    public float getAdaptiveDepth() {
        return this.adaptiveDepth;
    }

    /**
     * Gets the phong value
     *
     * @return      the phong value
     */
    public float getPhong() {
        return this.phong;
    }
}