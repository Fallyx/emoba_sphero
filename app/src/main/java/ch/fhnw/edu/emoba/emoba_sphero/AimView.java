package ch.fhnw.edu.emoba.emoba_sphero;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class AimView extends View
{
    private Paint mPaintCircle;


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

        float cx, cy;
        float radius = 100f;

        cx = getWidth() / 2;
        cy = getHeight() / 2;

        canvas.drawCircle(cx, cy, radius, mPaintCircle);
    }
}
