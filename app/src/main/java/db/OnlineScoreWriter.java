package db;

import android.os.StrictMode;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.Score;

/**
 * Created by Bram on 8/23/2015.
 */
public class OnlineScoreWriter implements ScoreWriter {

    private Connection connection;
    private PreparedStatement statement;

    public OnlineScoreWriter(String url, Properties properties) throws ClassNotFoundException, SQLException{
        /* SDK<9 no online option */
        if (android.os.Build.VERSION.SDK_INT >= 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        connection = DriverManager.getConnection(url, properties);
        connection.createStatement();
    }

    @Override
    public void write(Score score)  throws SQLException{
    }

    @Override
    public void write(String playerName, int score) throws SQLException{
        statement = connection.prepareStatement("INSERT INTO ratnest(name, score) VALUES(?,?)");
        statement.setString(1, playerName);
        statement.setInt(2, score);
        statement.executeUpdate();
    }

    @Override
    public List<Score> getScores() throws SQLException{
        List<Score> scores = new ArrayList<Score>();

        statement = connection.prepareStatement("SELECT * FROM ratnest ORDER BY score DESC LIMIT 10");

        ResultSet set = statement.executeQuery();

        for(int i = 0; set.next(); i++){
            scores.add(new Score(set.getString(1), set.getInt(2)));
        }

        return scores;
    }

    @Override
    public void closeConnection() throws SQLException{
        connection.close();
    }
}
