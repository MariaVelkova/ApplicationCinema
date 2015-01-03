package com.example.maria.cinema.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.maria.cinema.R;
import com.example.maria.cinema.common.DBManager;
import com.example.maria.cinema.models.Cinema;
import com.example.maria.cinema.models.Movie;
import com.example.maria.cinema.models.Projection;
import com.example.maria.cinema.models.Reservation;

import java.util.ArrayList;


public class FragmentBookTicketDialog extends DialogFragment implements View.OnClickListener {

    // Use this instance of the interface to deliver action events
//    BookTicketDialogListener mListener;
    private Spinner cinemaName;
    private Spinner bookTicketsCount;
    Button bookBtn, cancelBtn;
    int movieId, currentMovieId;
    int cinemaId, currentCinemaId;


//    /* The activity that creates an instance of this dialog fragment must
//    * implement this interface in order to receive event callbacks.
//    * Each method passes the FragmentBookTicketDialog in case the host needs to query it. */
//    public interface BookTicketDialogListener {
//        public void onDialogPositiveClick(DialogFragment dialog);
//        public void onDialogNegativeClick(DialogFragment dialog);
//    }


    /**
     * Create a new instance of FragmentBookTicketDialog, providing "movieId" and "cinemaId"
     * as arguments.
     */
    public static FragmentBookTicketDialog getInstance(int movieId, int cinemaId) {
        FragmentBookTicketDialog f = new FragmentBookTicketDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("movieId", movieId);
        args.putInt("cinemaId", cinemaId);
        f.setArguments(args);

        return f;
    }

//    // Override the Fragment.onAttach() method to instantiate the BookTicketDialogListener
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        // Verify that the host activity implements the callback interface
//        try {
//            // Instantiate the NoticeDialogListener so we can send events to the host
//            mListener = (BookTicketDialogListener) activity;
//        } catch (ClassCastException e) {
//            // The activity doesn't implement the interface, throw exception
//            throw new ClassCastException(activity.toString()
//                    + " must implement BookTicketDialogListener");
//        }
//    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        // Get the layout inflater
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//
//        // Inflate and set the layout for the dialog
//        // Pass null as the parent view because its going in the dialog layout
//
//        View view = inflater.inflate(R.layout.book_ticket_dialog, null);
//        cinemaName = (Spinner) view.findViewById(R.id.cinemaName);
//        cinemaName.setOnItemSelectedListener(this);
//        bookTicketsCount = (Spinner) view.findViewById(R.id.bookTicketCount);
//        bookTicketsCount.setOnItemSelectedListener(this);
//
//        builder.setView(view);
//
//        builder.setMessage(R.string.book_now)
//                .setPositiveButton(R.string.book, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // FIRE ZE MISSILES!
//                    }
//                })
//                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User cancelled the dialog
//                    }
//                });
//        // Create the AlertDialog object and return it
//        AlertDialog dialog = builder.create();
//
//        return dialog;
//    }

    public FragmentBookTicketDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(getString(R.string.book_now));
        currentCinemaId = getArguments().getInt("cinemaId", 0);
        currentMovieId = getArguments().getInt("movieId", 0);
        DBManager dbManager = DBManager.getInstance();
        ArrayList<Cinema> cinemas = dbManager.getCinemasByMovieId(currentMovieId);
        int maxAvailableTicketsCount = 0;
        View view = inflater.inflate(R.layout.book_ticket_dialog, container);
        cinemaName = (Spinner) view.findViewById(R.id.cinemaName);
        ArrayList<String> cinemasList = new ArrayList<String>();
        for(Cinema cinema:cinemas) {
            cinemasList.add(cinema.getName());
        }
        ArrayAdapter<String> CinemaArrayAdapter=
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        cinemasList);
        cinemaName.setAdapter(CinemaArrayAdapter);
        if (currentCinemaId > 0) {
            Cinema cinema = dbManager.getCinemaById(currentCinemaId);
            cinemaName.setSelection(CinemaArrayAdapter.getPosition(cinema.getName()));
            maxAvailableTicketsCount = dbManager.getAvailableTickets(currentCinemaId, currentMovieId);
        } else {
            String cinemaName = CinemaArrayAdapter.getItem(0);
            Cinema cinema = dbManager.getCinemaByName(cinemaName);
            maxAvailableTicketsCount = dbManager.getAvailableTickets(cinema.getId(), currentMovieId);
        }

        bookTicketsCount = (Spinner) view.findViewById(R.id.bookTicketCount);
        if (maxAvailableTicketsCount > 0) {
            ArrayList<String> countTicketsList = new ArrayList<String>();
            for (int i = 1; i <= maxAvailableTicketsCount; i++) {
                countTicketsList.add(Integer.toString(i));
            }
            ArrayAdapter<String> CountTicketsArrayAdapter=
                    new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item,
                            countTicketsList);
        }

        cinemaName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                int maxAvailableTicketsCount = 0;
                DBManager dbManager = DBManager.getInstance();
                Cinema cinema = dbManager.getCinemaByName(cinemaName.getSelectedItem().toString());
                maxAvailableTicketsCount = dbManager.getAvailableTickets(cinema.getId(), currentMovieId);
                if (maxAvailableTicketsCount > 0) {
                    bookTicketsCount.setAdapter(null);
                    ArrayList<String> countTicketsList = new ArrayList<String>();
                    for (int i = 1; i <= maxAvailableTicketsCount; i++) {
                        countTicketsList.add(Integer.toString(i));
                    }
                    ArrayAdapter<String> CountTicketsArrayAdapter=
                            new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                    countTicketsList);
                    bookTicketsCount.setAdapter(CountTicketsArrayAdapter);
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }

        });

        bookBtn = (Button) view.findViewById(R.id.bookBtn);
        bookBtn.setOnClickListener(this);
        cancelBtn = (Button) view.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bookBtn:
                DBManager dbManager = DBManager.getInstance();
                Cinema cinema = dbManager.getCinemaByName(cinemaName.getSelectedItem().toString());
                Movie movie = dbManager.getMovieById(currentMovieId);
                int ticketsCount = Integer.parseInt(bookTicketsCount.getSelectedItem().toString());
                dbManager.makeReservation(cinema.getId(), currentMovieId, ticketsCount);
                //Projection projection = dbManager.getProjection(cinema.getId(), currentMovieId);
                //Reservation reservation = new Reservation(dbManager.getReservations().size()+1, dbManager.getCurrentUserId(),projection.getId(),ticketsCount);
                Toast.makeText(getActivity(),String.format(getString(R.string.new_reservation), dbManager.getCurrentUser().getUsername(), ticketsCount, movie.getTitle(),cinema.getName()),Toast.LENGTH_SHORT).show();

                getDialog().hide();
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
                break;
            case R.id.cancelBtn:
                getDialog().cancel();
                break;
        }
    }


}
