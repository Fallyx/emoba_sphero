package ch.fhnw.edu.emoba.emoba_sphero;

import android.content.Context;
import android.os.Build;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

public final class SpheroWrapper
{
    private static SpheroRobotProxy proxy;

    public static void setupProxy()
    {
        boolean onEmulator = Build.PRODUCT.startsWith("sdk");
        proxy = SpheroRobotFactory.createRobot(onEmulator);
    }

    public static void setListener(SpheroRobotDiscoveryListener listener, Context context)
    {
        proxy.setDiscoveryListener(listener);
        proxy.startDiscovering(context);
    }

    public static void stopListener()
    {
        proxy.stopDiscovering();
    }

    public static void drive(float heading, float vel)
    {
        proxy.drive(heading, vel);
    }

    public static void setZeroHeading()
    {
        proxy.setZeroHeading();
    }

    public static void disconnect()
    {
        proxy.disconnect();
    }
}
