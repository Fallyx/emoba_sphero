package ch.fhnw.edu.emoba.emoba_sphero;

import android.content.Context;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class SensorFragment extends Fragment implements SensorEventListener

{
    SpheroWrapper wrapper;
    private SensorManager sensorManager;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[1];
        float y = -event.values[0];
        double magnitude = Math.sqrt(x*x+y*y);

        double rotation = Math.acos(y/magnitude) * 180 / Math.PI;
        if(x < 0){
            rotation = 360 - rotation;
        }

        double speed = Math.min(magnitude * 3, 1);
        wrapper.drive((float)rotation, (float)speed);
        //Log.i("sensor", Float.toString(y));



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
