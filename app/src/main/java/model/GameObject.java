package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.Random;

/**
 * Created by Bram on 8/15/2015.
 */
public abstract class GameObject {

    private Position position;
    private String direction;
    private Bitmap bitmap, resizedBitmap;
    private Matrix matrix;
    private float width, height;
    private float scaleWidth, scaleHeight;
    private double speed;

    public GameObject(){
        matrix = new Matrix();
        //random startDirection
        setRandomDirection();
    }

    public GameObject(Position position, float height, float width, Bitmap bitmap, double speed){
        this();



        setPosition(position);
        setHeight(height);
        setWidth(width);
        setBitmap(bitmap);
        setSpeed(speed);
    }

    public void setRandomDirection(){
        Random random = new Random();
        int r = random.nextInt(4);

        if(r == 1){
            setDirection("W");
        }
        else if(r == 2){
            setDirection("N");
        }
        else if(r == 3){
            setDirection("W");
        }
        else{
            setDirection("S");
        }
    }

    public void drawGameObject(Canvas canvas){

        canvas.drawBitmap(bitmap, (float) position.getX(), (float) position.getY(), null);
        height = bitmap.getHeight();
        width = bitmap.getWidth();
    }

    public void resize(Canvas canvas){

       ///Scale width has to be smaller than height. TODO Why? Mistery for now

        scaleHeight = ((float) canvas.getHeight()/5) / bitmap.getHeight();
        scaleWidth = ((float) canvas.getWidth()/6) / bitmap.getWidth();

        matrix.postScale(scaleWidth, scaleHeight);

        resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap = resizedBitmap;

        width = (float)position.getX() + bitmap.getWidth();
        height = (float)position.getY() + bitmap.getHeight();
    }

    public void rotate(String direction){
        //TODO make sprite rotate to facing direction
        /*switch (direction){
            case "W":
                getMatrix().postRotate(90);
                break;
            case "N":
                getMatrix().postRotate(180);
                break;
            case "E":
                getMatrix().postRotate(270);
                break;
            default:
                break;
        }

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,(int)width,(int)height,true);

        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);

        bitmap = rotatedBitmap;*/

    }

    public double getLeft(){
        return this.getPosition().getX();
    }

    public double getRight(){
        return this.getPosition().getX() + this.getWidth();
    }

    public double getTop(){
        return this.getPosition().getY();
    }

    public double getBottom(){
        return this.getPosition().getY() + this.getHeight();
    }

    /*public void moveLeft(){
        setPosition(new Position(getPosition().getX() - getSpeed(), getPosition().getY()));
    }

    public void moveRight(){
        setPosition(new Position(getPosition().getX() + getSpeed(), getPosition().getY()));
    }

    public void moveUp(){
        setPosition(new Position(getPosition().getX(), getPosition().getY() - getSpeed()));
    }

    public void moveDown(){
        setPosition(new Position(getPosition().getX(), getPosition().getY() + getSpeed()));
    }*/

    public void moveLeft() {
        //setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mexican_stand_w));
        setDirection("W");
        rotate(getDirection());
        setPosition(new Position(getPosition().getX() - getSpeed(), getPosition().getY()));
    }


    public void moveRight() {
        //setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mexican_stand_e));
        setDirection("E");
        rotate(getDirection());
        setPosition(new Position(getPosition().getX() + getSpeed(), getPosition().getY()));
    }


    public void moveUp() {
        //setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mexican_stand_n));
        setDirection("N");
        rotate(getDirection());
        setPosition(new Position(getPosition().getX(), getPosition().getY() - getSpeed()));
    }


    public void moveDown(){
        //setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mexican_stand_s));
        setDirection("S");
        rotate(getDirection());
        setPosition(new Position(getPosition().getX(), getPosition().getY() + getSpeed()));
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getScaleWidth() {
        return scaleWidth;
    }

    public void setScaleWidth(float scaleWidth) {
        this.scaleWidth = scaleWidth;
    }

    public float getScaleHeight() {
        return scaleHeight;
    }

    public void setScaleHeight(float scaleHeight) {
        this.scaleHeight = scaleHeight;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
