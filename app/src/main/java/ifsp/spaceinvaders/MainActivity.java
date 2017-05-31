package ifsp.spaceinvaders;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import ifsp.spaceinvaders.model.Utils;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Game game;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout container = (FrameLayout) findViewById(R.id.container);
        game = new Game(this);
        container.addView(this.game);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
        * ACCELEROMETER
        * */
    @Override
    public void onSensorChanged(SensorEvent event) {
        double aX = 0, aY = 0, angle;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)

            aX = event.values[0];
            aY= event.values[1];
            //aZ= event.values[2];
            angle = Math.atan2(aX, aY)/(Math.PI/180);

            this.game.setHeroVelocity(new Utils().getVelocityFromInclinationDegrees(angle));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause(){

        super.onPause();
        senSensorManager.unregisterListener(this);
        this.game.stop();
    }

    @Override
    protected void onResume(){

        super.onResume();

        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        this.game.start();
        new Thread(this.game).start();
    }
}
