package test.bb;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.hardware.SensorEventListener;
import android.view.WindowManager;

import static java.lang.Boolean.TRUE;
import android.graphics.Canvas;

/**
 * Created by Admin on 4/1/2017.
 */

public class SimulationView extends View implements SensorEventListener {

    private Bitmap somethingGreat, leftBall, rightBall;
    private Particle solidBallL = new Particle(), solidBallR = new Particle();
    private Display display;
    private float startX, startY;
    private float maxX, maxY;
    private float curX, curY, curZ;
    private long timeStamp;
    private Sensor acc;
    private SensorManager accMom;

    public SimulationView(Context context) {
        super(context);
        Bitmap left = BitmapFactory.decodeResource(getResources(), R.drawable.myballd);
        leftBall = Bitmap.createScaledBitmap(left, 250, 250, true);
        Bitmap right = BitmapFactory.decodeResource(getResources(), R.drawable.myballd);
        rightBall = Bitmap.createScaledBitmap(right, 250, 250, true);
        BitmapFactory.Options oppa = new BitmapFactory.Options();
        oppa.inDither = TRUE;
        oppa.inPreferredConfig = Bitmap.Config.RGB_565;
        somethingGreat = BitmapFactory.decodeResource(getResources(), R.drawable.somethingnice, oppa);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();


        accMom = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        acc = accMom.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //origin of ball
        startX = w * 0.5f;
        startY = h * 0.5f;
        //screen bounds
        maxX = (w - 250) * 0.5f;
        maxY = (h - 250) * 0.5f;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if( event.sensor.getType() == Sensor.TYPE_ACCELEROMETER ) {
            if( getRotation() == Surface.ROTATION_0){
                curX = event.values[0];
                curY = event.values[1];

            }
            else if ( getRotation() == Surface.ROTATION_90) {
                curX = -event.values[1];
                curY = event.values[0];
            }
        }
        timeStamp = event.timestamp;
        curZ = event.values[2];
    }

    protected void onDraw(Canvas canvas) {
        //draws bitmaps
        super.onDraw(canvas);

        canvas.drawBitmap(somethingGreat, 0, 0, null);

        solidBallL.updatePosition(curX, curY, curZ, timeStamp);
        solidBallR.updatePosition(curX, curY, curZ, timeStamp);
        solidBallR.resolveCollisionWithBounds(maxX, maxY);
        solidBallL.resolveCollisionWithBounds(maxX, maxY);

        canvas.drawBitmap(leftBall, (startX - 250 / 2) + solidBallL.mPosX, (startY - 250 / 2) + solidBallL.mPosY, null);
        canvas.drawBitmap(rightBall, (startX - 250 / 2) + solidBallL.mPosX, (startY - 250 / 2) + solidBallL.mPosY, null);
    }
    public void StarSim(){
        accMom.registerListener(this,acc,SensorManager.SENSOR_DELAY_UI);
    }
    public void StopSim(){accMom.unregisterListener(this);}
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        /* nothing */
    }
}
