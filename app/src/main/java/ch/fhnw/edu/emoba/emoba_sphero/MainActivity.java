package ch.fhnw.edu.emoba.emoba_sphero;


import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

public class MainActivity extends AppCompatActivity
{
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

   // private SpheroRobotProxy proxy;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

       // proxy = SpheroRobotFactory.getActualRobotProxy();
    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AimFragment(), "Aim");
        adapter.addFragment(new TouchFragment(), "Touch");
        adapter.addFragment(new SensorFragment(), "Sensor");

        viewPager.setAdapter(adapter);
    }
}
