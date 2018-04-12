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
    SpheroWrapper wrapper;
    SpheroRobotProxy proxy;

    PointF startPoint = new PointF(0, 0);
    PointF lastPoint = new PointF(0,0);

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
                float x = event.getX();
                float y = event.getY();



                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startPoint.set(x,y);
                        Log.d("DOWN", String.valueOf(startPoint));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        lastPoint.set(x,y);
                        Log.d("MOVE", "moveing");
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        Log.d("UP start", String.valueOf(startPoint));
                        Log.d("UP last", String.valueOf(lastPoint));
                        break;
                }

                return true;
            }
        });
    }
}
