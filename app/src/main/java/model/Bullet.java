package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.rabbithole.bram.ratnest.GameActivity;
import com.rabbithole.bram.ratnest.R;

/**
 * Created by Bram on 8/13/2015.
 */
public class Bullet extends GameObject {

    public Bullet(Position position, GameActivity activity){
        setPosition(position);
        setHeight(50);
        setWidth(50);
        setSpeed(20);
        setDirection("S");
        setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bullet));
    }

    public Bullet(Position position, float height, float width,  double speed, GameActivity activity){
        setPosition(position);
        setHeight(height);
        setWidth(width);
        setSpeed(speed);
        setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bullet));
    }

    public void move(){
        switch (getDirection()){
            case "N":
                this.moveUp();
                break;
            case "E":
                this.moveRight();
                break;
            case "W":
                this.moveLeft();
                break;
            default:
                this.moveDown();
                break;
        }
    }


}
