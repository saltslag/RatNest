package model;

import android.os.CountDownTimer;

import com.rabbithole.bram.ratnest.GameActivity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Bram on 8/12/2015.
 */
public class Game {

    private Timer gameTimer, ratTimer;
    private Score endScore;
    private int score;
    private Mexican mexican;
    private CopyOnWriteArrayList<Rat> rats; //thread-safe arraylist implementation
    private boolean isStarted, isPaused, isGameOver;

    public Game(){
        rats = new CopyOnWriteArrayList<Rat>();
        score = 0;
        gameTimer = new Timer();
        ratTimer = new Timer();
    }

    public Game(Mexican mexican){
        this();
        setMexican(mexican);
    }

    public Game(Mexican mexican, CopyOnWriteArrayList<Rat> rats){
        this(mexican);
        setRats(rats);
    }

    public void kill(){

    }

    public void moveMexicanLeft(){
        /*if >= linkerkant scherm*/
        mexican.moveLeft();
    }

    public void moveMexicanUp(){
        /*if >= bovenkant scherm*/
        mexican.moveUp();

    }

    public void moveMexicanRight(){
        /*if <= rechterkant scherm*/
        mexican.moveRight();
    }

    public void moveMexicanDown(){
        /*if >= onderkant scherm*/
        mexican.moveDown();
    }

    public Timer getGameTimer() {
        return gameTimer;
    }

    public void setGameTimer(Timer gameTimer) {
        this.gameTimer = gameTimer;
    }

    public Timer getRatTimer() {
        return ratTimer;
    }

    public void setRatTimer(Timer ratTimer) {
        this.ratTimer = ratTimer;
    }

    public Score getEndScore() {
        return endScore;
    }

    public void setEndScore(Score endScore) {
        this.endScore = endScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRats(CopyOnWriteArrayList<Rat> rats) {
        this.rats = rats;
    }



    public Mexican getMexican() {
        return mexican;
    }

    public void setMexican(Mexican mexican) {
        this.mexican = mexican;
    }

    public CopyOnWriteArrayList getRats() {
        return rats;
    }

    public void addRat(Rat rat){
        rats.add(rat);
    }

    public void addRat(double maxX, double maxY, float ratWidth, float ratHeight, GameActivity activity){
        Position position = new Position();
        position.setRandomPosition((int)maxX, (int)maxY);
        rats.add(new Rat(position, ratHeight, ratWidth, activity));
    }

    public void removeRat(Rat rat){
        rats.remove(rat);

    }

    public void removeBullet(Bullet bullet){
        mexican.getBullets().remove(bullet);
    }

    public void addScore(int score){
        this.score += score;
    }


   /*TODO
    public CopyOnWriteArrayList generateRats(){

    }
    */

    public boolean isStarted() {
        return isStarted;
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setIsGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }
}
