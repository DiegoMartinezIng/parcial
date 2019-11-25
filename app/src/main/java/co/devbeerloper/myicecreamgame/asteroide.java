package co.devbeerloper.myicecreamgame;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import  java.util.*;
public class asteroide {

    public static final float INIT_X =100;
    public static final float INIT_Y =100;
    public static final int SPRITE_SIZE_WIDTH =120;
    public static final int SPRITE_SIZE_HEIGTH=100;
    private float maxY;
    private float maxX;
    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap imagenAsteroide;
    private int orientation=-1;
    public asteroide(Context context, float screenWidth, float screenHeigth){

        speed = 1;
        positionX = screenWidth-200;
        positionY = (screenWidth/2)-200;
        //Getting bitmap from resource
        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.asteroide);
        imagenAsteroide  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (imagenAsteroide.getWidth()/2);
        this.maxY = screenHeigth - imagenAsteroide.getHeight();

        Random random = new Random();
        if(random.nextBoolean()){
            orientation=1;
        }

    }

    public asteroide(Context context, float initialX, float initialY, float screenWidth, float screenHeigth){

        speed = 1;
        positionX = initialX;
        positionY = initialY;

        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.asteroide);
        imagenAsteroide = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (imagenAsteroide.getWidth()/2);
        this.maxY = screenHeigth - imagenAsteroide.getHeight();

    }

    public static float getInitX() {
        return INIT_X;
    }

    public static float getInitY() {
        return INIT_Y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Bitmap getImagenAsteroide() {
        return imagenAsteroide;
    }

    public void setImagenAsteroide(Bitmap spritekid) {
        this.imagenAsteroide = spritekid;
    }


    /**
     * Control the position and behaviour of the icecream car
     */
    public int updateInfo (float a, float b, int level) {
           this.positionX-=10;
            if(level>4 ){
                this.positionY+=(orientation* (level));
            }
            if(positionY<0){
                orientation=1;
            }
            if(positionY>maxY){
                orientation=-1;
            }


            if(a+60>this.positionX && a-60<this.positionX){
                if(b+60>this.positionY && b-60<this.positionY ){
                    this.positionX= this.maxX;
                    this.positionY =  (float)Math.random()*400;
                    return 1;

                }
            }
            if(this.positionX<0){
                this.positionX= this.maxX;
                this.positionY =  (float)Math.random()*400;
                return-1;
            }

        return 0;
    }
}
