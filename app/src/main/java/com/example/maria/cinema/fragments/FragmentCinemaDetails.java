package com.example.maria.cinema.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.maria.cinema.R;
import com.example.maria.cinema.activities.UserHomeActivity;
import com.example.maria.cinema.common.CinemasCustomAdapter;
import com.example.maria.cinema.common.DBManager;
import com.example.maria.cinema.models.Cinema;

import java.io.IOException;


public class FragmentCinemaDetails extends Fragment {

    // Create a new instance of FragmentCinemaDetails, showing the cinema at 'position'.
    public static FragmentCinemaDetails newInstance(int position) {
        Log.d("FragmentCinemaDetails", Integer.toString(position));
        FragmentCinemaDetails f = new FragmentCinemaDetails();
        Bundle args = new Bundle();
        args.putInt("currentChoice", position);
        f.setArguments(args);
        return f;
    }

    public int getCurrentPosition() {
        return getArguments().getInt("currentChoice", 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            Log.d("FragmentCinemaDetails:::onCreateView", "container == null");
            //There is no need to create a scroll as it won't be displayed
            return null;
        } else {
            Log.d("FragmentCinemaDetails:::onCreateView", "container != null");
            // Inflate the layout for this fragment
            ScrollView scroll = new ScrollView(getActivity());
            View view = inflater.inflate(R.layout.fragment_fragment_cinema_details, container, false);
            int padding = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10, getActivity().getResources().getDisplayMetrics());
            view.setPadding(padding, padding, padding, padding);
            scroll.addView(view);
            return scroll;
        }

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();
        View cinemaDetails = activity.findViewById(R.id.cinemaContainer);
        if (cinemaDetails != null && cinemaDetails.getVisibility() == View.VISIBLE) {
            int position;
            Log.d("FragmentCinemaDetails", "onActivityCreated");

            DBManager dbManager = DBManager.getInstance();

            Resources resources = getResources();
            AssetManager assetManager = resources.getAssets();
            if (savedInstanceState == null) {
                position = getCurrentPosition();
            } else {
                position = savedInstanceState.getInt("currentChoice");
            }
            Log.d("position", Integer.toString(position));
            Cinema cinema = dbManager.getCinema(position);
            final int cinemaId = cinema.getId();
            String cinemaName = cinema.getName();
            String cinemaAddress = cinema.getAddress();
            String cinemaWorkingTime = cinema.getWorking_time();
            String cinemaPic1 = cinema.getPicture1();
            String cinemaPic2 = cinema.getPicture2();

            ImageView cinemaPicture1 = (ImageView) activity.findViewById(R.id.cinemaPicture1);
            try {
                cinemaPicture1.setImageDrawable(Drawable.createFromResourceStream(resources, new TypedValue(), assetManager.open("cinema/" + cinemaPic1), null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            ImageView cinemaPicture2 = (ImageView) activity.findViewById(R.id.cinemaPicture2);
            try {
                cinemaPicture2.setImageDrawable(Drawable.createFromResourceStream(resources, new TypedValue(), assetManager.open("cinema/" + cinemaPic2), null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            TextView cinemaAddressView = (TextView) activity.findViewById(R.id.cinemaAddress);
            cinemaAddressView.setText(getString(R.string.address) + " " + cinemaAddress);
            TextView cinemaWorkingTimeView = (TextView) activity.findViewById(R.id.cinemaWorkingTime);
            cinemaWorkingTimeView.setText(getString(R.string.working_time) + " " + cinemaWorkingTime);
            Button cinemaProgramBtn = (Button) activity.findViewById(R.id.cinemaProgramBtn);
            cinemaProgramBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.cinemaProgramBtn) {
                        Intent intent = new Intent(activity, UserHomeActivity.class);
                        intent.putExtra("currentTab", UserHomeActivity.moviesTabPosition);
                        intent.putExtra("cinemaId", cinemaId);
                        startActivity(intent);

                    }
                }
            });
            activity.getActionBar().setTitle(cinemaName);
        }

    }

}
