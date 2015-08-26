package db;

import java.sql.SQLException;
import java.util.List;

import model.Score;

/**
 * Created by Bram on 8/23/2015.
 */
public interface DBFacade {
    ScoreWriter getScoreWriter();
    void setWriter(ScoreWriter sw);
    void write(String playerName, int score) throws SQLException;
    void write(Score score) throws SQLException;
    List<Score> getScore() throws SQLException;
    void closeConnection() throws SQLException;
}
