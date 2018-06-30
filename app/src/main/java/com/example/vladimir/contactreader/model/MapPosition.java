package com.example.vladimir.contactreader.model;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.model.db.AppDataBase;
import com.example.vladimir.contactreader.model.db.ContactDao;
import com.example.vladimir.contactreader.ui.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class MapPosition implements SaveMapPosition{

    @Inject
    public MapPosition() {

    }

    @Override
    public Observable<Void> savePosition(LatLng latLng, long id) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> emitter) throws Exception {
                try {
                    AppDataBase appDataBase = App.getInstance().getDataBase();
                    ContactDao contactDao = appDataBase.contactDao();
                    double lat = latLng.latitude;
                    double lng = latLng.longitude;
                    contactDao.updateLat(lat, (int) id);
                    contactDao.updateLng(lng, (int) id);
                } catch (Exception e) {
                    emitter.onError(e);
                } finally {
                    emitter.onComplete();
                }
            }
        });
    }
}
