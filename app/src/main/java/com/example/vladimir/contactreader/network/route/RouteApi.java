package com.example.vladimir.contactreader.network.route;

import com.example.vladimir.contactreader.data.route.RouteApiResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RouteApi {

    @GET("/maps/api/directions/json")
    Single<RouteApiResponse> getRoute(@Query("origin") String origin,
                                      @Query("destination") String destination,
                                      @Query("key") String key
    );
}
