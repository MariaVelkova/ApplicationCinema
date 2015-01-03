package com.example.maria.cinema.common;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maria.cinema.R;
import com.example.maria.cinema.fragments.FragmentBookTicketDialog;
import com.example.maria.cinema.models.Cinema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Maria on 1/2/2015.
 */
public class MovieProgramCustomAdapter extends BaseAdapter {
    private Activity context;
    private int movieId;
    public MovieProgramCustomAdapter(Activity context, int movieId) {
        this.context = context;
        this.movieId = movieId;
    }

    public ArrayList<Cinema> getMovieProgram() {
        DBManager dbManager = DBManager.getInstance();
        return dbManager.getCinemasByMovieId(movieId);
    }

    @Override
    public int getCount() {
        return getMovieProgram().size();
    }

    @Override
    public Cinema getItem(int position) {
        return getMovieProgram().get(position);
    }

    @Override
    public long getItemId(int position) {
        return getMovieProgram().get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView cinemaName;
        TextView availableTickets;
        Button bookTicketsBtn;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.program_cinema_row, parent, false);
            cinemaName = (TextView) convertView.findViewById(R.id.cinemaName);
            availableTickets = (TextView) convertView.findViewById(R.id.availableTickets);
            bookTicketsBtn = (Button) convertView.findViewById(R.id.bookTicketsBtn);

            convertView.setTag(R.id.cinemaName, cinemaName);
            convertView.setTag(R.id.availableTickets, availableTickets);
            convertView.setTag(R.id.bookTicketsBtn, bookTicketsBtn);
        } else {
            cinemaName = (TextView) convertView.getTag(R.id.cinemaName);
            availableTickets = (TextView) convertView.getTag(R.id.availableTickets);
            bookTicketsBtn = (Button) convertView.getTag(R.id.bookTicketsBtn);
        }

        final Cinema currentCinema = getItem(position);
        cinemaName.setText(currentCinema.getName());
        int availableTicketsCount = DBManager.getInstance().getAvailableTickets(currentCinema.getId(), movieId);
        availableTickets.setText(String.format(context.getString(R.string.availableTicketsLabel),availableTicketsCount));
        if (availableTicketsCount == 0) {
            bookTicketsBtn.setEnabled(false);
        } else {
            bookTicketsBtn.setEnabled(true);
        }
        bookTicketsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.bookTicketsBtn) {
                    // Create an instance of the dialog fragment and show it
                    DialogFragment dialog = FragmentBookTicketDialog.getInstance(movieId, currentCinema.getId());
                    dialog.show(context.getFragmentManager(), "FragmentBookTicketDialog");
                }
            }
        });
        return convertView;
    }
}
