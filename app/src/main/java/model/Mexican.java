package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.rabbithole.bram.ratnest.GameActivity;
import com.rabbithole.bram.ratnest.R;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Bram on 8/2/2015.
 */
public class Mexican extends GameObject{

     //to know which direction the bullet has to travel
    private int lives;
    private GameActivity activity;
    private boolean leftGun;
    private ConcurrentLinkedQueue<Bullet> bullets;

    public Mexican(){
        super();
        bullets = new ConcurrentLinkedQueue<Bullet>();
        setLives(5);
        setSpeed(15);
        setWidth(10);
        setHeight(10);
    }

    public Mexican(Position position, GameActivity activity){
        this();
        setPosition(position);
        setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mexican_stand_s));

        this.activity = activity;
    }

    public Mexican(Position position, GameActivity activity, float height, float width, Bitmap bitmap, double speed) {
        this(position, activity);
        setHeight(height);
        setWidth(width);
        setSpeed(speed);
        setBitmap(bitmap);

        this.activity = activity;
    }

    public Mexican(Position position, GameActivity activity, float height, float width, Bitmap bitmap,  double speed, int lives){
        this(position, activity, height, width, bitmap, speed);
        setLives(lives);
    }

    public Mexican(Position position, GameActivity activity, float height, float width, Bitmap bitmap,  double speed, int lives, ConcurrentLinkedQueue<Bullet> bullets){
        this(position, activity, height, width, bitmap, speed, lives);
        setBullets(bullets);
    }


    public void fire(GameActivity activity){
        /*
            fire gun

         */

       /* if(leftGun){
            setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mexican_shot_left));
        }
        else {
            setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mexican_shot_right));
        }*/


        Bullet bullet = new Bullet(this.getPosition(), activity);
        bullet.setDirection(this.getDirection());
        bullets.add(bullet);


    }

    @Override
    public void moveLeft() {
        //setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mexican_stand_w));
        setDirection("W");
        rotate(getDirection());
        setPosition(new Position(getPosition().getX() - getSpeed(), getPosition().getY()));
    }

    @Override
    public void moveRight() {
        //setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mexican_stand_e));
        setDirection("E");
        rotate(getDirection());
        setPosition(new Position(getPosition().getX() + getSpeed(), getPosition().getY()));
    }

    @Override
    public void moveUp() {
        //setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mexican_stand_n));
        setDirection("N");
        rotate(getDirection());
        setPosition(new Position(getPosition().getX(), getPosition().getY() - getSpeed()));
    }

    @Override
    public void moveDown(){
        //setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mexican_stand_s));
        setDirection("S");
        rotate(getDirection());
        setPosition(new Position(getPosition().getX(), getPosition().getY() + getSpeed()));
    }

    public void loseLive(int nr){
        lives -= nr;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }


    public ConcurrentLinkedQueue<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ConcurrentLinkedQueue<Bullet> setBullets) {
        this.bullets = bullets;
    }
}
