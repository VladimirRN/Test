package com.example.vladimir.contactreader.model.route;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class RouteApiResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;
    @SerializedName("routes")
    @Expose
    private List<Route> routes = null;
    public String getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<Route> getRoutes() {
        return routes;
    }
}

