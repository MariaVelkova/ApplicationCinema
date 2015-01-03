package com.example.maria.cinema.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maria.cinema.R;
import com.example.maria.cinema.common.MovieProgramCustomAdapter;

public class FragmentMovieOnScreenIn extends Fragment {

    int movieId;
    int currentMovieId;

    ListView view;
    MovieProgramCustomAdapter adapter;

    public static FragmentMovieOnScreenIn newInstance(int movieId) {
        FragmentMovieOnScreenIn fragment = new FragmentMovieOnScreenIn();
        Bundle args = new Bundle();
        args.putInt("movieId", movieId);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentMovieOnScreenIn() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           currentMovieId = getArguments().getInt("movieId");
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_movie_on_screen_in, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();

        view = (ListView) activity.findViewById(R.id.movieProjectedIn);

        adapter = new MovieProgramCustomAdapter(activity, currentMovieId);
        view.setAdapter(adapter);

    }


}
