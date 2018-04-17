package ch.fhnw.edu.emoba.emoba_sphero;

import android.graphics.PointF;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TouchFragment extends Fragment
{
    SpheroWrapper wrapper;
    private ScheduledExecutorService exec = null;
    DriveHelper dHelper = new DriveHelper();

    // Screen center x / y and touch x / y
    private float cx = 0;
    private float cy = 0;
    private float tx = 0;
    private float ty = 0;
    private float angle = 0;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_touch, container, false);
        touchListener(view);

        return view;
    }

    private void touchListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cx = v.getWidth() / 2;
                cy = v.getHeight() / 2;
                tx = event.getX();
                ty = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        exec = Executors.newSingleThreadScheduledExecutor();
                        startDriveSphero();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        stopDriveSphero();
                        break;
                }

                return true;
            }
        });
    }

    private void startDriveSphero()
    {
        exec.scheduleAtFixedRate(new Runnable()
        {
            @Override
            public void run()
            {
                float x0 = 0;
                float y0 = 0;
                float xx = tx - cx;
                float yy = ty - cy;
                float vel = 0;

                float distance = (float) Math.sqrt(((x0 - xx)*(x0 - xx)) + ((y0 - yy) * (y0 - yy)));
                angle = dHelper.calcAnglePoint(0, -250, xx, yy);

                vel = (float) Math.min(distance / cx, 0.2);

                if( cx > tx)
                {
                    angle = 360 - angle;
                }

                if(angle < 0)
                {
                    angle = 360 + angle;
                }

                wrapper.drive(angle, vel);
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void stopDriveSphero()
    {
        exec.shutdownNow();
        wrapper.drive(angle,0);
    }
}
