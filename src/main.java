import processing.core.*;

import java.util.ArrayList;

public class main extends PApplet {

    int samples = 10; // number of lasers
    int height = 280;
    int stepSize = 1;
    int simLength = 1600;

    int simPosition = 0;

    public int TOP_OFFSET = 210;

    ArrayList<particle> particleList = new ArrayList<>();
    ArrayList<lens> lensList = new ArrayList<>();

    float[][][] data = new float[samples][(simLength/stepSize)][];

    public static void main(String args[]) {
        PApplet.main("main");
    }

    @Override
    public void settings() {
        size(1820, 700);
    }

    @Override
    public void setup() {
        clear();
        background(0);

        // load particles into list
        int offset = height/samples;
        for (int i = 0; i != samples; i++) {
            particleList.add(new particle(0,10,100,230+(i*offset))); // sim offsets 100 and 210 on x and y
        }

        // lens test = new lens(new concaveFace(this,3,300,new float[]{600,TOP_OFFSET}), new flatFace(this,300, new float[]{800,TOP_OFFSET}), this, TOP_OFFSET);
        // lens test = new lens(new concaveFace(this,4,300,new float[]{600,TOP_OFFSET}), new concaveFace(this,4, 300,new float[]{650,TOP_OFFSET}), this, TOP_OFFSET);
        // lens test = new lens(new concaveFace(this,4, 300,new float[]{650,TOP_OFFSET}), new convexFace(this,4,300,new float[]{1000,TOP_OFFSET}),this, TOP_OFFSET);
        lens test = new lens(new convexFace(this,4,300,new float[]{875,TOP_OFFSET}),new concaveFace(this,4, 300,new float[]{650,TOP_OFFSET}),this, TOP_OFFSET);
        lensList.add(test);

        test.renderLens();
    }

    @Override
    public void draw() {

        if (simLength/stepSize != simPosition) {

            for (int i = 0; i != particleList.size(); i++) {
                particle particle = particleList.get(i);

                // if a collision with a lens is made
                for (int j = 0; j != lensList.size(); j++) {
                    if (lensList.get(j).isIntersecting(particle)) {
                        System.out.println("Particle is intersecting");
                        // update angular direction
                        particle.setAngularDirection(particle.getAngularDirection() + lensList.get(j).refractionIndex(particle, data[i]));
                    }
                }

                // draw particles
                float[] temp = particle.getCoordinate();
                //stroke(0);
                fill(0, 255, 0);
                ellipse(temp[0], temp[1], 5, 5);

                // store data
                data[i][simPosition] = temp;

                // step data
                particleList.get(i).step(stepSize);

            }
            simPosition++;
        }
    }
}
