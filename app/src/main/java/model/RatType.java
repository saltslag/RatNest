package model;

/**
 * Created by Bram on 8/13/2015.
 */
public enum RatType {
    SMALL,
    TOXIC;
    //BIG;

    public static RatType getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
