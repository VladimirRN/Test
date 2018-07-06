package com.example.vladimir.contactreader.data;

import com.google.android.gms.maps.model.LatLng;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface SaveMapPosition {
    Observable<Void> savePosition(LatLng latLng, long id, String address);
}
