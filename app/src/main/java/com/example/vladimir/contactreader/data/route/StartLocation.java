package com.example.vladimir.contactreader.data.route;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartLocation {
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
