package com.example.maria.cinema.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maria.cinema.R;
import com.example.maria.cinema.activities.CinemaDetailsActivity;
import com.example.maria.cinema.common.CinemasCustomAdapter;
import com.example.maria.cinema.models.Cinema;

public class FragmentCinemasTab extends Fragment implements AdapterView.OnItemClickListener {
    boolean mDualPane = false;
    int mCurCheckPosition = 0;

    GridView gridView;
    CinemasCustomAdapter adapter;

    // The onCreateView method is called the first time the fragment draws its user interface.
    // Return the View to be drawn, or null if the fragment does not provide a UI.
    // We create a scroll view and text and return a reference to the scroll which is then drawn to the screen.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (container == null) {
            //There is no need to create a scroll as it won't be displayed
            return null;
        } else {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_fragment_cinemas_tab, container, false);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();

        activity.getActionBar().setTitle(getString(R.string.cinemas));
        gridView = (GridView) activity.findViewById(R.id.cinemasView);
        adapter = new CinemasCustomAdapter(activity);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        View cinemaDetails = activity.findViewById(R.id.cinemaDetails);
        mDualPane = cinemaDetails != null && cinemaDetails.getVisibility() == View.VISIBLE;

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

        Cinema cinema = adapter.getItem(mCurCheckPosition);
        Log.d("Cinema:",cinema.getName());
        if (mDualPane) {
            // Display both titles and content, side to side. Highlight the current selection.
            //gridView.getSelectedView().setHovered(true);

            // Check what fragment is currently shown, replace if needed.
            FragmentCinemaDetails cinemaDetails = (FragmentCinemaDetails) getFragmentManager().findFragmentById(R.id.cinemaDetails);
            if (cinemaDetails == null || cinemaDetails.getCurrentPosition() != mCurCheckPosition) {
                // Make new fragment to show this selection.
                cinemaDetails = FragmentCinemaDetails.newInstance(mCurCheckPosition);

                // Execute a transaction, replacing any existing fragment with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.cinemaDetails, cinemaDetails);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {

            // Launch new DetailsActivity to display the dialog fragment with selected text. Put selected cinemaId as Extra.
            Intent intent = new Intent();
            intent.setClass(getActivity(), CinemaDetailsActivity.class);
            intent.putExtra("currentChoice", mCurCheckPosition);
            startActivity(intent);
        }
    }
}
