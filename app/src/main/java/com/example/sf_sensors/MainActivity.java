package com.example.sf_sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView x_tv, y_tv, z_tv;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x_tv = findViewById(R.id.x_tv);
        y_tv = findViewById(R.id.y_tv);
        z_tv = findViewById(R.id.z_tv);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){} else {
            System.err.println("Датчик ускорения недоступен");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float changedValueX = event.values[0];
        float changedValueY = event.values[1];
        float changedValueZ = event.values[2];
        x_tv.setText(String.valueOf(changedValueX));
        y_tv.setText(String.valueOf(changedValueY));
        z_tv.setText(String.valueOf(changedValueZ));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        System.err.println("onResume()");
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.err.println("onPause()");
        sensorManager.unregisterListener(this);
    }
}