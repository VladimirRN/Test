package com.example.vladimir.contactreader.model;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.model.db.AppDataBase;
import com.example.vladimir.contactreader.model.db.ContactDao;
import com.example.vladimir.contactreader.presenter.DetailsPresenter;


import io.reactivex.android.schedulers.AndroidSchedulers;

public class DetailsRead {
    public DetailsPresenter detailsPresenter;

    public DetailsRead(DetailsPresenter detailsPresenter) {
        this.detailsPresenter = detailsPresenter;
    }

    public void getContactByKey(int key) {
        AppDataBase dataBase = App.getInstance().getDataBase();
        ContactDao contactDao = dataBase.contactDao();
        contactDao.getAllContact()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    String name = list.get(key).getName();
                    String surname = list.get(key).getSurname();
                    String email = list.get(key).getEmail();
                    String phone = list.get(key).getPhone();
                    detailsPresenter.getDataEmail(email);
                    detailsPresenter.getDataPhone(phone);
                    detailsPresenter.getDataName(name, surname);
                });
    }
}
