package com.example.vladimir.contactreader.model;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.model.db.AppDataBase;
import com.example.vladimir.contactreader.model.db.Contact;
import com.example.vladimir.contactreader.model.db.ContactDao;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

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
                .subscribe(new Consumer<Contact>() {
                    @Override
                    public void accept(Contact contact) throws Exception {
                        onDetailsListener.getDataEmail(contact.getEmail());
                        onDetailsListener.getDataName(contact.getName(), contact.getSurname());
                        onDetailsListener.getDataPhone(contact.getPhone());
                        if (contact.getLat() != null) {
                            onDetailsListener.getLat(contact.getLat());
                        } else {
                            onDetailsListener.getLat(0);
                        }
                        if (contact.getLng() != null) {
                            onDetailsListener.getLng(contact.getLng());
                        } else {
                            onDetailsListener.getLng(0);
                        }
                    }
                });
    }
}
