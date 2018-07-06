package com.example.vladimir.contactreader.model.network;

import com.example.vladimir.contactreader.model.geocode.GeoResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodeApi {

    @GET("1.x/")
    Single<GeoResponse> getPositionAddress(
            @Query("geocode")
                    String geocode,
            @Query("format")
                    String format,
            @Query("kind")
                    String kind,
            @Query("spn")
                    String spn);
}

