package com.example.vladimir.contactreader.model.route;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("legs")
    @Expose
    private List<Leg> legs = null;

    public String getSummary() {
        return summary;
    }

    public List<Leg> getLegs() {
        return legs;
    }
}
