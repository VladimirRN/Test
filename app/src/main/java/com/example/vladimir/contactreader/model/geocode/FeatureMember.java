package com.example.vladimir.contactreader.model.geocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeatureMember {
    @SerializedName("GeoObject")
    @Expose
    private GeoObject geoObject;

    public GeoObject getGeoObject() {
        return geoObject;
    }
}
