package com.example.maria.cinema.models;

import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import java.util.ArrayList;

/**
 * Created by Maria on 12/28/2014.
 */
public class Cinema {
    private int id;
    private String name;
    private String address;
    private String working_time;
    private String picture1;
    private String picture2;

    public Cinema(int id, String name, String address, String working_time, String picture1, String picture2) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.working_time = working_time;
        this.picture1 = picture1;
        this.picture2 = picture2;
        //Drawable.createFromStream(getAssets().open("Cloths/btn_no.png"), null);
        //this.picture1 = Drawable.createFromResourceStream(resources,new TypedValue(), resources.getAssets().open(file_name_picture1), null);

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorking_time() {
        return working_time;
    }

    public void setWorking_time(String working_time) {
        this.working_time = working_time;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }
}
