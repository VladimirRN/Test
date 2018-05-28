package com.example.vladimir.contactreader.model;

import android.app.Application;
import android.arch.persistence.room.util.StringUtil;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.AppDataBase;
import com.example.vladimir.contactreader.Contact;
import com.example.vladimir.contactreader.ContactDao;
import com.example.vladimir.contactreader.presenter.DetailsPresenter;
import com.example.vladimir.contactreader.view.DetailsModelInterface;

import java.util.List;

public class Details implements DetailsModelInterface {
    public DetailsPresenter detailsPresenter;

    public Details(DetailsPresenter detailsPresenter) {
        this.detailsPresenter = detailsPresenter;
    }

    @Override
    public void putKey(int key) {

        AppDataBase dataBase = App.getInstance().getDataBase();
        ContactDao contactDao = dataBase.contactDao();
        List<Contact> listContact = contactDao.getAll();
        String name = listContact.get(key -1).getName();
        String surname = listContact.get(key -1).getSurname();
        String email = listContact.get(key -1).getEmail();
        String phone = listContact.get(key -1).getPhone();
        detailsPresenter.getDataEmail(email);
        detailsPresenter.getDataPhone(phone);
        detailsPresenter.getDataName(name, surname);
    }
}
