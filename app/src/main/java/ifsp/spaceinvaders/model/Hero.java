package ifsp.spaceinvaders.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.SoundPool;

import ifsp.spaceinvaders.R;

/**
 * Created by Chan on 22/05/2017.
 */

public class Hero {

    private int height;
    private int width;

    private int xPos;
    private int yPos;
    private int xVel;

    private Bitmap bitmap;

    private Screen screen;

    public Hero(Context context) {

        this.height = 50;
        this.width = 150;

        this.xVel = 0;

        this.screen = new Screen(context);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero_1);
        this.bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), screen.getHeight(), false);
        this.bitmap = new Utils().scale(bitmap, 0.15f);

        this.xPos = (screen.getWidth() / 2) + (this.bitmap.getWidth() / 2);
        this.yPos = (screen.getHeight() - this.bitmap.getHeight()) - 20;

    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(this.bitmap, xPos, yPos, null);
    }

    public void move(){

        if((this.xVel > 0 && (this.xPos + this.getWidth() + this.xVel) < screen.getWidth())
            || this.xVel < 0 && this.xPos + xVel > 0)

        this.xPos += this.getXVel();
    }

    public boolean strike(Projectile projectile){

        if(projectile.getYPos() > this.getYPos() && projectile.getYPos() < (this.getYPos() + this.bitmap.getHeight())){
            if(projectile.getXPos() > this.getXPos() && projectile.getXPos() < (this.getXPos() + this.bitmap.getWidth())){
                return true;
            }
        }

        return false;
    }

    /*
    * Getters e Setters
    * */

    public int getHeight() {
        return this.bitmap.getHeight();
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.bitmap.getWidth();
    }

    public void setWidth(int width) {
        this.width = width;
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

    public int getXVel() {
        return xVel;
    }

    public void setXVel(int xVel) {
        this.xVel = xVel;
    }
}
