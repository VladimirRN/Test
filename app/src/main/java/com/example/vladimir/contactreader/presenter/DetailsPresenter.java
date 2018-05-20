package com.example.vladimir.contactreader.presenter;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.model.DetailsModel;
import com.example.vladimir.contactreader.view.DetailsView;

@InjectViewState
public class DetailsPresenter extends MvpPresenter<DetailsView> {

    public DetailsModel detailsModel;
    public DetailsPresenter(Context context, LoaderManager loaderManager) {
        this.detailsModel = new DetailsModel(context, loaderManager, this);
    }

    public void getDataName(String name, String surname) {
        getViewState().setDetailsName(name, surname);
    }

    public void getDataPhone(String phone){
        getViewState().setDetailsPhone(phone);
    }

    public void getDataEmail(String email){
        getViewState().setDetailsEmail(email);
    }

    public void getKeyItem(String key) {
        detailsModel.putKey(key);
    }
}
