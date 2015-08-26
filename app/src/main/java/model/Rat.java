package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.rabbithole.bram.ratnest.GameActivity;
import com.rabbithole.bram.ratnest.R;

import java.util.Random;

import biteStrategy.BiteStrategy;
import biteStrategy.BiteStrategyFactory;

/**
 * Created by Bram on 8/2/2015.
 */
public class Rat extends GameObject{

    private RatType type;
    private BiteStrategy biteStrategy;


    //Not giving type results in random rat type

    public Rat(Position position, float ratWidth, float ratHeight, GameActivity activity){
        super();
        setPosition(position);
        setSpeed(2);
        setWidth(ratWidth);
        setHeight(ratHeight);
        setType(RatType.getRandom());
        setBitmapForType(activity);
        biteStrategy = new BiteStrategyFactory().createBiteStrategy(this);
    }

    public Rat(Position position, double speed, float ratWidth, float ratHeight, GameActivity activity){
        setPosition(position);
        setSpeed(speed);
        setType(type);
        setWidth(ratWidth);
        setHeight(ratHeight);
        setType(RatType.getRandom());
        setBitmapForType(activity);
    }

    public Rat(Position position, double speed, RatType type, float ratWidth, float ratHeight, GameActivity activity){
        setPosition(position);
        setSpeed(speed);
        setType(type);
        setWidth(ratWidth);
        setHeight(ratHeight);

        setBitmapForType(activity);
    }

    public void setBitmapForType(GameActivity activity){
        if(this.getType().equals(RatType.SMALL)){
            setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.rat_standard_sm));
        }
        else if(this.getType().equals(RatType.TOXIC)){
            setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.rat_rabbies_1));
        }
        /*else{
            setBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.rat_standard_sm));
        }*/
    }

    public Position getRandomSpawnPosition(int xMax, int yMax){
        //TODO generate x, y coordinates inside screen size
        Random random = new Random();

        double x = random.nextInt(xMax);
        double y = random.nextInt(yMax);

        Position position = new Position(x,y);
        return position;
    }

    public RatType getType() {
        return type;
    }

    public void setType(RatType type) {
        this.type = type;
    }

    public BiteStrategy getBiteStrategy() {
        return biteStrategy;
    }

    public void setBiteStrategy(BiteStrategy biteStrategy) {
        this.biteStrategy = biteStrategy;
    }


    public void move(){
        //makes the rat move true the room in a random way
        Random random = new Random();
        int rnd = random.nextInt(4);
        int stepts = random.nextInt(10);
        //TODO make better
        if(rnd == 1){
            for(int i = 0; i < stepts; i++){
                moveLeft();
            }
        }
        else if(rnd == 2){
            for(int i = 0; i < stepts; i++){
                moveUp();
            }
        }
        else if(rnd == 3){
            for(int i = 0; i < stepts; i++){
                moveRight();
            }
        }
        else {
            for (int i = 0; i < stepts; i++) {
                moveDown();
            }
        }
    }
}
