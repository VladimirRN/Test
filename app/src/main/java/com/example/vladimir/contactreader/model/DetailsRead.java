package com.example.vladimir.contactreader.model;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.model.db.AppDataBase;
import com.example.vladimir.contactreader.model.db.ContactDao;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class DetailsRead implements DetailsInterface {


    @Inject
    public DetailsRead() {
    }

    @Override
    public void getData(onDetailsListener onDetailsListener, Long key) {
        AppDataBase dataBase = App.getInstance().getDataBase();
        ContactDao contactDao = dataBase.contactDao();
        contactDao.getContactById(key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contact -> {
                    onDetailsListener.getDataEmail(contact.getEmail());
                    onDetailsListener.getDataName(contact.getName(), contact.getSurname());
                    onDetailsListener.getDataPhone(contact.getPhone());
                    onDetailsListener.getAddress(contact.getAddress());
                });
    }
}
