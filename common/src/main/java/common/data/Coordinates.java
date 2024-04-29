package common.data;

import java.io.Serializable;

/**
 * Coordinates class.
 */
public class Coordinates implements Serializable {
    /**
     * X coordinate.
     */
    private float x;
    /**
     * Y coordinate.
     */
    private double y;

    /**
     * Coordinates class constructor.
     * @param x
     * @param y
     */
    public Coordinates(float x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * X coordinate getter.
     * @return X coordinate.
     */
    public float getX() {
        return x;
    }


    /**
     * Y coordinate getter.
     * @return Y coordinate.
     */
    public double getY() {
        return y;
    }
}
