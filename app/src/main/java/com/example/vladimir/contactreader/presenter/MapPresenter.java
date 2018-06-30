package com.example.vladimir.contactreader.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.model.SaveMapPosition;
import com.example.vladimir.contactreader.model.db.AppDataBase;
import com.example.vladimir.contactreader.model.db.Contact;
import com.example.vladimir.contactreader.model.db.ContactDao;
import com.example.vladimir.contactreader.view.MapView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {
    private static final String TAG = "TAG";
  //  private LatLng geo;
   // private Long idContact;
    private SaveMapPosition positionInterface;
    private DisposableObserver<Void> disposableObserver;

    @Inject
    public MapPresenter(SaveMapPosition positionInterface) {
        this.positionInterface = positionInterface;
    }

    public void setLatLng(LatLng latLng) {
        Log.d(TAG, "LatLNg = " + latLng);
        //getViewState().showMarker(latLng);
      //  geo = latLng;
        //TODO delete this metod before
    }

    public void saveLanLng(LatLng latLng, Long id) {
        disposableObserver = new DisposableObserver<Void>() {
            @Override
            public void onNext(Void aVoid) {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "координаты сохранены в бд");
            }
        };
        positionInterface.savePosition(latLng, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        //TODO сделать отписку

    }


    public void mapReady(GoogleMap googleMap, Long id) {
        AppDataBase dataBase = App.getInstance().getDataBase();
        ContactDao contactDao = dataBase.contactDao();
       // Log.d(TAG, "idcontact = " + idContact);
        contactDao.getContactById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Contact>() {
                    @Override
                    public void onSuccess(Contact contact) {
                        Log.d(TAG, "координаты выгружены");
                        if (contact.getLat() != null && contact.getLng() != null) {
                            double lat = contact.getLat();
                            double lng = contact.getLng();
                            LatLng latLngFromDB = new LatLng(lat, lng);
                            getViewState().showMarker(googleMap, latLngFromDB);
                        } else {
                            LatLng latlngDefault = new LatLng(57, 53);
                            getViewState().showMarker(googleMap, latlngDefault);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "ошибка при выгрузке координат");
                        e.printStackTrace();
                    }
                });
    }

//    public void idForDB(Long id) {
//        Log.d(TAG, "idForDb = " + id);
//        idContact = id;
//    }

    public void showMarkerForListContact(GoogleMap googleMap) {
        AppDataBase dataBase = App.getInstance().getDataBase();
        ContactDao contactDao = dataBase.contactDao();
        //Log.d(TAG, "idcontact = " + idContact);
        contactDao.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Contact>>() {
                    @Override
                    public void onSuccess(List<Contact> contacts) {
                        getViewState().showMarkers(googleMap, contacts);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }
}
