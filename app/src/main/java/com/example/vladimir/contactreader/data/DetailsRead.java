package com.example.vladimir.contactreader.data;

import com.example.vladimir.contactreader.app.AppDataBase;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class DetailsRead implements DetailsInterface {


    private final AppDataBase database;

    @Inject
    public DetailsRead(AppDataBase appDataBase) {
        this.database = appDataBase;
    }

    @Override
    public void getData(onDetailsListener onDetailsListener, Long key) {
        database.getContactDao().getContact(key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contact -> {
                    onDetailsListener.getDataEmail(contact.getEmail());
                    onDetailsListener.getDataName(contact.getName(), contact.getSurname());
                    onDetailsListener.getDataPhone(contact.getPhone());
                    onDetailsListener.getAddress(contact.getAddress());
                });
    }
}
