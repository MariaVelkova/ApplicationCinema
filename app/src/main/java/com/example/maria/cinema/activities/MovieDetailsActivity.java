package com.example.maria.cinema.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.maria.cinema.R;
import com.example.maria.cinema.common.BaseActivity;
import com.example.maria.cinema.common.DBManager;
import com.example.maria.cinema.fragments.FragmentBookTicketDialog;
import com.example.maria.cinema.fragments.FragmentCinemaDetails;
import com.example.maria.cinema.fragments.FragmentMovieDetails;

public class MovieDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If the screen changes to landscape mode at any moment, we switch back to multiPane view mode.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {

            FragmentMovieDetails movieDetails = new FragmentMovieDetails();
            // Get and set the position input by user which is the construction arguments for this fragment
            movieDetails.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, movieDetails).addToBackStack("Movie").commit();
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
                intent.putExtra("currentTab", UserHomeActivity.moviesTabPosition);
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
