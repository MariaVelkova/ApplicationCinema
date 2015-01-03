package com.example.maria.cinema.models;

import android.graphics.drawable.Drawable;

/**
 * Created by Maria on 12/28/2014.
 */
public class Movie {
    private int id;
    private String title;
    private String poster;
    private String cast;

    public Movie(int id, String title, String poster, String cast) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.cast = cast;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }
}
