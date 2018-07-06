package com.example.vladimir.contactreader.presentation.contacts;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.app.AppDataBase;
import com.example.vladimir.contactreader.data.Contact;
import com.example.vladimir.contactreader.data.ContactLoading;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView>  {

    private static final String TAG = "TAG";
    private final AppDataBase database;
    private ContactLoading contactLoading;
    private DisposableObserver<String> disposableObserver;

    @Inject
    public ContactsPresenter(ContactLoading contactLoading, AppDataBase appDataBase) {
        this.database = appDataBase;
        this.contactLoading =  contactLoading;
        startLoadingContacts();
    }


    public void showListContact() {
        database.getContactDao().getAllContact()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contacts -> getViewState().showContacts(contacts));
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
                showListContact();
            }
        };
        contactLoading.getContacts()
                .flatMap(new Function<List<Contact>, ObservableSource<Contact>>() {
                    @Override
                    public ObservableSource<Contact> apply(List<Contact> source) throws Exception {
                        return Observable.fromIterable(source);
                    }
                })
                .flatMap(new Function<Contact, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Contact contact) throws Exception {
                        return contactLoading.getDeatailsContact(contact);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }
}
