package raytracer.pigmentation;

import java.util.List;
import javafx.scene.paint.Color;
import raytracer.raydefinition.*;

/**
 * The Colour class
 *
 * Defines a surface to be coloured on
 * Contains methods to execute the application of colouring
 */
public class Colour {
    private static final float TINY = 0.001f;
    private Surface surface;

    /**
     * Constructor
     * To apply colour, there must be a surface to colour on
     *
     * @param surface   the surface of the shape that will be coloured
     */
    public Colour(Surface surface) {
        this.surface = surface;
    }

    /**
     * This method is a substitution for the getRGB method available in the awt library
     *
     * @param colour    a Color object
     * @return          integer RGB value
     */
    public static int getRGB(Color colour){
        // retrieve floating value of each component
        float red = (float) Math.abs(colour.getRed());
        float green = (float) Math.abs(colour.getGreen());
        float blue = (float) Math.abs(colour.getBlue());
        float alpha = (float) Math.abs(colour.getOpacity());

        // convert each component to their byte values
        int r = Math.round(red * 255);
        int g = Math.round(green * 255);
        int b = Math.round(blue * 255);
        int a = Math.round(alpha*255);

        // convert each component to their hex values
        var redHex = String.format("%02X", r);
        var greenHex = String.format("%02X", g);
        var blueHex = String.format("%02X", b);
        var alphaHex = String.format("%02X", a);

        // concatenate the hex values to form a hex string
        var hex = redHex + greenHex + blueHex + alphaHex;

        // convert the hex string to an integer value
        int rgb = (int) Long.parseUnsignedLong(hex, 16);
        return rgb;
    }

    /**
     * This method calculates the colour to be applied to the surface
     *
     * @param intersectionPoint     the point of intersection
     * @param surfaceNormal         a unit-length surface normal
     * @param rayOrigin             a unit-length vector towards the ray's origin
     * @param lights                list of light objects
     * @param objects               list of objects
     * @param background            the color of the surface background
     * @return                      the colour to be applied to the surface
     */
    public Color Shading(Vector3D intersectionPoint, Vector3D surfaceNormal, Vector3D rayOrigin, java.util.List<Object> lights, List<Object> objects, Color background) {
        float red = 0;
        float green = 0;
        float blue = 0;
        for (Object lightSources : lights) {
            Light light = (Light) lightSources;
            if (light.getLightType() == LightType.AMBIENT) {
                red += surface.getAmbientReflection() * surface.getIr() * light.getIntensity().getRed();
                green += surface.getAmbientReflection() * surface.getIg() * light.getIntensity().getGreen();
                blue += surface.getAmbientReflection() * surface.getIb() * light.getIntensity().getBlue();
            } else {
                Vector3D l;
                if (light.getLightType() == LightType.POINT) {
                    l = new Vector3D(light.getLvec().getPoint().subtract(intersectionPoint.getPoint()));
                    l.normalize();
                } else {
                    l = new Vector3D(light.getLvec().getPoint().multiply(-1));
                }

                // Check if the surface point is in shadow
                Vector3D poffset = new Vector3D(intersectionPoint.getPoint().add(l.getPoint().multiply(TINY)));
                Ray shadowRay = new Ray(poffset.getPoint(), l);
                if (shadowRay.trace(objects))
                    break;

                float lambert = surfaceNormal.dotProduct(l);
                if (lambert > 0) {
                    if (surface.getDiffuseReflection() > 0) {
                        float diffuse = surface.getDiffuseReflection() * lambert;
                        red += diffuse * surface.getIr() * light.getIntensity().getRed();
                        green += diffuse * surface.getIg() * light.getIntensity().getGreen();
                        blue += diffuse * surface.getIb() * light.getIntensity().getBlue();
                    }
                    if (surface.getSpecularReflection() > 0) {
                        lambert *= 2;
                        Vector3D v = new Vector3D(surfaceNormal.getPoint().multiply(lambert).subtract(l.getPoint()));
                        float spec = rayOrigin.dotProduct(v);
                        if (spec > 0) {
                            spec = surface.getSpecularReflection() * ((float) Math.pow(spec, surface.getPhong()));
                            red += spec * light.getIntensity().getRed();
                            green += spec * light.getIntensity().getGreen();
                            blue += spec * light.getIntensity().getBlue();
                        }
                    }
                }
            }
        }

        // Compute illumination due to reflection
        if (surface.getAdaptiveDepth() > 0) {
            float t = rayOrigin.dotProduct(surfaceNormal);
            if (t > 0) {
                t *= 2;
                Vector3D reflect = new Vector3D(surfaceNormal.getPoint().multiply(t).subtract(rayOrigin.getPoint()));
                Vector3D poffset = new Vector3D(intersectionPoint.getPoint().add(reflect.getPoint().multiply(TINY)));
                Ray reflectedRay = new Ray(poffset.getPoint(), reflect);
                if (reflectedRay.trace(objects)) {
                    Color rcolor = reflectedRay.Shade(lights, objects, background);
                    red += surface.getAdaptiveDepth() * rcolor.getRed();
                    green += surface.getAdaptiveDepth() * rcolor.getGreen();
                    blue += surface.getAdaptiveDepth() * rcolor.getBlue();
                } else {
                    red += surface.getAdaptiveDepth() * background.getRed();
                    green += surface.getAdaptiveDepth() * background.getGreen();
                    blue += surface.getAdaptiveDepth() * background.getBlue();
                }
            }
        }

        // Code for refraction
        red = Math.min(red, 1f);
        green = Math.min(green, 1f);
        blue = Math.min(blue, 1f);

        red = (red < 0) ? 0 : red;
        green = (green < 0) ? 0 : green;
        blue = (blue < 0) ? 0 : blue;

        return new Color(red, green, blue, 1);
    }
}
