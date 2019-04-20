package com.example.sayarsamanta.cashrichtest;

import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("date")
    private String date;

    @SerializedName("equity")
    private String quity;

    public Model(String date, String quity) {
        this.date = date;
        this.quity = quity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuity() {
        return quity;
    }

    public void setQuity(String quity) {
        this.quity = quity;
    }
}
