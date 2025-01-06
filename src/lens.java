import processing.core.PApplet;

import java.util.Arrays;

public class lens {

    face face1;
    face face2;

    int topOffset;

    PApplet applet;
    public lens(face face1, face face2, PApplet applet, int topOffset) {
        this.face1 = face1;
        this.face2 = face2;
        this.applet = applet;
        this.topOffset = topOffset;
    }

    public void renderLens() {
        face1.drawFace();
        face2.drawFace();

        // render top and bottom face
        float height = face1.height;
        System.out.println(height);
        int width = (int)(face2.getX(height) - face1.getX(height));
        System.out.printf("Difference: %d",width);

        for (int i = 0; i != width; i++) {
            applet.fill(200,200,200);
            applet.ellipse(face1.getX(height)+i,face1.origin[1],10,10);
            applet.ellipse(face1.getX(height-(height*2))+i,face1.origin[1]+(height*2),10,10);
        }
    }

    public boolean isIntersecting(particle point) {
        float[] coordinate = point.getCoordinate();
        //System.out.printf("x1 = %f, y1 = %f, x2 = %f, y2 = %f\n", face1.getX(coordinate[1]-209), coordinate[1], face2.getX(coordinate[1]-209),coordinate[1]);
        /*
        if ((face1.getX(coordinate[1]-209) <= coordinate[0]) && (coordinate[0] <= face2.getX(coordinate[1]-209))) {
            applet.stroke(127,0,255);
            applet.strokeWeight(16);
            applet.line(face1.getX(coordinate[1]-209)+10, coordinate[1], face2.getX(coordinate[1]-209), coordinate[1]);
            applet.noStroke();
        }
         */

        // y coordinate conversion
        float newY = face1.height - (coordinate[1]-topOffset);

        boolean face1Bool = face1.getX(newY)-1 <= coordinate[0] && coordinate[0] <= face1.getX(newY)+1;
        boolean face2Bool = face2.getX(newY)-1 <= coordinate[0] && coordinate[0] <= face2.getX(newY)+1;

        return  face1Bool || face2Bool;
    }

    // TODO
    double a = 299705;
    double g = 200000;
    public float refractionIndex(particle point, float[][] pointData) {

        float newY = face1.height - (point.coordinate[1]-topOffset);

        applet.fill(255,0,0);
        applet.ellipse(point.coordinate[0],point.coordinate[1],12,12);

        // particle path slope
        System.out.println(Arrays.deepToString(pointData));
        float pointSlope = point.getSlope();

        // lens normal slope
        System.out.println(newY);
        // decide which lens to use
        double lensSlope;
        System.out.println(point.coordinate[0]- face1.origin[0]);                                                            // TODO: face1.offset is apparently really important lol
        if (face1.getX(newY)-1 <= point.coordinate[0] && point.coordinate[0] <= face1.getX(newY)+1) {
            // use face1
            lensSlope = (-1/face1.getSlope(newY)); // get normal from tangent slope
            System.out.println("ping");
        } else if (face2.getX(newY)-1 <= point.coordinate[0] && point.coordinate[0] <= face2.getX(newY)+1) {
            //use face2
            lensSlope = (-1/face2.getSlope(newY)); // get normal from tangent slope
            System.out.println("pong");
        } else {
            lensSlope = 0;
        }
        if (lensSlope == Double.POSITIVE_INFINITY || lensSlope == Double.NEGATIVE_INFINITY) {
            lensSlope = 0;
        }

        System.out.printf("lensSlope: %f", (float) lensSlope);


        //drawLine(pointSlope, 0, new int[]{150,0,150});
        System.out.println();
        //drawLine((float) lensSlope, (float)(point.coordinate[1]-(lensSlope*point.coordinate[0])), new int[]{150,150,0});

        //drawLine((float)(face2.getSlope(point.coordinate[1])), 100, new int[]{255,118,0});

        System.out.printf("Slope1: %f, slope2: %f\n", pointSlope, lensSlope);

        // visual check if no refraction
        if (lensSlope == 0 && pointSlope == 0) {
            applet.fill(0,0,255);
            applet.noStroke();
            applet.ellipse(point.coordinate[0], point.coordinate[1], 14,14);
        }

        // get angle between the two lines
        double angleDeg = Math.tanh( Math.abs( (pointSlope-lensSlope)/(1+(pointSlope*lensSlope) ) ) );

        // snell's law
        double refractionAngleDeg = 0;
        if (face1.getX(newY)-1 <= point.coordinate[0] && point.coordinate[0] <= face1.getX(newY)+1) {
            // air to glass
            refractionAngleDeg = Math.sinh( Math.sin(angleDeg)/(1.5));
            System.out.println("air to glass");
        } else if (face2.getX(newY)-1 <= point.coordinate[0] && point.coordinate[0] <= face2.getX(newY)+1) {
            // glass to air
            refractionAngleDeg = Math.sinh(Math.sin(angleDeg) / (0.67));
            System.out.println("glass to air");
        }

        //
        double refractionFromOrigin = angleDeg-refractionAngleDeg;

        // convert to radians
        float amplification = 10;
        double refractionAngleRad = Math.toRadians(refractionFromOrigin) * amplification;
        System.out.println((float) refractionAngleRad);


        // determine sign switch
        if (newY > 0) { // refraction occurred above the midpoint
            return (float) refractionAngleRad; // return negative radian value
        }

        return (float) -refractionAngleRad; // return positive radian value (refraction occurred below the midpoint)

    }

    public void drawLine(float m, float b, int[] rgb) {
        for (int i = 200; i != 400; i++) {
            applet.fill(rgb[0], rgb[1], rgb[2]);
            System.out.printf("(%f,%f)",m*i+b,Math.abs(i-b/m));
            applet.ellipse(m*i+b+600,Math.abs(i-b/m)+100, 10, 10);
        }
    }

}
