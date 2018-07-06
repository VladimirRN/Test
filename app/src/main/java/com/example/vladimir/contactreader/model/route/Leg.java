package com.example.vladimir.contactreader.model.route;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Leg {
    @SerializedName("steps")
    @Expose

    private List<Step> steps = null;

    public List<Step> getSteps() {return  steps;}
}
