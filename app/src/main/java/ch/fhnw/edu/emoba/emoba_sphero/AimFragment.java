package ch.fhnw.edu.emoba.emoba_sphero;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AimFragment extends Fragment implements View.OnClickListener
{
    public AimFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(
                R.layout.fragment_aim,
                container,
                false); //!!! this is important
        Button b = view.findViewById(R.id.button3);
        b.setOnClickListener(this);

        return view;
    }

    public void calibrateLeft(View view)
    {
        Log.d("CALIBRATE LEFT", "ONCLICK");
        // ((YourActivityClassName)getActivity()).yourPublicMethod();
        ((MainActivity)getActivity()).buttonTest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button3:
                calibrateLeft(v);
            break;
        }
    }
}
