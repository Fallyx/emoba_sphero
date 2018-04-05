package ch.fhnw.edu.emoba.emoba_sphero;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

public class PairingActivity extends AppCompatActivity implements SpheroRobotDiscoveryListener
{
    // SPHERO vars
    boolean onEmulator;
    SpheroRobotProxy proxy;

    // App vars
    TextView btStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        btStatus = findViewById(R.id.btStatus);

        onEmulator = Build.PRODUCT.startsWith("sdk");
        proxy = SpheroRobotFactory.createRobot(onEmulator);
        proxy.setDiscoveryListener(this);
        proxy.startDiscovering(getApplicationContext());


        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                String text = "Started discovering SPHERO device...";
                updateText(text);
            }
        });
    }

    @Override
    public void handleRobotChangedState(SpheroRobotBluetoothNotification spheroRobotBluetoothNotification)
    {
        // handle notifications
        // on "Online" switch to MainActivity

        Log.d("Bluetooth Notification", spheroRobotBluetoothNotification.toString());

        if(spheroRobotBluetoothNotification == SpheroRobotBluetoothNotification.Online)
        {
            proxy.stopDiscovering();

            // Explicit intent
            Intent mainIntent = new Intent(this, MainActivity.class);

            startActivity(mainIntent);
            finish();
        }
        else
        {
            Log.d("Bluetooth Notification", "Connection Failed...");

            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    String text = "Connection failed...";
                    updateText(text);
                }
            });
        }
    }

    private void updateText(String text)
    {
        btStatus.setText(text);
    }
}
