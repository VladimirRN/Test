package com.example.vladimir.contactreader.model.route;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {
    @SerializedName("start_location")
    @Expose
    private StartLocation startLocation;
    @SerializedName("end_location")
    @Expose
    private EndLocation endLocation;

    public StartLocation getStartLocation() {
        return startLocation;
    }

    public EndLocation getEndLocation() {
        return endLocation;
    }
}
