package test.bb;

import android.app.Activity;
import android.content.Context;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;


public class MainActivity extends Activity {
    protected PowerManager.WakeLock pPower;
    SimulationView Sim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        pPower = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,"My Tag");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Sim = new SimulationView(this);
        setContentView(Sim);
    }
    @Override
    protected void onResume(){ Sim.StarSim(); pPower.acquire(); super.onResume();}
    @Override
    protected void onPause(){Sim.StopSim(); pPower.release();super.onPause();}
    @Override
    protected void onDestroy(){super.onDestroy(); pPower.release();
    }
}
