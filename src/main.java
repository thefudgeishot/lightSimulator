import processing.core.*;

import java.util.ArrayList;

public class main extends PApplet {

    int samples = 20; // number of lasers
    int height = 300;
    int stepSize = 1;
    int simLength = 1600;

    int simPosition = 0;

    public int TOP_OFFSET = 100;

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
            particleList.add(new particle((float)0,10,100,TOP_OFFSET+15+(i*offset))); // sim offsets 100 and 210 on x and y
        }

        // lens test = new lens(new concaveFace(this,3,300,new float[]{600,TOP_OFFSET}), new flatFace(this,300, new float[]{800,TOP_OFFSET}), this, TOP_OFFSET);
        // lens test = new lens(new concaveFace(this,4,300,new float[]{600,TOP_OFFSET}), new concaveFace(this,4, 300,new float[]{650,TOP_OFFSET}), this, TOP_OFFSET);
        // lens test = new lens(new concaveFace(this,4, 300,new float[]{250,TOP_OFFSET}), new convexFace(this,4,300,new float[]{600,TOP_OFFSET}),this, TOP_OFFSET);

        // lens test = new lens(new convexFace(this,4,300,new float[]{475,TOP_OFFSET}),new concaveFace(this,4, 300,new float[]{250,TOP_OFFSET}),this, TOP_OFFSET);

        // biconvex lens - focus image to a point
        lens test = new biConvexLens(this, TOP_OFFSET, 150, 4, 300);
        lensList.add(test);
        test.renderLens();

        // biconcave lens - begin image magnification
        lens test1 = new biConcaveLens(this, TOP_OFFSET, 175, 4, 300);
        lensList.add(test1);
        test1.renderLens();

        // biconvex lens - begin focusing image
        lens test2 = new biConvexLens(this, TOP_OFFSET, 1375, 4, 300);
        lensList.add(test2);
        test2.renderLens();

        // biconvex lens - focus image to a point
        // lens test3 = new biConvexLens(this, TOP_OFFSET, 1475, 3, 300);
        // lensList.add(test3);
        // test3.renderLens();

        // render sim limits
        for (int i = 0; i != ((float) simLength /stepSize); i++) {
            fill(100);
            ellipse(100+i, TOP_OFFSET, 10,10); // top limit
            ellipse(100+i, TOP_OFFSET+height, 10, 10); // bottom limit
        }
    }

    @Override
    public void draw() {

        if (simLength/stepSize != simPosition) {

            for (int i = 0; i != particleList.size(); i++) {
                particle particle = particleList.get(i);

                // if particle goes out of bounds... KILL IT
                if (particle.coordinate[1] <= TOP_OFFSET || (TOP_OFFSET+height) <= particle.coordinate[1] ) {
                    particle.isAlive = false;
                }

                // skip the particle if it's dead
                if (!particle.isAlive) {
                    continue;
                }

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
                ellipse(temp[0], temp[1], 2, 2);

                // store data
                data[i][simPosition] = temp;

                // step data
                particleList.get(i).step(stepSize);

            }
            simPosition++;
        }
    }
}
