package biteStrategy;

import com.rabbithole.bram.ratnest.GameActivity;

import model.Position;
import model.Rat;
import model.RatType;

/**
 * Created by Bram on 8/12/2015.
 */
public class RatFactory {
    public Rat createStandardRat(Position position, double speed, float ratWidth, float ratHeigh, GameActivity activity){
        Rat rat = new Rat(position, speed, RatType.SMALL, ratWidth, ratHeigh, activity);

        return rat;
    }


}
