package biteStrategy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.rabbithole.bram.ratnest.GameActivity;

import model.Rat;
import model.RatType;

/**
 * Created by Bram on 8/13/2015.
 */
public class BiteStrategyFactory {

    public BiteStrategy createBiteStrategy(Rat rat){

        BiteStrategy strategy;

        switch(rat.getType()){
            case TOXIC:
                strategy = new ToxicBiteStrategy();
                break;
            /*case BIG:
                strategy = new BigBiteStrategy();
                break;
                */
            default:
                strategy = new NoBiteStrategy();
        }

        return strategy;
    }

}
