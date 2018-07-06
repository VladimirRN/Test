package com.example.vladimir.contactreader.model.geocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoResponse {
    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

}
