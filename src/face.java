import processing.core.PApplet;

public abstract class face {

    // flat face

    PApplet applet;

    float height;

    float[] origin; // [x-offset,y-offset from top]
    int samples;

    public face(PApplet applet, float height, float[] origin) {
        this.height = height;
        this.origin = origin;
        this.applet = applet;
    }

    public float getHeight() {
        return height;
    }
    public abstract float getX(double y);

    public abstract double getSlope(double y);

    public abstract void drawFace();
}
