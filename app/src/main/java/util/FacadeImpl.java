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
 * Created by Bram on 8/12/2015.
 */
public class FacadeImpl implements Facade {

    private Game game;
    private boolean isPaused, isGameOver, isStarted;

    public FacadeImpl(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void moveMexicanUp() {

        game.moveMexicanUp();
    }

    @Override
    public void moveMexicanDown() {
        game.moveMexicanDown();
    }

    @Override
    public void moveMexicanLeft() {
        game.moveMexicanLeft();
    }

    @Override
    public void moveMexicanRight() {
        game.moveMexicanRight();
    }

    @Override
    public Mexican getMexican() {
        return game.getMexican();
    }

    @Override
    public CopyOnWriteArrayList getRats() {
        return game.getRats();
    }

    public void addRat(double maxX, double maxY, float ratWidth, float ratHeight, GameActivity activity){
        game.addRat(maxX, maxY, ratWidth, ratHeight, activity);
    }

    public void killRat(Rat rat){
        game.removeRat(rat);
    }

    public void removeBullet(Bullet bullet){
        game.removeBullet(bullet);
    }

    @Override
    public ConcurrentLinkedQueue<Bullet> getBullets() {
        return game.getMexican().getBullets();
    }

    @Override
    public int getScore() {
        return game.getScore();
    }

    @Override
    public void kill() {
        game.kill();
    }

    @Override
    public Timer getGameTimer() {
        return game.getGameTimer();
    }

    @Override
    public Timer getRatTimer() {
        return game.getRatTimer();
    }

    public boolean isStarted(){
        return isStarted;
    }

    public boolean isPaused(){
        return isPaused;
    }

    public boolean isGameOver(){
        return isGameOver;
    }

    @Override
    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    @Override
    public void setIsGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    @Override
    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }
}
