package raytracer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        RayTracerAPI scene = new RayTracerAPI(600,600);

        scene.setOrigin((float)1.5,(float)10.5,(float)-1.5);
        scene.setLookat((float)-0.5, 0 ,(float)-0.5);
        scene.setlight((float)1.0,(float)1.0,(float)0.981,"ambient");
        scene.setlight((float)0.9,(float)0.9,(float)0.9,"ambient");
        scene.setlight((float)0.745,(float)0.859,(float)0.224,"ambient");
        scene.setlight((float)0.6,(float)0.6,(float)0.6,"directional",-1,-1,-1);

        scene.setSurface((float)0.2,(float)0.8,(float)0.2,(float)0.5,(float)0.9,(float)0.4,(float)10.0,(float)0,0,1);
        scene.createSphere((float)-0.4,(float)0.375,(float)-0.4,(float)0.375);

        scene.setSurface((float)0.7,(float)0.3,(float)0.2,(float)0.5,(float)0.9,(float)0.4,(float)6.0,(float)0,0,1);
        scene.createSphere((float)-0.6,(float)1.05,(float)-0.6,(float)0.3);

        scene.setSurface((float)0.2,(float)0.8,(float)0.2,(float)0.5,(float)0.9,(float)0.4,(float)10.0,(float)0,0,1);
        scene.createSphere((float)-0.4,(float)0.375,(float)-0.4,(float)0.375);

        scene.setSurface((float)0.2,(float)0.3,(float)0.8,(float)0.5,(float)0.9,(float)0.4,(float)10.0,(float)0,0,1);
        scene.createSphere((float)-0.8,(float)1.575,(float)-0.8,(float)0.125);

        scene.setSurface((float)0.5,(float)0.5,(float)0.8,(float)0.5,(float)0.9,(float)0.4,(float)10.0,(float)0,0,1);
        scene.createSphere((float)-1.2,(float)2.575,(float)-0.8,(float)0.1);
        scene.setupFrame();
        scene.renderFrame();
    }
}
