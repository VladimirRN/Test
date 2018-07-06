package com.example.vladimir.contactreader.presentation.map;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.vladimir.contactreader.data.Contact;
import com.example.vladimir.contactreader.data.route.RouteApiResponse;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface MapView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void showMarker(GoogleMap googleMap, LatLng latLng, String address, String name);

    @StateStrategyType(SkipStrategy.class)
    void showMarkers(GoogleMap googleMap, List<Contact> contactList);

    @StateStrategyType(SkipStrategy.class)
    void showMarkerWithGeocodePosition(String position);

    @StateStrategyType(SkipStrategy.class)
    void showRoute(GoogleMap googleMap, RouteApiResponse routeApiResponse, LatLng end, LatLng start, String nameEnd, String nameStart,
                   String startAddress, String endAddress);
}
