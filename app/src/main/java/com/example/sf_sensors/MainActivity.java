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
    private TextView step_counter_tv, step_detector_tv;
    private SensorManager sensorManager;
    private Sensor stepCounter;
    private int stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        step_counter_tv = findViewById(R.id.step_counter_tv);
        step_detector_tv = findViewById(R.id.step_detector_tv);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        } else {
            System.err.println("Датчик ускорения недоступен");
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {

        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == stepCounter) {
            stepCount = (int) event.values[0];
            step_counter_tv.setText("step counter = "+stepCount);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        System.err.println("onResume()");
        sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.err.println("onPause()");
        sensorManager.unregisterListener(this);
    }
}