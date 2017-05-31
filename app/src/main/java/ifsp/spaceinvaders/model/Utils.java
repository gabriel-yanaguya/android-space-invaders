package ifsp.spaceinvaders.model;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by Chan on 18/05/2017.
 */

public class Utils {

    public int getVelocityFromInclinationDegrees(double degrees){
        int velocity = 0;

        //TODO mais ranges para maior controle;

        if( degrees < 30  ) return 8;
        if( degrees < 60  ) return 3;
        if( degrees < 80  ) return 1;
        if( degrees < 100  ) return 0;
        if( degrees < 120  ) return -1;
        if( degrees < 160  ) return -3;
        if( degrees < 180  ) return -8;

        return 0;
    }

    public Bitmap flip(Bitmap bitmap)
    {
        Matrix m = new Matrix();
        m.preScale(-1, 1);

        Bitmap dst = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);

        return dst;
    }

    public Bitmap scale(Bitmap bitmap, float scale)
    {
        Matrix m = new Matrix();
        m.preScale(scale, scale);

        Bitmap dst = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);

        return dst;
    }
}
