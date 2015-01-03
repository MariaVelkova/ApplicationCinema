package com.example.maria.cinema.activities;

import com.example.maria.cinema.activities.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.maria.cinema.R;
import com.example.maria.cinema.common.BaseActivity;
import com.example.maria.cinema.common.DBManager;
import com.example.maria.cinema.common.PlaceholderFragment;
import com.example.maria.cinema.fragments.FragmentCinemaDetails;
import com.example.maria.cinema.fragments.FragmentCinemasTab;
import com.example.maria.cinema.fragments.FragmentMovieDetails;
import com.example.maria.cinema.fragments.FragmentMoviesTab;
import com.example.maria.cinema.listeners.TabListener;
import com.example.maria.cinema.models.Cinema;

public class UserHomeActivity extends BaseActivity {
    public static final int cinemasTabPosition = 0;
    public static final int moviesTabPosition = 1;
    ActionBar.Tab cinemasTab, moviesTab;
    Fragment cinemasFragment = new FragmentCinemasTab();
    Fragment moviesFragment = new FragmentMoviesTab();
    Fragment cinemaDetailsFragment = new FragmentCinemaDetails();
    Fragment movieDetailsFragment = new FragmentMovieDetails();
    String backStackStateName = "Home";
    int cinemaId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        int currentTab = -1;

        Intent callerIntent = getIntent();

        if (callerIntent != null) {
            currentTab = callerIntent.getIntExtra("currentTab", MainActivity.currentTab);
            MainActivity.currentTab = currentTab;
            cinemaId = callerIntent.getIntExtra("cinemaId",0);
            Cinema cinema = DBManager.getInstance().getCinemaById(cinemaId);
            if (cinema != null) {
                backStackStateName = "DetailsCinema" + cinemaId;
            }
        }

        if (savedInstanceState != null) {
            PlaceholderFragment placeholderFragment = new PlaceholderFragment();
            placeholderFragment.setLayoutId(R.layout.activity_user_home);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, placeholderFragment)
                    .addToBackStack(backStackStateName)
                    .commit();
        }

        ActionBar actionBar = getActionBar();
        if (cinemaId > 0) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_TABS);

        cinemasTab = actionBar.newTab().setText(R.string.cinemas);
        moviesTab = actionBar.newTab().setText(R.string.movies);

        cinemasTab.setTabListener(new TabListener(cinemasFragment, this));
        moviesTab.setTabListener(new TabListener(moviesFragment, this));

        if (MainActivity.currentTab == moviesTabPosition || currentTab == moviesTabPosition) {
            actionBar.addTab(cinemasTab, cinemasTabPosition, false);
            actionBar.addTab(moviesTab, moviesTabPosition, true);
        } else {
            actionBar.addTab(cinemasTab, cinemasTabPosition, true);
            actionBar.addTab(moviesTab, moviesTabPosition, false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (cinemaId > 0) {
                    Intent intent = new Intent(this, CinemaDetailsActivity.class);
                    intent.putExtra("cinemaId", cinemaId);
                    startActivity(intent);
                }
                return true;
            case R.id.action_logout:
                DBManager dbManager = DBManager.getInstance();
                dbManager.logoutUser();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
