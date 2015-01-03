package com.example.maria.cinema.models;

/**
 * Created by Maria on 1/1/2015.
 */
public class Projection {
    private int id;
    private int movieId;
    private int cinemaId;
    private int ticketsCount;

    public Projection(int id, int movieId, int cinemaId, int ticketsCount) {
        this.id = id;
        this.movieId = movieId;
        this.cinemaId = cinemaId;
        this.ticketsCount = ticketsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public int getTicketsCount() {
        return ticketsCount;
    }

    public void setTicketsCount(int ticketsCount) {
        this.ticketsCount = ticketsCount;
    }
}
