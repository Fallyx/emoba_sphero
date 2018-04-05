package ch.fhnw.edu.emoba.emoba_sphero;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.Touch;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TouchFragment extends Fragment
{
    public TouchFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(
                R.layout.fragment_touch,
                container,
                false); //!!! this is important
        return view;
    }
}
