package com.example.vladimir.contactreader.presenter;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.model.ContactLoad;
import com.example.vladimir.contactreader.view.ContactView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class ContactPresenter extends MvpPresenter<ContactView> {

    public ContactLoad model;
    public static final String TAG = "TAG";



    public ContactPresenter(Context context) {
        this.model = new ContactLoad(context, this);
        startLoadingContacts();
        getViewState().showProgress();
        Log.d(TAG, "show");
    }


    public void startLoadingContacts() {
        model.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> strings) {
                        getViewState().showContacts(strings);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        getViewState().hideProgress();
                        Log.d(TAG, "HIDE");
                    }
                });
    }
}
