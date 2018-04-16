package ch.fhnw.edu.emoba.emoba_sphero;

import android.graphics.Point;

import com.orbotix.common.utilities.math.Vector2D;

public class DriveHelper
{
    /** Calculating the angle
     *
     * For Aim
     * x1, y1 are touch event on touch pressed
     * x2, y2 are touch event on touch released
     *
     * For Touch Drive
     * x1, y1 are 0, 250
     * x2, y2 are the actual touch event coordinates
     *
     * @param x1 coordinate of touch event 1
     * @param y1 coordinate of touch event 1
     * @param x2 coordinate of touch event 2
     * @param y2 coordinate of touch event 2
     * @return
     */

    public float calcAngle(float x1, float y1, float x2, float y2)
    {
        float ab = x1 * x2 + y1 * y2;
        float a = (float) Math.sqrt(x1 * x1 + y1 * y1);
        float b = (float) Math.sqrt(x2 * x2 + y2 * y2);

        float cosAngle = (ab /(a * b));
        float angle = (float) Math.toDegrees(Math.acos(cosAngle));

        return angle;
    }

    public float calcAnglePoint(float x1, float y1, float x2, float y2)
    {
        Vector2D xy1 = new Vector2D(x1, y1);
        Vector2D xy2 = new Vector2D(x2, y2);
        double scalar = (xy1.x * xy2.x) + (xy1.y * xy2.y);
        double magnitude =  xy1.magnitude() * xy2.magnitude();
        double angle = Math.toDegrees(Math.acos(scalar/magnitude));

        return (float)angle;
    }
}
