package com.example.vladimir.contactreader.data.geocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Response {
    @SerializedName("GeoObjectCollection")
    @Expose
    private GeoObjectCollection geoObjectCollection;

    public GeoObjectCollection getGeoObjectCollection() {
        return geoObjectCollection;
    }

}
