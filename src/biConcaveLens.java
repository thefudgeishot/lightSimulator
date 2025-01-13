import processing.core.PApplet;

public class biConcaveLens extends lens {

    public biConcaveLens(PApplet applet, int TOP_OFFSET, int x, float scale, float height) {
        super(new concaveFace(applet,scale, height,new float[]{x,TOP_OFFSET}), new convexFace(applet,scale,height,new float[]{x+350,TOP_OFFSET}),applet, TOP_OFFSET);
    }

    @Override
    public float refractionIndex(particle point, float[][] pointData) {
        return -super.refractionIndex(point, pointData);
    }
}
