package com.example.vladimir.contactreader.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.model.DetailsRead;
import com.example.vladimir.contactreader.view.DetailsView;

@InjectViewState
public class DetailsPresenter extends MvpPresenter<DetailsView> {

    public DetailsRead details;

    public DetailsPresenter() {
        this.details = new DetailsRead(this);
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

        public void getKeyItem (Long key){
            details.getContactByKey(key);
        }

    }



