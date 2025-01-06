public class particle {

    float angularDirection; // in radians
    float velocity;
    float[] coordinate = new float[2];

    public particle(float angularDirection, float velocity ,float x, float y) {
        this.angularDirection = angularDirection;
        this.velocity = velocity;
        this.coordinate[0] = x;
        this.coordinate[1] = y;
    }

    public float getAngularDirection() {
        return this.angularDirection;
    }

    public void setAngularDirection(float angularDirection) {
        this.angularDirection = angularDirection;
    }

    public float[] getCoordinate() {
        return this.coordinate;
    }

    public float getVelocity() {
        return velocity;
    }

    // https://www.desmos.com/calculator/oz956zgm5h
    public float getSlope() {

        double numerator = (Math.sin(angularDirection)*3 - Math.sin(angularDirection));
        double denominator = (Math.cos(angularDirection)*3 - Math.cos(angularDirection));

        return (float) (numerator/denominator);
    }

    // public void refraction(float newVelocity, )
    public void step(int stepSize) {
        float[] temp = new float[2];
        temp[0] = (float) (coordinate[0] + (stepSize * Math.cos(angularDirection)));
        temp[1] = (float) (coordinate[1] + (stepSize * Math.sin(angularDirection)));

        coordinate = temp;
    }

}
