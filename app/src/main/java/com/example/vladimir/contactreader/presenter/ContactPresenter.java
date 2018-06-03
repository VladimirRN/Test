package com.example.vladimir.contactreader.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.model.ContactLoad;
import com.example.vladimir.contactreader.model.DetailsLoad;
import com.example.vladimir.contactreader.view.ContactView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class ContactPresenter extends MvpPresenter<ContactView> {

    private final DetailsLoad detailsLoad;
    private static final String TAG = "TAG";
    private ContactLoad model;
    private DisposableObserver<List<String>> disposableObserver;


    public ContactPresenter(Context context) {
        this.model = new ContactLoad(context);
        this.detailsLoad = new DetailsLoad(context);
        startLoadingContacts();
        getViewState().showProgress();
    }


    public void startLoadingContacts() {

        disposableObserver = new DisposableObserver<List<String>>() {
            @Override
            public void onNext(List<String> displayName) {
                getViewState().showContacts(displayName);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                getViewState().hideProgress();
                detailsLoad.getDeatailContact()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String string) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        };
        model.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

    }

    public void disposeFragment() {
        disposableObserver.dispose();
    }
}
