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
    PointF direction = new PointF(0,0);
    private ScheduledExecutorService exec = null;
    DriveHelper dHelper = new DriveHelper();

    // Screen center x / y and touch x / y
    private float cx = 0;
    private float cy = 0;
    private float tx = 0;
    private float ty = 0;

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
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    Log.d("TOUCH", "Touch event");
                }

                cx = v.getWidth() / 2;
                cy = v.getHeight() / 2;
                tx = event.getX();
                ty = event.getY();
                float distance = 0;

                //double distanceFromCenter = Math.sqrt((cx + x) * (cx + x) + (cy + y) * (cy + y));

               // Log.d("TouchEvent", String.valueOf(distanceFromCenter));

                //distance = (float) Math.sqrt(((cx - x)*(cx - x)) + ((cy - y) * (cy - y)));


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                       // direction.set(x,y);
                        exec = Executors.newSingleThreadScheduledExecutor();

                        Log.d("DOWN", String.valueOf(direction));
                        //Log.d("CENTER", "(" +  String.valueOf(cx) + ", " + String.valueOf(cy) + ")");
                       // Log.d("DISTANCE", String.valueOf(distance));
                        //calculateDrive(cx, cy, x, y);
                       // sendMessage(cx,cy,x,y);

                        startDriveSphero();

                        break;
                    case MotionEvent.ACTION_MOVE:
                       // direction.set(x,y);
                       // calculateDrive(cx, cy, x, y);
                       // Log.d("MOVE", "moveing");

                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        //direction.set(cx,cy);
                       // wrapper.drive(0, 0);
                        stopDriveSphero();
                        break;
                }

                return true;
            }
        });
    }

    private void calculateDrive(float cx, float cy, float x, float y)
    {
        float x0 = 0;
        float y0 = 0;
        float xx = cx - x;
        float yy = cy - y;

        float x90 = 0;
        float y90 = 250;
        float vel = 0;

        float distance = (float) Math.sqrt(((x0 - xx)*(x0 - xx)) + ((y0 - yy) * (y0 - yy)));
        Log.d("DISTANCE", String.valueOf(distance));


        /*
        float headingY = cy - 250;
        Log.d("HeadingY", String.valueOf(headingY));

        float ab = cx * x + headingY * y;
        float a = (float) Math.sqrt(cx * cx + headingY * headingY);
        float b = (float) Math.sqrt(x * x + y * y);

        float cosAngle = (ab /(a * b));
        float angle = (float) Math.toDegrees(Math.acos(cosAngle));
        */

        float ab = x90 * xx + y90 * yy;
        float a = (float) Math.sqrt(x90 * x90 + y90 * y90);
        float b = (float) Math.sqrt(xx * xx + yy * yy);

        float cosAngle = (ab /(a * b));
        float angle = (float) Math.toDegrees(Math.acos(cosAngle));
        if(xx > 0)
        {
            angle = 360 - angle;
        }

        if(distance > 390)
        {
            vel = 1;
        }
        else
        {
            vel = distance / 390;
        }

        wrapper.drive(angle, vel);


        Log.d("XX", String.valueOf(xx));
        Log.d("YY", String.valueOf(yy));

        Log.d("AB", String.valueOf(ab));
        Log.d("A", String.valueOf(a));
        Log.d("B", String.valueOf(b));
        Log.d("COSANGLE", String.valueOf(cosAngle));
        Log.d("ANGLE", String.valueOf(angle));
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
                float xx = cx - tx;
                float yy = cy - ty;
                float vel = 0;

                float distance = (float) Math.sqrt(((x0 - xx)*(x0 - xx)) + ((y0 - yy) * (y0 - yy)));
                float angle = dHelper.calcAngle(0, 250, xx, yy);

                /*
                float x0 = 0;
                float y0 = 0;


                float x90 = 0;
                float y90 = 250;
                float vel = 0;



                float ab = x90 * xx + y90 * yy;
                float a = (float) Math.sqrt(x90 * x90 + y90 * y90);
                float b = (float) Math.sqrt(xx * xx + yy * yy);

                float cosAngle = (ab /(a * b));
                float angle = (float) Math.toDegrees(Math.acos(cosAngle));
                */


                if(xx > 0)
                {
                    angle = 360 - angle;
                }

                if(distance > 390)
                {
                    vel = 1;
                }
                else
                {
                    vel = distance / 390;
                }

                wrapper.drive(angle, vel);
            }
        }, 0, 200, TimeUnit.MILLISECONDS);
    }

    private void stopDriveSphero()
    {
        exec.shutdown();
        wrapper.drive(0,0);
    }
}
