package db;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import model.Score;

/**
 * Created by Bram on 8/23/2015.
 */
public class OfflineScoreWriter implements ScoreWriter {

    private ArrayList<Score> scores;
    private Context context;
    private File file;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String scoreFileName = "scores.txt";

    public OfflineScoreWriter(Context context){
        this.context = context;
        scores = new ArrayList<Score>();
        scoreFileName = "highscores.txt";
    }

    @Override
    public void write(Score score) {
        loadScoreFile();
        scores.add(score);
        prepareOutput();
    }

    @Override
    public void write(String playerName, int s) {
        loadScoreFile();
        Score score = new Score(playerName, s);
        scores.add(score);
        prepareOutput();
    }

    private void prepareOutput(){
        try{
            outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(scores);

        } catch(Exception e){

        } finally{
            try{
                if(outputStream != null){
                    outputStream.flush();
                    outputStream.close();
                }

            } catch (Exception e){
                System.out.println("IO ERROR UPDATE");
            }
        }
    }

    public void sort(){
        //
    }

    @Override
    public List<Score> getScores() {
        loadScoreFile();
        return scores;
    }

    public void loadScoreFile(){
        try{
            File filesDir = context.getFilesDir();
            file = new File(filesDir, scoreFileName);
            file.createNewFile();

            inputStream = new ObjectInputStream(new FileInputStream(file));

            scores = (ArrayList<Score>) inputStream.readObject();

        } catch(Exception e){
            //TODO catch
        } finally {
            try{
                if(outputStream != null){
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e){
                //TODO catch
            }
        }

    }

    @Override
    public void closeConnection() {
        
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
