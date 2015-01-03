package com.example.maria.cinema.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.maria.cinema.R;
import com.example.maria.cinema.common.BaseActivity;
import com.example.maria.cinema.common.DBManager;
import com.example.maria.cinema.fragments.FragmentCinemaDetails;
import com.example.maria.cinema.models.User;

public class CinemaDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If the screen changes to landscape mode at any moment, we switch back to multiPane view mode.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {

            FragmentCinemaDetails cinemaDetails = new FragmentCinemaDetails();
            // Get and set the position input by user which is the construction arguments for this fragment
            cinemaDetails.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, cinemaDetails).addToBackStack("Cinema").commit();
        }
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                intent = new Intent(this, UserHomeActivity.class);
                intent.putExtra("currentTab", UserHomeActivity.cinemasTabPosition);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                DBManager dbManager = DBManager.getInstance();
                dbManager.logoutUser();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
