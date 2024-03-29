package com.example.sf_sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private SensorManager sensorManager;
    private Sensor sensor;

    private Thread threadSensors = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        assert sensor != null;
        refreshTextView();
    }

    @SuppressLint("SetTextI18n")
    private void refreshTextView() {
        threadSensors = new Thread(() ->{
            while (true) {
                runOnUiThread(() -> {//показать сначала без запуска на главном потоке
                    textView.setText(sensor.getName() + "\n" +
                            sensor.getPower() + "\n" +
                            sensor.getType() + "\n" +
                            sensor.getMinDelay());
                });
                System.err.println("отправили новые данные "+sensor.getPower());
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println("случилось непредвиденное дерьмо!");
                }
            }
        });
        threadSensors.start();
    }

}