package raytracer.pigmentation;

import javafx.scene.paint.Color;
import raytracer.raydefinition.*;

/**
 *  The Light Class
 *
 *  Defines the RGB colour intensity of the Light
 *  Uses {@link LightType} to determine the position of a point light or
 *  the direction to a directional light
 */
public class Light {
    private LightType lightType;
    private Vector3D lvec;
    private Color intensity;

    /**
     *
     * @param lightType Type of Light
     * @param vector    Vector
     * @param intensity Intensity of colour
     *
     */
    public Light(LightType lightType, Vector3D vector, Color intensity) {
        this.lightType = lightType;
        this.intensity = intensity;
        if (lightType != LightType.AMBIENT) {
            this.lvec = vector;
            if (lightType == LightType.DIRECTIONAL) {
                this.lvec.normalize();
            }
        }
    }

    /**
     * Gets the light type
     *
     * @return  the lightType object
     */
    public LightType getLightType() {
        return this.lightType;
    }


    /**
     * Gets the vector
     *
     * @return   the vector
     */
    public Vector3D getLvec() {
        return this.lvec;
    }

    /**
     * Gets the intensity of light
     *
     * @return  the intensity object
     */
    public Color getIntensity() {
        return this.intensity;
    }

}
