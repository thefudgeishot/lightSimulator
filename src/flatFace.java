import processing.core.*;

public class flatFace extends face {

    // ** FACE IS VERTICAL

    int samples = 100;

    public flatFace(PApplet applet, float height, float[] origin) {
        super(applet, height, origin);
    }

    @Override
    public float getX(double y) {
        return origin[0];
    }

    @Override
    public double getSlope(double y) {
        return 0;
    }

    @Override
    public void drawFace() {
        float offset = height/samples;
        for (int i = 0; i != samples; i++) {
            applet.noStroke();
            applet.fill(200,200,200);
            applet.ellipse(origin[0],210+(i*offset),10,10);
        }
    }
}
