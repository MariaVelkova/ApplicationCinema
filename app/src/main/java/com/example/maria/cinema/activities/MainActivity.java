package com.example.maria.cinema.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.example.maria.cinema.R;
import com.example.maria.cinema.common.PlaceholderFragment;
import com.example.maria.cinema.fragments.FragmentLoginTab;
import com.example.maria.cinema.fragments.FragmentUserInfo;
import com.example.maria.cinema.listeners.TabListener;

import java.util.ArrayList;


public class MainActivity extends Activity {
    public static final int loginTabPosition = 0;
    public static final int registerTabPosition = 1;
    ActionBar.Tab loginTab, registerTab;
    Fragment loginFragment = new FragmentLoginTab();
    Fragment registerFragment = new FragmentUserInfo();
    public static int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            PlaceholderFragment placeholderFragment = new PlaceholderFragment();
            placeholderFragment.setLayoutId(R.layout.activity_main);
            FragmentManager fragmentManager =  getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container, placeholderFragment)
                    .commit();
        }

        // Asking for the default ActionBar element that our platform supports.
        ActionBar actionBar = getActionBar();

        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Setting custom tab title.
        loginTab = actionBar.newTab().setText(R.string.login);
        registerTab = actionBar.newTab().setText(R.string.register);

        // Setting tab listeners.
        loginTab.setTabListener(new TabListener(loginFragment, this));
        registerTab.setTabListener(new TabListener(registerFragment, this));


        // Adding tabs to the ActionBar.
        if (MainActivity.currentTab == registerTabPosition) {
            actionBar.addTab(loginTab, loginTabPosition, false);
            actionBar.addTab(registerTab, registerTabPosition, true);
        } else {
            actionBar.addTab(loginTab, loginTabPosition, true);
            actionBar.addTab(registerTab, registerTabPosition, false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
