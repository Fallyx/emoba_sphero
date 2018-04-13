package ch.fhnw.edu.emoba.emoba_sphero;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class AimView extends View
{
    private Paint mPaintCircle;
    private PointF point;
    private float cx = getWidth() / 2;
    private float cy = getWidth() / 2;

    public AimView(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setColor(Color.parseColor("#00ccff"));
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        float radius = 100f;

        canvas.drawCircle(cx, cy, radius, mPaintCircle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();

        double distanceFromCenter = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
        //Figure out which ring it's in.

        Log.d("TouchEvent", String.valueOf(distanceFromCenter));

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                point = new PointF(x, y);
                Log.d("DOWN", String.valueOf(point));
                break;
            case MotionEvent.ACTION_MOVE:
                point.set(x, y);
                Log.d("MOVE", String.valueOf(point));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                point = null;
                break;
        }
        return true;
    }

    private void calibrate()
    {

    }
}
