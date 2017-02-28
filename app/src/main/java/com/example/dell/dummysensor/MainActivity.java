package com.example.dell.dummysensor;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSerivicemanager;
    private Sensor mproximity;
    private Sensor maccelerometer;
    private RelativeLayout mMainRelativeLayout;
    private   long oldtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSerivicemanager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = this.mSerivicemanager.getSensorList(Sensor.TYPE_ALL);
        printSensorlist(sensorList);
        mMainRelativeLayout=(RelativeLayout) findViewById(R.id.activity_main);

      mproximity=   mSerivicemanager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        maccelerometer=   mSerivicemanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Log.i("",this.maccelerometer==null ? "null":"ok accelerometer");
        Log.i("","max"+this.maccelerometer.getMaximumRange());
        oldtime=System.currentTimeMillis();
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
        if(event.sensor.getType()== Sensor.TYPE_PROXIMITY )
        {
       // Long eventTime=event.timestamp;
            Log.e("",String.valueOf(event.timestamp));
        Toast.makeText(this, String.valueOf(event.timestamp), Toast.LENGTH_SHORT).show();
        }else if(event.sensor.getType()== Sensor.TYPE_ACCELEROMETER )
        {
            turnScreenColor(event);
            //Log.e("", String.valueOf(event.values[0])+String.valueOf(event.values[1])+String.valueOf(event.values[2]));
          //  Toast.makeText(this, String.valueOf(event.values[0])+String.valueOf(event.values[1])+String.valueOf(event.values[2]), Toast.LENGTH_SHORT).show();

        }
    }

    private void turnScreenColor(SensorEvent event) {
        float xacc=event.values[0];
        float yacc=event.values[1];
        float zacc=event.values[2];
        double magnitude = (Math.pow(xacc,2)+ Math.pow(yacc,2)+Math.pow(zacc,2))/(Math.pow(SensorManager.GRAVITY_EARTH,2));
     //   double magnitude = (xacc*xacc+ yacc*yacc+zacc*zacc)/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH);
     long currenttime=event.timestamp;

        if(magnitude>5.0 && (currenttime-oldtime)>1000000000)
        {
            Log.e("","SHUFUL");
            oldtime=currenttime;

            int currentcolor =((ColorDrawable)mMainRelativeLayout.getBackground()).getColor();
            if(currentcolor==ContextCompat.getColor(this,R.color.black))
             mMainRelativeLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.blue));
            else
               mMainRelativeLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.black));

        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mproximity!=null)
        this.mSerivicemanager.registerListener(this,mproximity,mSerivicemanager.SENSOR_DELAY_NORMAL);

        if(maccelerometer!=null)
            this.mSerivicemanager.registerListener(this,maccelerometer,mSerivicemanager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mproximity!=null)
        {
            mSerivicemanager.unregisterListener(this,mproximity);
        }

        if(maccelerometer!=null)
        {
            mSerivicemanager.unregisterListener(this,maccelerometer);
        }
    }
}
