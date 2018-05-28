package com.example.vladimir.contactreader.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.model.Details;
import com.example.vladimir.contactreader.model.DetailsModel;
import com.example.vladimir.contactreader.view.DetailsView;

@InjectViewState
public class DetailsPresenter extends MvpPresenter<DetailsView> {
    //public Details details;

    public Details details;

    public DetailsPresenter() {
        this.details = new Details(this);
   }

        public void getDataName (String name, String surname){
            getViewState().setDetailsName(name, surname);
        }


        public void getDataPhone (String phone){
            getViewState().setDetailsPhone(phone);
        }

        public void getDataEmail (String email){
            getViewState().setDetailsEmail(email);
        }

        public void getKeyItem ( int key){
            details.putKey(key);
        }

    }



