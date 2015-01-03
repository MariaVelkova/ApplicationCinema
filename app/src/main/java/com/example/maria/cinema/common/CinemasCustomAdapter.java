package com.example.maria.cinema.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maria.cinema.R;
import com.example.maria.cinema.models.Cinema;

import java.io.IOException;

/**
 * Created by Maria on 12/29/2014.
 */
public class CinemasCustomAdapter extends BaseAdapter {
    private Context context;
    private DBManager dbManager;

    public CinemasCustomAdapter(Context context) {
        this.context = context;
        this.dbManager = DBManager.getInstance();
    }

    @Override
    public int getCount() {
        return dbManager.getCinemas().size();
    }

    @Override
    public Cinema getItem(int position) {
        Log.d("cinema position", Integer.toString(position));
        return dbManager.getCinemas().get(position);
    }

    @Override
    public long getItemId(int position) {
        return dbManager.getCinemas().get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView cinemaLogo;
        TextView cinemaName;

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.cinema_row, parent, false);
            cinemaLogo = (ImageView) convertView.findViewById(R.id.cinemaLogo);
            convertView.setTag(R.id.cinemaLogo, cinemaLogo);

            cinemaName = (TextView) convertView.findViewById(R.id.cinemaName);
            convertView.setTag(R.id.cinemaName, cinemaName);
        } else  {
            cinemaLogo = (ImageView) convertView.getTag(R.id.cinemaLogo);
            cinemaName = (TextView) convertView.getTag(R.id.cinemaName);
        }
        Resources resources = convertView.getResources();
        final Cinema currentCinema = getItem(position);
        try {
            //cinemaLogo.setImageDrawable(Drawable.createFromPath("drawable/"+currentCinema.getPicture1()));
            cinemaLogo.setImageDrawable(Drawable.createFromResourceStream(resources, new TypedValue(), resources.getAssets().open("cinema/" + currentCinema.getPicture1()), null));
        } catch (IOException e) {
           cinemaLogo.setVisibility(View.INVISIBLE);
           e.printStackTrace();
        }
        cinemaName.setText(currentCinema.getName());

        return convertView;
    }
}
