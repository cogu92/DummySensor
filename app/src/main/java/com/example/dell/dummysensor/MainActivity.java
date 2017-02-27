package com.example.dell.dummysensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSerivicemanager;
private Sensor mproximity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSerivicemanager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = this.mSerivicemanager.getSensorList(Sensor.TYPE_ALL);
        printSensorlist(sensorList);

      mproximity=   mSerivicemanager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

    }

    private void printSensorlist(List<Sensor> sensorList) {

        StringBuilder sensorAvailableString=new StringBuilder();

    for(Sensor sensor :sensorList )
    {
        sensorAvailableString.append(sensor.getName()).append("\n");

    }
        Toast.makeText(this, sensorAvailableString.toString(), Toast.LENGTH_SHORT).show();
        Log.d("",sensorAvailableString.toString());
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Long eventTime=event.timestamp;
        Toast.makeText(this, String.valueOf(eventTime), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mSerivicemanager.registerListener(this,mproximity,mSerivicemanager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mSerivicemanager!=null)
        {
            mSerivicemanager.unregisterListener(this);
        }
    }
}
