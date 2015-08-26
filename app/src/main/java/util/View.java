package util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import model.Bullet;
import model.Mexican;
import model.Rat;

/**
 * Created by Bram on 8/12/2015.
 */
public class View extends SurfaceView {

    private Facade facade;
    private Paint paint, rectPaint, blockPaint; //TODO mogelijk meer paint nodig
    private boolean configured = false;
    //block contact of bar with rectangle

    public View(Context context) {
        super(context);
        paint = new Paint();
        rectPaint = new Paint();
        rectPaint.setColor(Color.BLACK);
        rectPaint.setAlpha(200);
    }

    public View(Context context, AttributeSet attrs){
        super(context, attrs);
        paint = new Paint();
        paint = new Paint();
        rectPaint = new Paint();
        rectPaint.setColor(Color.BLACK);
        rectPaint.setAlpha(200);
    }

    public View(Context context, AttributeSet attrs, int number){
        super(context, attrs, number);
        paint = new Paint();
        paint = new Paint();
        rectPaint = new Paint();
        rectPaint.setColor(Color.BLACK);
        rectPaint.setAlpha(200);
    }

    //TODO configure

    public void configure(Canvas canvas){
        //TODO resize mexican and rats
        //facade.getMexican().resize(canvas);



        configured = true;


    }

    public void resizeRats(Canvas canvas) {
        CopyOnWriteArrayList<Rat> rats = facade.getRats();

        if (!rats.isEmpty()){
            for (Rat rat : rats) {
                rat.resize(canvas);
            }
        }
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if(facade != null && !configured) {
            configure(canvas);
        }

        canvas.drawColor(Color.TRANSPARENT);
        if(getFacade() != null && configured){
            Mexican mexican = facade.getMexican();
            mexican.drawGameObject(canvas);

            drawBullets(canvas);
            //resizeRats(canvas);
            drawRats(canvas);


            //rectpaint.setStyle(Paint.Style.FILL);

            Paint textPaint = new Paint();
            textPaint.setColor(Color.GREEN);
            textPaint.setTextSize(50);
            textPaint.setTextAlign(Paint.Align.CENTER);

            //canvas.drawRect((float) mexican.getLeft(), (float) mexican.getTop(), (float) mexican.getRight(), (float) mexican.getBottom(), rectpaint);
           // rectpaint.setColor(Color.RED);
            //canvas.drawRect((float) facade.getRats().get(0).getLeft(), (float) facade.getRats().get(0).getTop(), (float) facade.getRats().get(0).getRight(), (float) facade.getRats().get(0).getBottom(), rectpaint);



            //drawScore(canvas);


            if(!facade.isStarted() && !facade.isGameOver()) {

                //canvas.drawText("START", canvas.getWidth() / 2, (canvas.getHeight() - paint.ascent()) / 2 , paint);
                canvas.drawText("TAP SCREEN TO START",canvas.getWidth()/2, canvas.getHeight()/2, textPaint);
            }
        }

        invalidate();
    }

    public void drawRats(Canvas canvas){
        CopyOnWriteArrayList<Rat> rats = getFacade().getRats();
        for(Rat rat:rats){
            //TODO resize
            rat.drawGameObject(canvas);
           /* float left = (float) rat.getLeft();
            float top = (float) rat.getTop();
            float right = (float) rat.getRight();
            float bottom = (float) rat.getBottom();

            canvas.drawRect(left, top, right, bottom, rectPaint);*/


        }
    }

    public void drawBullets(Canvas canvas){
        ConcurrentLinkedQueue<Bullet> bullets = facade.getMexican().getBullets();

        for (Bullet bullet : bullets) {
            bullet.drawGameObject(canvas);

        }

        /*switch (facade.getMexican().getDirection()){
            case "W":
                for(Bullet bullet:bullets){
                    bullet.rotate(bullet.getDirection());
                    bullet.drawGameObject(canvas);
                }
                break;
            case "N":
                for(Bullet bullet:bullets){
                    bullet.rotate(bullet.getDirection());
                    bullet.drawGameObject(canvas);
                }
                break;
            case "E":
                for(Bullet bullet:bullets){
                    bullet.rotate(bullet.getDirection());
                    bullet.drawGameObject(canvas);
                }
                break;
            default:
                for(Bullet bullet:bullets){
                    bullet.rotate(bullet.getDirection());
                    bullet.drawGameObject(canvas);
                }
                break;
        }*/


    }

    public void drawLives(Canvas canvas){
        canvas.drawText("Lives: " + facade.getMexican().getLives(),5,25, paint);
    }

    public void drawScore(Canvas canvas){
        canvas.drawText("Score: " + facade.getScore(),20,25, paint);
    }



    public Facade getFacade() {
        return facade;
    }

    public void setFacade(Facade facade) {
        this.facade = facade;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
