package com.example.vladimir.contactreader.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.model.ContactLoading;
import com.example.vladimir.contactreader.model.db.AppDataBase;
import com.example.vladimir.contactreader.model.db.Contact;
import com.example.vladimir.contactreader.model.db.ContactDao;
import com.example.vladimir.contactreader.view.ContactView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class ContactPresenter extends MvpPresenter<ContactView>  {

    private static final String TAG = "TAG";
    private ContactLoading contactLoading;
    private DisposableObserver<String> disposableObserver;

    //@Inject
    public ContactPresenter(ContactLoading contactLoading) {
        this.contactLoading =  contactLoading;
        startLoadingContacts();
    }


    public void showListContact() {
        AppDataBase dataBase = App.getInstance().getDataBase();
        ContactDao contactDao = dataBase.contactDao();
        contactDao.getAllContact()
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
                .flatMap((Function<List<Contact>, ObservableSource<Contact>>) Observable::fromIterable)
                .flatMap((Function<Contact, ObservableSource<String>>) contact -> contactLoading.getDeatailsContact(contact))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }
}
