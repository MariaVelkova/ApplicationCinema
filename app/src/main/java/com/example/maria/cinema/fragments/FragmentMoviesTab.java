package com.example.maria.cinema.fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.maria.cinema.R;
import com.example.maria.cinema.activities.MovieDetailsActivity;
import com.example.maria.cinema.common.DBManager;
import com.example.maria.cinema.common.MoviesCustomAdapter;
import com.example.maria.cinema.models.Cinema;
import com.example.maria.cinema.models.Movie;

import java.util.ArrayList;


public class FragmentMoviesTab extends Fragment implements AdapterView.OnItemClickListener {
    boolean mDualPane = false;
    int mCurCheckPosition = 0;
    int cinemaId = 0;
    ListView list;
    MoviesCustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            //There is no need to create a scroll as it won't be displayed
            return null;
        } else {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_fragment_movies_tab, container, false);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();
        String actionBarTitle = getString(R.string.movies);
        DBManager dbManager = DBManager.getInstance();
        ArrayList<Movie> movies = dbManager.getMovies();
        Intent callerIntent = activity.getIntent();
        if (callerIntent != null) {
            cinemaId = callerIntent.getIntExtra("cinemaId", 0);
            Log.d("cinemaId", Integer.toString(cinemaId));
            if (cinemaId > 0) {
                Cinema cinema =  dbManager.getCinemaById(cinemaId);
                actionBarTitle = String.format(getString(R.string.cinema_program), cinema.getName());
                movies = dbManager.getMoviesByCinemaId(cinemaId);
            }
        }
        activity.getActionBar().setTitle(actionBarTitle);
        list = (ListView) activity.findViewById(R.id.moviesList);
        adapter = new MoviesCustomAdapter(activity, movies);

        // If we have lots of users we should enable fast scrolling
        if (adapter.getCount() > 3) {
            list.setFastScrollEnabled(true);
        }

        // Assign the adapter to the list
        list.setAdapter(adapter);

        // Set ITEM CLICK listener
        list.setOnItemClickListener(this);

        View movieDetails = activity.findViewById(R.id.movieDetails);
        mDualPane = movieDetails != null && movieDetails.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("currentChoice", 0);
        }

        // Highlight the selected item in the list view .
        //getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        if (mDualPane) {
            showDetails(mCurCheckPosition);
        } else {
            //getListView().setItemChecked(mCurCheckPosition, true);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentChoice", mCurCheckPosition);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mCurCheckPosition = position;
        showDetails(mCurCheckPosition);
    }

    // Helper function to show the details of a selected item, either by displaying a fragment
    // in-place in the current UI, or starting a whole new activity in which it is displayed.

    void showDetails(int mCurCheckPosition) {

        Movie movie = adapter.getItem(mCurCheckPosition);
        Log.d("Movie:", movie.getTitle());
        if (mDualPane) {
            // Display both titles and content, side to side. Highlight the current selection.
            //gridView.getSelectedView().setHovered(true);

            // Check what fragment is currently shown, replace if needed.
            FragmentMovieDetails movieDetails = (FragmentMovieDetails) getFragmentManager().findFragmentById(R.id.movieDetails);
            if (movieDetails == null || movieDetails.getCurrentPosition() != mCurCheckPosition) {
                // Make new fragment to show this selection.
                movieDetails = FragmentMovieDetails.newInstance(mCurCheckPosition, cinemaId);

                // Execute a transaction, replacing any existing fragment with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.movieDetails, movieDetails);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {

            // Launch new DetailsActivity to display the dialog fragment with selected text. Put selected cinemaId as Extra.
            Intent intent = new Intent();
            intent.setClass(getActivity(), MovieDetailsActivity.class);
            intent.putExtra("currentChoice", mCurCheckPosition);
            intent.putExtra("cinemaId", cinemaId);
            startActivity(intent);
        }
    }
}
