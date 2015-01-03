package com.example.maria.cinema.models;

/**
 * Created by Maria on 1/1/2015.
 */
public class Reservation {
    private int id;
    private int userId;
    private int projectionId;
    private int bookedTicketsCount;

    public Reservation(int id, int userId, int projectionId, int bookedTicketsCount) {
        this.id = id;
        this.userId = userId;
        this.projectionId = projectionId;
        this.bookedTicketsCount = bookedTicketsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(int projectionId) {
        this.projectionId = projectionId;
    }

    public int getBookedTicketsCount() {
        return bookedTicketsCount;
    }

    public void setBookedTicketsCount(int bookedTicketsCount) {
        this.bookedTicketsCount = bookedTicketsCount;
    }
}
