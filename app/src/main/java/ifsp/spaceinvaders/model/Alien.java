package ifsp.spaceinvaders.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import ifsp.spaceinvaders.R;

/**
 * Created by Chan on 22/05/2017.
 */

public class Alien {

    private int xPos;
    private int yPos;
    private int xVel;

    private Bitmap bitmap;

    private Screen screen;

    public Alien(Context context) {
        this.xVel = 2;

        this.screen = new Screen(context);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.disco_1);
        this.bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), screen.getHeight(), false);
        this.bitmap = new Utils().scale(bitmap, 0.15f);

        this.xPos = (screen.getWidth() / 2) + (this.bitmap.getWidth() / 2);
        this.yPos = (screen.getHeight() - this.bitmap.getHeight()) - 20;
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(this.bitmap, xPos, yPos, null);
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

    public int getXVel() {
        return xVel;
    }

    public void setXVel(int xVel) {
        this.xVel = xVel;
    }
}
