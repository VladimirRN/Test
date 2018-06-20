package com.example.vladimir.contactreader.model;

import com.example.vladimir.contactreader.App;
//import com.example.vladimir.contactreader.DetailsPresenterInterface;
import com.example.vladimir.contactreader.model.db.AppDataBase;
import com.example.vladimir.contactreader.model.db.Contact;
import com.example.vladimir.contactreader.model.db.ContactDao;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailsRead implements DetailsInterface{
   // public DetailsPresenterInterface detailsPresenterInterface;
   // private Contact contact;

  @Inject
  public DetailsRead(){
  }

    @Override
    public void getData(onDetailsListener onDetailsListener, Long key) {
        AppDataBase dataBase = App.getInstance().getDataBase();
        ContactDao contactDao = dataBase.contactDao();
        contactDao.getContactById(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Contact>() {
                    @Override
                    public void onSuccess(Contact contact) {
                        onDetailsListener.getDataEmail(contact.getEmail());
                        onDetailsListener.getDataName(contact.getName(), contact.getSurname());
                        onDetailsListener.getDataPhone(contact.getPhone());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

}
