package model;

/**
 * Created by Bram on 8/12/2015.
 */
public class Score {

    private String name;
    private int score;

    public Score(){

    }

    public Score(String name, int score){
        setName(name);
        setScore(score);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
