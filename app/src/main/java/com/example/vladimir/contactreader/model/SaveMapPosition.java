package com.example.vladimir.contactreader.model;

import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Observable;

public interface SaveMapPosition {
    Observable<Void> savePosition(LatLng latLng, long id);
}
