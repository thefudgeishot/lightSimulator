import processing.core.PApplet;

public class biConvexLens extends lens {

    public biConvexLens(PApplet applet, int TOP_OFFSET, int x, float scale, float height) {

        super(new convexFace(applet,scale,height,new float[]{x+225,TOP_OFFSET}),new concaveFace(applet,scale, height,new float[]{x,TOP_OFFSET}),applet, TOP_OFFSET);
    }
}
