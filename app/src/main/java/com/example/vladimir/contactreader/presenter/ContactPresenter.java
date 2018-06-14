package com.example.vladimir.contactreader.presenter;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.model.ContactLoad;
import com.example.vladimir.contactreader.model.db.Contact;
import com.example.vladimir.contactreader.view.ContactView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class ContactPresenter extends MvpPresenter<ContactView> {

    private static final String TAG = "TAG";
    private ContactLoad contactLoad;
    private DisposableObserver<String> disposableObserver;


    public ContactPresenter(Context context) {
        this.contactLoad = new ContactLoad(context, this);
        startLoadingContacts();
    }

    public void showListContact(List<Contact> contacts) {
        getViewState().showContacts(contacts);
    }

    public void disposeFragment() {
        disposableObserver.dispose();
    }


    public void startLoadingContacts() {
        getViewState().showProgress();
        disposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "ERROR");
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
                getViewState().hideProgress();
                contactLoad.getListDisplayName();
            }
        };
        contactLoad.getContacts()
                .flatMap((Function<List<Contact>, ObservableSource<Contact>>) Observable::fromIterable)
                .flatMap((Function<Contact, ObservableSource<String>>) contact -> contactLoad.getDeatailsContact(contact))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }
}
