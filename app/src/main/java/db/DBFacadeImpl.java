package db;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Score;

/**
 * Created by Bram on 8/23/2015.
 */
public class DBFacadeImpl implements DBFacade{

    private ScoreWriter scoreWriter;

    public DBFacadeImpl(ScoreWriter sw){
        setScoreWriter(sw);
    }

    @Override
    public ScoreWriter getScoreWriter() {
        return scoreWriter;
    }

    @Override
    public void setWriter(ScoreWriter sw) {
        scoreWriter = sw;
    }

    @Override
    public void write(String playerName, int score) throws SQLException {
        scoreWriter.write(playerName, score);
    }

    @Override
    public void write(Score score) throws SQLException {
        scoreWriter.write(score);
    }

    @Override
    public List<Score> getScore() throws SQLException {
        return scoreWriter.getScores();
    }

    @Override
    public void closeConnection() throws SQLException {
        scoreWriter.closeConnection();
    }

    public void setScoreWriter(ScoreWriter scoreWriter) {
        this.scoreWriter = scoreWriter;
    }
}
