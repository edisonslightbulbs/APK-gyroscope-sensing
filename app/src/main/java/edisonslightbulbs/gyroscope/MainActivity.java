package edisonslightbulbs.gyroscope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView m_gyroscopeX;
    TextView m_gyroscopeY;
    TextView m_gyroscopeZ;

    private SensorManager m_sensorManager;
    private Sensor m_gyroscope;

    // round up to n number of decimal places
    DecimalFormat df = new DecimalFormat("#.######");

    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        df.setRoundingMode(RoundingMode.CEILING);

        m_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (m_sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            m_gyroscope = m_sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            Utils.toast(this, "gyroscope detected");
        } else{
            Utils.toast(this, "sorry, gyroscope not detected");
            Log.e(TAG, "-- gyroscope sensor not found!");
        }

        m_gyroscopeX = findViewById(R.id.accXTextView);
        m_gyroscopeY = findViewById(R.id.accYTextView);
        m_gyroscopeZ = findViewById(R.id.accZTextView);

        m_gyroscopeX.setText("0.0");
        m_gyroscopeY.setText("0.0");
        m_gyroscopeZ.setText("0.0");
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        String xValue = df.format(event.values[0]);
        String yValue = df.format(event.values[1]);
        String zValue = df.format(event.values[2]);

        m_gyroscopeX.setText(xValue);
        m_gyroscopeY.setText(yValue);
        m_gyroscopeZ.setText(zValue);
    }

    @Override
    protected void onResume() {
        super.onResume();
        m_sensorManager.registerListener(this, m_gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        m_sensorManager.unregisterListener(this);
    }
}