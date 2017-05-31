package ifsp.spaceinvaders.model;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.Random;

/**
 * Created by Chan on 04/05/2017.
 */

public class Screen {

    private DisplayMetrics metrics;

    public Screen(Context context){

        WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();

        this.metrics = new DisplayMetrics();
        display.getMetrics(this.metrics);
    }


    public int sortXPos(int offset){

        Random r = new Random();
        return r.nextInt(this.metrics.widthPixels - offset) + offset;
    }

    public int sortYPos(int offset){

        Random r = new Random();
        return r.nextInt(this.metrics.heightPixels - offset) + offset;
    }

    public int getWidth(){

        return this.metrics.widthPixels;
    }

    public int getHeight(){

        return this.metrics.heightPixels;
    }
}
