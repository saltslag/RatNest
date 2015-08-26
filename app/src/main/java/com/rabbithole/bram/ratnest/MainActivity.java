package com.rabbithole.bram.ratnest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import db.DBFacade;
import db.DBFacadeImpl;
import db.OfflineScoreWriter;
import db.OnlineScoreWriter;
import model.Score;
import util.ConnectionControler;


public class MainActivity extends Activity {

    private DBFacade dbFacade;
    private ConnectionControler cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*removes titlebar and android top-menubar*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        //intent.putExtra(MUSIC)

        startActivity(intent);
        MainActivity.this.finish();
    }

    //--------------------------------------------------HIGHSCORES

    public void showScore(View view)throws SQLException, ClassNotFoundException {
        prepareWriter();
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

    //---------------------------------------------------------------RULES

    public void showRules(View view){
        Intent intent = new Intent(this, RulesActivity.class);

        startActivity(intent);
        MainActivity.this.finish();
    }

    public void exitGame(View view){
        finish();
        System.exit(0);
    }
}
