package ch.fhnw.edu.emoba.emoba_sphero;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener;

public class PairingActivity extends AppCompatActivity implements SpheroRobotDiscoveryListener
{
    // App vars
    TextView btStatus;
    private String text = "";

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
        text = "Started discovering SPHERO device...";

        btStatus = findViewById(R.id.btStatus);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if ((checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) && !SpheroWrapper.onEmulator)
        {
            text =  "Location permission was not granted for this app.";
        }
        else if ((bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) && !SpheroWrapper.onEmulator)
        {
            text = "Bluetooth isn't enabled.";
        }
        else
        {
            SpheroWrapper.setupProxy();
            SpheroWrapper.setListener(this, getApplicationContext());
        }

        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                updateText(text);
            }
        });
    }

    @Override
    public void handleRobotChangedState(SpheroRobotBluetoothNotification spheroRobotBluetoothNotification)
    {
        if(spheroRobotBluetoothNotification == SpheroRobotBluetoothNotification.Online)
        {
            SpheroWrapper.stopListener();
            SpheroWrapper.setLED();

            Intent mainIntent = new Intent(this, MainActivity.class);

            startActivity(mainIntent);
            finish();
        }
        else if(spheroRobotBluetoothNotification == SpheroRobotBluetoothNotification.Connecting || spheroRobotBluetoothNotification == SpheroRobotBluetoothNotification.Connected)
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    text = "Connecting...";
                    updateText(text);
                }
            });
        }
        else
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    text = "Connection failed...";
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
