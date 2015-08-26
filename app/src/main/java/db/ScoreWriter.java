package db;

import java.sql.SQLException;
import java.util.List;

import model.Score;

/**
 * Created by Bram on 8/23/2015.
 */
public interface ScoreWriter {
    void write(Score score) throws SQLException;
    void write(String playerName, int score) throws SQLException;
    List<Score> getScores() throws SQLException;
    void closeConnection() throws SQLException;
}
