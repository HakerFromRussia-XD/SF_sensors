package com.example.sf_sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textView;
    private SensorManager sensorManager;
    private int accuracy = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float changedValue = event.values[0];
        textView.setText(String.valueOf(changedValue));
        System.err.println("отправили новые данные "+event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        System.err.println("accuracy changed = "+accuracy);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.err.println("onResume()");
        accuracy += 1;
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), accuracy);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.err.println("onPause()");
        sensorManager.unregisterListener(this);
    }
}