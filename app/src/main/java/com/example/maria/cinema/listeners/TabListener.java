package com.example.maria.cinema.listeners;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;

import com.example.maria.cinema.R;
import com.example.maria.cinema.activities.MainActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Maria on 12/28/2014.
 */
public class TabListener implements ActionBar.TabListener {
    private Fragment fragment;
    private Activity activity;

    // The constructor.
    public TabListener(Fragment fragment, Activity activity) {
        this.fragment = fragment;
        this.activity = activity;


    }


    // When a tab is tapped, the FragmentTransaction replaces
    // the content of our main layout with the specified fragment;
    // that's why we declared an id for the main layout.
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        MainActivity.currentTab = tab.getPosition();
        ft.replace(R.id.container, fragment);
    }

    // When a tab is unselected, we have to hide it from the user's view.
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }

    // Nothing special here. Fragments already did the job.
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
