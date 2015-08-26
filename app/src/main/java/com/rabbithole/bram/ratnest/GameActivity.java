package com.rabbithole.bram.ratnest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

import db.DBFacade;
import db.DBFacadeImpl;
import db.OfflineScoreWriter;
import db.OnlineScoreWriter;
import model.Bullet;
import model.Rat;
import model.RatType;
import model.Score;
import util.ConnectionControler;
import util.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import model.Game;
import model.Mexican;
import model.Position;
import util.Facade;
import util.FacadeImpl;


public class GameActivity extends Activity implements SensorEventListener{

    private View view;
    private Facade facade;
    private Mexican mexican;
    private Handler handler;
    private SensorManager sensorManager;
    private CountDownTimer cooldownTimer;
    private int screenWidth, screenHeight;

    private ImageView pauseButton, resumeButton;
    private DBFacade dbFacade;
    private ConnectionControler cc;

    private void initializeComponents(){
        configureScreenSize();
        mexican = new Mexican(new Position(screenWidth/2,screenHeight/2),this);
        facade = new FacadeImpl(new Game(mexican));

        configureView();
        configureSensors();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_game);


        pauseButton = (ImageView)findViewById(R.id.pauseButton);
        pauseButton.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(android.view.View v, MotionEvent event){
                pauseGame();
                return false;
            }
        });
        initializeComponents();
    }

    protected void onDestroy(){
        super.onDestroy();
    }

    //---------------------------------------------------------------GAMESTATES

    private void startGame(){
        handler = new Handler();
        handler.postDelayed(thread, 0);
        handler.postDelayed(ratTimer, 0);
        facade.setIsStarted(true);
        facade.setIsGameOver(false);
        pauseButton.setVisibility(View.VISIBLE);
    }

    private void stopGame(){
        handler.removeCallbacks(thread);
        handler.removeCallbacks(ratTimer);
        facade.setIsGameOver(true);
        facade.setIsStarted(false);
        gameOver();
    }

    private void restartGame(){
        findViewById(R.id.gameOverText).setVisibility(View.INVISIBLE);
        TextView scoreText = (TextView)findViewById(R.id.scoreText);
        scoreText.setText("0");

        handler.postDelayed(thread, 0);

        facade.setIsGameOver(false);
        facade.setIsStarted(false);
        initializeComponents();
        facade.getRats().clear();
        facade.getBullets().clear();
        facade.getGame().setScore(0);
        facade.getGame().getMexican().setLives(5);
        showCorrectLives();
    }

    public void exitGame(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void pauseGame() {
        //showSettings();
        handler.removeCallbacks(thread);
        handler.removeCallbacks(ratTimer);
        facade.setIsPaused(true);
        pauseButton.setVisibility(View.INVISIBLE);
        resumeButton.setVisibility(View.VISIBLE);
        sensorManager.unregisterListener(this);
    }

    private void unpauseGame() {
        handler.postDelayed(thread, 0);
        facade.setIsPaused(false);
        pauseButton.setVisibility(View.VISIBLE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    private void gameOver(){
        findViewById(R.id.gameOverText).setVisibility(View.VISIBLE);
        gameOverMenu();
    }

    private void gameOverMenu(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GAME OVER");
        builder.setMessage("Restart?");
        builder.setNegativeButton("Menu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di, int btn) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                GameActivity.this.finish();
            }
        });

        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di, int btn) {
                restartGame();
            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }

    //---------------------------------------------------------------RATS&BULLETS&LIVES


    private void showCorrectLives(){
        int lives = mexican.getLives();



        if(lives == 5){
            findViewById(R.id.life5).setVisibility(View.VISIBLE);
            findViewById(R.id.life4).setVisibility(View.VISIBLE);
            findViewById(R.id.life3).setVisibility(View.VISIBLE);
            findViewById(R.id.life2).setVisibility(View.VISIBLE);
            findViewById(R.id.life1).setVisibility(View.VISIBLE);
        }
        else if(lives == 4){
            findViewById(R.id.life5).setVisibility(View.INVISIBLE);
        }
        else if(lives == 3){
            findViewById(R.id.life5).setVisibility(View.INVISIBLE);
            findViewById(R.id.life4).setVisibility(View.INVISIBLE);
        }
        else if(lives == 2){
            findViewById(R.id.life5).setVisibility(View.INVISIBLE);
            findViewById(R.id.life4).setVisibility(View.INVISIBLE);
            findViewById(R.id.life3).setVisibility(View.INVISIBLE);

        }
        else if(lives == 1){
            findViewById(R.id.life5).setVisibility(View.INVISIBLE);
            findViewById(R.id.life4).setVisibility(View.INVISIBLE);
            findViewById(R.id.life3).setVisibility(View.INVISIBLE);
            findViewById(R.id.life2).setVisibility(View.INVISIBLE);

        }
        else if(lives <= 0){
            findViewById(R.id.life5).setVisibility(View.INVISIBLE);
            findViewById(R.id.life4).setVisibility(View.INVISIBLE);
            findViewById(R.id.life3).setVisibility(View.INVISIBLE);
            findViewById(R.id.life2).setVisibility(View.INVISIBLE);
            findViewById(R.id.life1).setVisibility(View.INVISIBLE);

            facade.setIsGameOver(true);
        }


    }


    public void addRat(){
        addRat(screenWidth - 200, screenHeight - 200, 20, 20, this);
    }

    private void addRat(Rat rat){
        facade.getGame().addRat(rat);
    }

    private void addRat(double maxX, double maxY, float ratWidth, float ratHeight, GameActivity activity){
        facade.addRat(maxX, maxY, ratWidth, ratHeight, this);
    }

    public void processRats(){
        CopyOnWriteArrayList<Rat> rats = facade.getRats();

        if(rats != null && !rats.isEmpty()){
            for (Rat rat: rats){
                if(rat.getTop() - rat.getSpeed() >= 0){
                    if(rat.getLeft() - rat.getSpeed() >= 0){
                        if(rat.getRight() + rat.getSpeed() <= screenWidth){
                            if(rat.getBottom() + rat.getSpeed() <= screenHeight){

                                rat.move();
                            }
                            else{
                                rat.moveUp();
                            }
                        }
                        else{
                            rat.moveLeft();
                        }
                    }
                    else{
                        rat.moveRight();
                    }
                }
                else{
                    rat.moveDown();
                }

            }

        }
    }

    public void processBullets(){
        ConcurrentLinkedQueue<Bullet> bullets = mexican.getBullets();
        if(bullets != null && !bullets.isEmpty()){
            for (Bullet bullet: bullets){
                if(bullet.getLeft()<0 || bullet.getTop() < 0 || bullet.getRight() > screenWidth || bullet.getBottom() > screenHeight){
                    bullets.remove(bullet);
                }
                bullet.move();
            }
        }
    }

    //---------------------------------------------------------------COLISSIONS

    private void processColissionBetweenMexicanAndRat() {
        Iterator<Rat> ratIterator = facade.getRats().iterator();
        while(ratIterator.hasNext()) {
            Rat rat = ratIterator.next();

            //TODO make oval for better precision
            Rect ratRect = new Rect((int)rat.getLeft(), (int)rat.getTop(), (int)rat.getRight(), (int)rat.getBottom());
            Rect mexicanRect = new Rect((int)mexican.getLeft(), (int)mexican.getTop(), (int)mexican.getRight(), (int)mexican.getBottom());

            if(mexicanRect.intersect(ratRect)) {
                facade.getMexican().loseLive(rat.getBiteStrategy().bite());
                showCorrectLives();
                facade.getGame().removeRat(rat);
                TextView scoreText = (TextView)findViewById(R.id.scoreText);
                int score = facade.getScore();
                scoreText.setText(Integer.toString(score));
            }
        }
    }

    private void processColissionBetweenBulletAndRat() {
        ConcurrentLinkedQueue<Bullet> bullets = facade.getBullets();
        CopyOnWriteArrayList<Rat> rats = facade.getRats();
        for(Bullet bullet:bullets) {
            Rect bulletRect = new Rect((int)bullet.getLeft(), (int)bullet.getTop(), (int)bullet.getRight(), (int)bullet.getBottom());

            for(Rat rat:rats){
                Rect ratRect = new Rect((int)rat.getLeft(), (int)rat.getTop(), (int)rat.getRight(), (int)rat.getBottom());
                if(Rect.intersects(bulletRect, ratRect)) {

                    facade.getGame().addScore(rat.getBiteStrategy().bite());
                    facade.getGame().removeRat(rat);
                    facade.getGame().removeBullet(bullet);
                    TextView scoreText = (TextView)findViewById(R.id.scoreText);
                    int score = facade.getScore();
                    scoreText.setText(Integer.toString(score));
                    break;
                }
            }
        }
    }

    //---------------------------------------------------------------SENSORS
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(!facade.isGameOver() && facade.isStarted()){

            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                float xAcceleration = -event.values[0];
                float yAcceleration = -event.values[1];
                if(xAcceleration < - 1) {
                    if(mexican.getBottom() + mexican.getSpeed() <= screenHeight){
                        facade.moveMexicanDown();
                    }

                }
                if(xAcceleration > 1) {
                    if(mexican.getTop() - mexican.getSpeed() >= 0){
                        facade.moveMexicanUp();
                    }
                }
                if(yAcceleration < - 1) {
                    if(mexican.getRight() + mexican.getSpeed() <= screenWidth) {
                        facade.moveMexicanRight();
                    }
                }
                if(yAcceleration > 1) {
                    if(mexican.getLeft() - mexican.getSpeed() >= 0){
                        facade.moveMexicanLeft();
                    }
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void configureView(){
        view = (View)findViewById(R.id.view);
        view.setWillNotDraw(false);
        view.setFacade(facade);
        view.setClickable(true);

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(android.view.View view, MotionEvent event) {
                if(!facade.isStarted()){
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        try {
                            if(!facade.isGameOver()){
                                startGame();
                            }

                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        view.performClick();

                    }
                }


                else if(facade.isStarted() && !facade.isGameOver()){
                    facade.getMexican().fire(GameActivity.this);

                }

                return true;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void configureScreenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getRealSize(size);

        screenHeight = size.y;
        screenWidth = size.x;
    }

    private void configureSensors(){
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }


    //---------------------------------------------------------------HIGHSCORES

    private void showHighscores() throws SQLException{
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Highscores top 10");

        TableLayout layout = new TableLayout(this);
        TableRow row = new TableRow(this);
        TextView id = new TextView(this);
        TextView name = new TextView(this);
        TextView score = new TextView(this);

        layout.setColumnStretchable(1, true);

        id.setText("#");
        name.setText("Name");
        score.setText("Score");
        id.setGravity(Gravity.LEFT);
        name.setGravity(Gravity.CENTER);
        score.setGravity(Gravity.RIGHT);

        row.addView(id);
        row.addView(name);
        row.addView(score);

        layout.addView(row);

        ArrayList<Score> scores = (ArrayList)dbFacade.getScore();
        int num = 0;
        for(Score s:scores){
            TableRow playerRow = new TableRow(this);
            TextView idRow = new TextView(this);
            TextView nameRow = new TextView(this);
            TextView scoreRow = new TextView(this);

            num++;
            idRow.setText(Integer.toString(num));
            nameRow.setText(s.getName());
            scoreRow.setText(Integer.toString(s.getScore()));

            nameRow.setGravity(Gravity.CENTER);

            playerRow.addView(idRow);
            playerRow.addView(nameRow);
            playerRow.addView(scoreRow);

            layout.addView(playerRow);
        }

        builder.setView(layout);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Close
            }
        });
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void createNewHighscore(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GAME OVER");
        builder.setMessage("Insert name to add yourself to the scoreboard!");

        final EditText editText = new EditText(this);
        editText.setSingleLine();

        builder.setView(editText);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di, int btn) {

            }
        });

        builder.setPositiveButton("Commit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di, int btn) {
                try{
                    if (editText.getText().equals("")) {
                        //if no name enteres -> ask again
                        createNewHighscore();
                    }
                    else{
                        prepareWriter();
                        String name = editText.getText().toString();
                        int score = facade.getScore();
                        dbFacade.write(name, score);
                        showHighscores();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }

    private void checkConnection(){
        cc = new ConnectionControler(getApplicationContext());
        cc.execute();
    }

    private void prepareWriter() throws ClassNotFoundException, SQLException{
        checkConnection();
        if(cc.isConnected()){
            Properties properties = new Properties();

            properties.setProperty("user", "r0428824");
            properties.setProperty("password", "banaCuvar2592");
            properties.setProperty("ssl", "true");
            properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
            OnlineScoreWriter osw = new OnlineScoreWriter("jdbc:postgresql://gegevensbanken.khleuven.be:51415/probeer", properties);
            dbFacade = new DBFacadeImpl(osw);
        }
        else{
            dbFacade = new DBFacadeImpl(new OfflineScoreWriter(getApplicationContext()));
        }
    }


    //---------------------------------------------------------------RUNNABLE

    private Runnable thread = new Runnable() {
        @Override
        public void run() {


            if(!facade.isGameOver() && !facade.isPaused() && facade.isStarted()){
                //TODO insert collision detector between rats and mexican
                processRats();
                processBullets();
                processColissionBetweenBulletAndRat();
                processColissionBetweenMexicanAndRat();

                handler.postDelayed(this, 0);
            }
            else if( facade.isGameOver()){
                stopGame();
                createNewHighscore();
            }
        }
    };

    private Runnable ratTimer = new Runnable() {
        @Override
        public void run() {
            if(!facade.isGameOver() && facade.isStarted() && !facade.isPaused()){
                addRat();

            }
            handler.postDelayed(this, 5000-facade.getScore());
        }
    };
}
