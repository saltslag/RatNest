package model;

import java.util.Random;

/**
 * Created by Bram on 8/2/2015.
 */
public class Position {

    private double x;
    private double y;

    public Position(){

    }

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean equals(Object o){
        if(o instanceof Position){
            Position p = (Position) o;
            if(p.getX() == this.getX() && p.getY() == this.getY()){
                return true;
            }
        }
        return false;
    }

    public void setRandomPosition(int maxX, int maxY){
        Random random = new Random();
        setX(random.nextInt(maxX));
        setY(random.nextInt(maxY));
    }
}
