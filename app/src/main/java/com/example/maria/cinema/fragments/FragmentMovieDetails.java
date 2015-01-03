package com.example.maria.cinema.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.maria.cinema.R;
import com.example.maria.cinema.common.DBManager;
import com.example.maria.cinema.common.MovieProgramCustomAdapter;
import com.example.maria.cinema.models.Movie;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maria on 12/29/2014.
 */
public class FragmentMovieDetails extends Fragment {
    Bundle args;
    // Create a new instance of FragmentMovieDetails, showing the movie at 'position'.
    public static FragmentMovieDetails newInstance(int position, int cinemaId) {
        Log.d("FragmentMovieDetails", Integer.toString(position));
        FragmentMovieDetails f = new FragmentMovieDetails();
        Bundle args = new Bundle();
        args.putInt("currentChoice", position);
        args.putInt("cinemaId", cinemaId);
        f.setArguments(args);
        return f;
    }

    public int getCurrentPosition() {
        return getArguments().getInt("currentChoice", 0);
    }
    public ArrayList<Movie> getCurrentList() {
        int cinenaId = getArguments().getInt("cinemaId", 0);
        if (cinenaId == 0) {
            return DBManager.getInstance().getMovies();
        } else {
            return DBManager.getInstance().getMoviesByCinemaId(cinenaId);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            Log.d("FragmentMovieDetails:::onCreateView", "container == null");
            //There is no need to create a scroll as it won't be displayed
            return null;
        } else {
            Log.d("FragmentMovieDetails:::onCreateView", "container != null");
            // Inflate the layout for this fragment
            ScrollView scroll = new ScrollView(getActivity());
            View view = inflater.inflate(R.layout.fragment_fragment_movie_details, container, false);
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
        View movieDetails = activity.findViewById(R.id.movieContainer);
        if (movieDetails != null && movieDetails.getVisibility() == View.VISIBLE) {
            int position;
            Log.d("FragmentMovieDetails", "onActivityCreated");

            Resources resources = getResources();
            AssetManager assetManager = resources.getAssets();
            if (savedInstanceState == null) {
                position = getCurrentPosition();
            } else {
                position = savedInstanceState.getInt("currentChoice");
            }
            Log.d("position", Integer.toString(position));
            final Movie movie = getCurrentList().get(position);

            ImageView moviePoster = (ImageView) activity.findViewById(R.id.moviePoster);
            try {
                moviePoster.setImageDrawable(Drawable.createFromResourceStream(resources, new TypedValue(), assetManager.open("movie/" + movie.getPoster()), null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            TextView movieTitleView = (TextView) activity.findViewById(R.id.movieTitle);
            movieTitleView.setText(getString(R.string.movieTitleLabel) + " " + movie.getTitle());

            TextView movieCastView = (TextView) activity.findViewById(R.id.movieCast);
            movieCastView.setText(getString(R.string.movieCastLabel) + " " + movie.getCast());

            getActivity().getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.cinemasOnScreenList,
                            FragmentMovieOnScreenIn.newInstance(movie.getId())
                    )
                    .commit();

            Button movieBookTicketsBtn = (Button) activity.findViewById(R.id.movieBookTicketsBtn);
            movieBookTicketsBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.movieBookTicketsBtn) {
                        // Create an instance of the dialog fragment and show it
                        DialogFragment dialog = FragmentBookTicketDialog.getInstance(movie.getId(), 0);
                        dialog.show(activity.getFragmentManager(), "FragmentBookTicketDialog");
                    }
            }});
            activity.getActionBar().setTitle(movie.getTitle());
        }

    }
}
