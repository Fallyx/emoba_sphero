package ch.fhnw.edu.emoba.emoba_sphero;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SensorFragment extends Fragment implements SensorEventListener
{
    SpheroWrapper wrapper;
    private SensorManager sensorManager;
    private boolean sensorOn = true;
    private double lastRot = 0;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        view.findViewById(R.id.sensorToggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSensor(!sensorOn);
            }
        });
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void onPause(){
        super.onPause();
        setSensor(false);
        sensorManager = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

            float x = event.values[1];
            float y = -event.values[0];
            double magnitude = Math.sqrt(x * x + y * y);

            double rotation = Math.acos(y / magnitude) * 180 / Math.PI;
            if (x < 0)
            {
                rotation = 360 - rotation;
            }

            double speed = Math.min(magnitude * 3, 0.2);
            lastRot = rotation;
            wrapper.drive((float) rotation, (float) speed);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void setSensor(boolean state){
        if(state){
            sensorManager.unregisterListener(this);
            ((TextView)getActivity().findViewById(R.id.textViewSensor)).setTextColor(0xffff0000);
            wrapper.drive((float)lastRot, 0f);
        }else{
            sensorManager.registerListener(this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR),
                    SensorManager.SENSOR_DELAY_NORMAL);
            ((TextView)getActivity().findViewById(R.id.textViewSensor)).setTextColor(0xff00ff00);
        }
        sensorOn = state;
    }
}
