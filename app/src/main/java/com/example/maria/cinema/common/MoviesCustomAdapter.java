package com.example.maria.cinema.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.maria.cinema.models.Movie;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Maria on 12/29/2014.
 */
public class MoviesCustomAdapter extends BaseAdapter {
    private Context context;
    private Resources resources;
    private ArrayList<Movie> movies;

    public MoviesCustomAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.resources = context.getResources();
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView moviePosterThumb;
        TextView movieTitleOption;

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.movie_row, parent, false);

            moviePosterThumb = (ImageView) convertView.findViewById(R.id.moviePosterThumb);
            convertView.setTag(R.id.moviePosterThumb, moviePosterThumb);

            movieTitleOption = (TextView) convertView.findViewById(R.id.movieTitleOption);
            convertView.setTag(R.id.movieTitleOption, movieTitleOption);
        } else {
            moviePosterThumb = (ImageView) convertView.getTag(R.id.moviePosterThumb);
            movieTitleOption = (TextView) convertView.getTag(R.id.movieTitleOption);
        }


        final Movie currentMovie = getItem(position);
            try
            {

                final int THUMBNAIL_SIZE = 200;
                InputStream iS = resources.getAssets().open("movie/" + currentMovie.getPoster());
                //FileInputStream fis = new resources.getAssets().open("movie/" + currentMovie.getPoster());;
                Bitmap imageBitmap = BitmapFactory.decodeStream(iS);
                int outWidth;
                int outHeight;
                int inWidth = imageBitmap.getWidth();
                int inHeight = imageBitmap.getHeight();
                if(inWidth > inHeight){
                    outWidth = THUMBNAIL_SIZE;
                    outHeight = (inHeight * THUMBNAIL_SIZE) / inWidth;
                } else {
                    outHeight = THUMBNAIL_SIZE;
                    outWidth = (inWidth * THUMBNAIL_SIZE) / inHeight;
                }
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, outWidth, outHeight, false);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                //imageData = baos.toByteArray();
                moviePosterThumb.setImageBitmap(imageBitmap);
            }
            catch(Exception ex) {

            }

        //Drawable poster = Drawable.createFromResourceStream(resources, new TypedValue(), resources.getAssets().open("movie/interstellar.jpg" ), null);


        movieTitleOption.setText(currentMovie.getTitle());

        return convertView;
    }
}
