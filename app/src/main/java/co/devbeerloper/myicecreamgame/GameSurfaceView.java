package co.devbeerloper.myicecreamgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Runnable {

    private boolean isPlaying;
    private personaje personaje;
    private Paint paint;
    private Paint paintStart;
    private Canvas canvas;
    private SurfaceHolder holder;
    private Thread gameplayThread = null;
    private  int times=0;
    private asteroide[] asteroides = new asteroide[5];
    private nave_enemiga[] naves_enemigas = new nave_enemiga[5];
    boolean active=false;
    int screenWith=0;
    int level=1;

    /**
     * Contructor
     * @param context
     */
    public GameSurfaceView(Context context, float screenWith, float screenHeight) {
        super(context);
        this.screenWith=(int)screenWith;
        personaje = new personaje(context, screenWith, screenHeight);
        paint = new Paint();
        paintStart= new Paint();
        paintStart.setTextSize(100);
        holder = getHolder();
        isPlaying = true;
        for (int i=0;i<5;i=i+1){
         asteroides[i]= new asteroide(context, screenWith, screenHeight);
        }
        for (int i=0;i<5;i=i+1){
            naves_enemigas[i]= new nave_enemiga(context, screenWith, screenHeight);
        }
    }

    /**
     * Method implemented from runnable interface
     */
    @Override
    public void run() {
        while (isPlaying) {
            updateInfo();
            paintFrame();
        }
    }
    private void updateInfo() {
        if(active){
            personaje.updateInfo();
            for(int i=0; i < times/100 ;i=i+1) {
                if(asteroides[i].updateInfo(personaje.getPositionX(),personaje.getPositionY(),level)==1||naves_enemigas[i].updateInfo(personaje.getPositionX(),personaje.getPositionY(),level)==1)
                {
                    active=false;
                }
            }
        }
    }

    private void paintFrame() {
        if (holder.getSurface().isValid()){
            if(times<500){
                times=times+1;
            }
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.CYAN);
            if(!active){canvas.drawText("You lose", 650,400,paint);}
            paint.setTextSize(40);
            canvas.drawBitmap(personaje.getImagenPersonaje(),personaje.getPositionX(),personaje.getPositionY(),paint);
            for(int i=0;i<times/100;i=i+1) {
                canvas.drawBitmap(asteroides[i].getImagenAsteroide(), asteroides[i].getPositionX(), asteroides[i].getPositionY(),paint);
            }
            for(int i=0;i<times/100;i=i+1) {
                canvas.drawBitmap(naves_enemigas[i].getImagenNave(), naves_enemigas[i].getPositionX(), naves_enemigas[i].getPositionY(),paint);
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            gameplayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {

        isPlaying = true;
        gameplayThread = new Thread(this);
        gameplayThread.start();
    }

    /**
     * Detect the action of the touch event
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.active=true;
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                personaje.setJumping(false);
                break;
            case MotionEvent.ACTION_DOWN:
                personaje.setJumping(true);
                break;
        }
        return true;
    }


}