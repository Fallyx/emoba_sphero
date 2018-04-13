package ch.fhnw.edu.emoba.emoba_sphero;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

public class AimFragment extends Fragment
{
    DriveHelper dHelper = new DriveHelper();

    private float xStart = 0;
    private float yStart = 0;
    private float cx = 0;
    private float cy = 0;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_aim, container, false);
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
                float x = event.getX();
                float y = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xStart = x;
                        yStart = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        calibrate(x, y);
                        break;
                }

                return true;
            }
        });
    }

    private void calibrate(float xEnd, float yEnd)
    {
        float xxStart = cx - xStart;
        float yyStart = cy - yStart;
        float xxEnd = cx - xEnd;
        float yyEnd = cx - yEnd;

        float angle = dHelper.calcAngle(xxStart, yyStart, xxEnd, yyEnd);
        SpheroWrapper.drive(angle, 0);

    }
}
