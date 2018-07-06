package com.example.vladimir.contactreader.presentation.contact;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.data.DetailsInterface;

@InjectViewState
public class ContactPresenter extends MvpPresenter<ContactView> implements DetailsInterface.onDetailsListener {

    private DetailsInterface detailsInterface;


    public ContactPresenter(DetailsInterface detailsInterface) {
        this.detailsInterface = detailsInterface;
    }


    @Override
    public void getDataName(String name, String surname) {
        getViewState().setDetailsName(name, surname);
    }

    @Override
    public void getDataPhone(String phone) {
        getViewState().setDetailsPhone(phone);
    }

    @Override
    public void getAddress(String address) {
        getViewState().setAddress(address);
    }

    @Override
    public void getDataEmail(String email) {
        getViewState().setDetailsEmail(email);
    }

    public void getKeyItem(Long key) {
        detailsInterface.getData(this, key);

    }
}



