package util;

import android.os.CountDownTimer;

import com.rabbithole.bram.ratnest.GameActivity;

import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import model.Bullet;
import model.Game;
import model.Mexican;
import model.Rat;
import model.Score;

/**
 * Created by Bram on 8/11/2015.
 */
public interface Facade {

    Game getGame();
    void setGame(Game game);

    void moveMexicanUp();
    void moveMexicanDown();
    void moveMexicanLeft();
    void moveMexicanRight();

    Mexican getMexican();
    CopyOnWriteArrayList<Rat> getRats();
    ConcurrentLinkedQueue<Bullet> getBullets();
    int getScore();
    void kill();

    void addRat(double maxX, double maxY, float ratWidth, float ratHeight, GameActivity activity);
    void killRat(Rat rat);
    void removeBullet(Bullet bullet);

    Timer getGameTimer();
    Timer getRatTimer();

    boolean isStarted();
    boolean isPaused();
    boolean isGameOver();

    void setIsStarted(boolean isStarted);
    void setIsPaused(boolean isPaused);
    void setIsGameOver(boolean isGameOver);


}
