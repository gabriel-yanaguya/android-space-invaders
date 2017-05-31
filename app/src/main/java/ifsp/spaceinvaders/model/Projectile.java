package ifsp.spaceinvaders.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import ifsp.spaceinvaders.R;

/**
 * Created by Chan on 24/05/2017.
 */

public class Projectile {

    private int xPos;
    private int yPos;
    private int yVel;
    private boolean isAlien;

    private Bitmap bitmap;

    private Screen screen;

    public Projectile(Context context, int xPos, int yPos, boolean isAlien) {
        this.yVel = 10;
        this.xPos = xPos;
        this.yPos = yPos;

        this.screen = new Screen(context);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser);

        if(isAlien){
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_alien);
            this.yVel *= -1;
        }

        this.bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), screen.getHeight(), false);
        this.bitmap = new Utils().scale(bitmap, 0.15f);



        this.isAlien = isAlien;
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(this.bitmap, xPos, yPos, null);
    }

    public void move(){

        this.yPos -= this.yVel;
    }

    /*
    * Getters e Setters
    * */

    public int getHeight() {
        return bitmap.getHeight();
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public boolean isAlien() {return isAlien;}

    public void setIsAlien(boolean isAlien) {this.isAlien = isAlien;}
}
