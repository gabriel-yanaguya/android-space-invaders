package ifsp.spaceinvaders.model;

import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;

import java.util.ArrayList;

/**
 * Created by Chan on 22/05/2017.
 */

public class AlienSquad {

    private final int X_SPACE_BETWEEN_ALIENS = 40;
    private final int Y_SPACE_BETWEEN_ALIENS = 40;
    private final int QUANTITY_ALIENS = 20;
    private int PADDING;

    private ArrayList<Alien> aliens;
    private Screen screen;

    private int xVel;

    public AlienSquad(Context context){

        this.xVel = 5;

        this.screen = new Screen(context);

        PADDING = (int) screen.getWidth() / 6;

        this.aliens = new ArrayList<>();
        int xPos = PADDING;
        int yPos = Y_SPACE_BETWEEN_ALIENS;

        for(int c = 0; c<= QUANTITY_ALIENS; c++){

            Alien alien = new Alien(context);
            alien.setXPos(xPos);
            alien.setYPos(yPos);
            xPos += X_SPACE_BETWEEN_ALIENS + alien.getWidth();

            if(xPos + (X_SPACE_BETWEEN_ALIENS + PADDING ) + alien.getWidth() > screen.getWidth()) {
                xPos = PADDING;
                yPos += Y_SPACE_BETWEEN_ALIENS + alien.getHeight();
            }

            this.aliens.add(alien);
        }
    }

    public void draw(Canvas canvas){

        for (Alien alien:this.aliens) {
            alien.draw(canvas);
        }
    }

    public void move(){

        boolean changeDirection = false;

        for (Alien alien : this.aliens) {
            //if(alien.getYPos() <= Y_SPACE_BETWEEN_ALIENS) {
                if ((alien.getXPos() + xVel + alien.getWidth()) > screen.getWidth()) {
                    changeDirection = true;
                }

                if ((alien.getXPos() + xVel) < 0) {
                    changeDirection = true;
                }
            //}
        }

        if(changeDirection) xVel *= -1;

        for (Alien alien : this.aliens) {
            alien.setXPos( alien.getXPos() + xVel );
        }
    }

    public ArrayList<Alien> getAliensBottomLine(){

        ArrayList<Alien> bottomLine = new ArrayList<>();
        int bottomYPos = 0;

        for (Alien alien : aliens) {
            if(alien.getYPos() > bottomYPos) bottomYPos = alien.getYPos();
        }

        for (Alien alien : aliens) {
            if(alien.getYPos() >= bottomYPos ) bottomLine.add(alien);
        }

        return bottomLine;
    }
    /*
    * Getters e Setters
    * */

    public ArrayList<Alien> getAliens() {
        return aliens;
    }

    public void setAliens(ArrayList<Alien> aliens) {
        this.aliens = aliens;
    }

    public ArrayList<Alien> getConcurrentAliens(){
        return new ArrayList(this.aliens);
    }
}
