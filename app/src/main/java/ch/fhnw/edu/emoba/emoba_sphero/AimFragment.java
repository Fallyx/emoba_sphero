package ch.fhnw.edu.emoba.emoba_sphero;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class AimFragment extends Fragment
{
    DriveHelper dHelper = new DriveHelper();

    private float xLast = 0;
    private float yLast = 0;
    private float cx = 0;
    private float cy = 0;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_aim, container, false);
        touchListener(view);

        view.findViewById(R.id.btnCalibrate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCalibrate();
            }
        });

        return view;
    }

    private void touchListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cx = v.getWidth() / 2;
                cy = v.getHeight() / 2;
                float x = event.getX();
                float y = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xLast = x;
                        yLast = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        xLast = x;
                        yLast = y;
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        calibrate();
                        break;
                }

                return true;
            }
        });
    }

    private void calibrate()
    {
        float x1 = 0;
        float y1 = -250;
        float xxLast = xLast - cx;
        float yyLast = yLast - cy;

        float angle = dHelper.calcAnglePoint(x1, y1, xxLast, yyLast);

        if( cx > xLast)
        {
            angle = 360 - angle;
        }

        angle = angle - 90;

        if(angle < 0)
        {
            angle = 360 + angle;
        }

        SpheroWrapper.drive(angle, 0);
    }

    private void onCalibrate()
    {
        SpheroWrapper.setZeroHeading();
    }
}
