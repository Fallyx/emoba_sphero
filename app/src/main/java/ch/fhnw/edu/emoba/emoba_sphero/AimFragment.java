package ch.fhnw.edu.emoba.emoba_sphero;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

public class AimFragment extends Fragment
{
    SpheroWrapper wrapper;
    SpheroRobotProxy proxy;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_aim, container, false);

        return view;
    }
}
