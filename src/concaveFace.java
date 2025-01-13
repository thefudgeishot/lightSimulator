import processing.core.PApplet;

public class concaveFace extends face {

    float scale;
    float angle;

    int samples = 100;

    // https://www.desmos.com/calculator/rlrwrhtbxo
    public concaveFace(PApplet applet, float scale, float height, float[] origin) {
        super(applet, height/2, origin);
        this.scale = scale;
        this.angle = (height/2)*(height/2);
    }


    @Override
    // input must be in relation to the origin point and height
    // if height was 150, the y value must range from -150 to 150
    public float getX(double y) {
        double temp = (Math.sqrt( (angle - Math.pow((y),2)  / scale)));
        return (float) (temp + origin[0]);
    }

    public double getSlope(double y) {

        return y/(Math.sqrt(scale)*Math.sqrt(angle-Math.pow(y,2)));
    }

    @Override
    public void drawFace() {
        double offset = (height*2)/samples; // height of graph is two times the variable
        for (int i =0; i != samples+1; i++) {
            double y = ((height*2)/2)-(i*offset); // y slice
            System.out.println(y);
            // System.out.printf("Height: %d\n", (int)y);

            applet.noStroke();
            applet.fill(200,200,200);

            // System.out.printf("x = %d, y = %d \n", (int) getX(y), (int) (origin[1]+(i*offset)));
            applet.ellipse(getX(y), (float) (origin[1]+(i*offset)), 10,10);
        }
    }
}
