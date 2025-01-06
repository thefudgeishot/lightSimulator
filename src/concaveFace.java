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

    static double nthRoot(int A, int N)
    {

        // initially guessing a random number between
        // 0 and 9
        double xPre = Math.random() % 10;

        // smaller eps, denotes more accuracy
        double eps = 0.001;

        // initializing difference between two
        // roots by INT_MAX
        double delX = 2147483647;

        // xK denotes current value of x
        double xK = 0.0;

        // loop until we reach desired accuracy
        while (delX > eps)
        {
            // calculating current value from previous
            // value by newton's method
            xK = ((N - 1.0) * xPre +
                    (double)A / Math.pow(xPre, N - 1)) / (double)N;
            delX = Math.abs(xK - xPre);
            xPre = xK;
        }

        return xK;
    }

    @Override
    // input must be in relation to the origin point and height
    // if height was 150, the y value must range from -150 to 150
    public float getX(double y) {
        double temp = (Math.sqrt( (angle - Math.pow((y),2)  / scale)));
        return (float) (temp + origin[0]);
    }

    public double getSlope(double y) {

        return nthRoot((int) (angle/scale - (Math.pow((y),2)/scale)), (int) scale)/y;
        // System.out.println(scale*getX(y)/y);
        // return (scale*(getX(y)-200)/(y));
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

            // System.out.printf("x = %d, y = %d \n", (int) getX(y), (int) (190+(i*offset)));
            applet.ellipse(getX(y), (float) (origin[1]+(i*offset)), 10,10);
        }
    }
}
