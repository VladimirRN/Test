package com.example.vladimir.contactreader.model;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.model.db.AppDataBase;
import com.example.vladimir.contactreader.model.db.Contact;
import com.example.vladimir.contactreader.model.db.ContactDao;
import com.example.vladimir.contactreader.presenter.DetailsPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailsRead {
    public DetailsPresenter detailsPresenter;

    public DetailsRead(DetailsPresenter detailsPresenter) {
        this.detailsPresenter = detailsPresenter;
    }

    public void getContactByKey(Long key) {
        AppDataBase dataBase = App.getInstance().getDataBase();
        ContactDao contactDao = dataBase.contactDao();
        contactDao.getContactById(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Contact>() {
                    @Override
                    public void onSuccess(Contact contact) {
                        detailsPresenter.getDataEmail(contact.getEmail());
                        detailsPresenter.getDataPhone(contact.getPhone());
                        detailsPresenter.getDataName(contact.getName(), contact.getSurname());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }
}
