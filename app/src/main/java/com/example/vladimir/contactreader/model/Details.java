package com.example.vladimir.contactreader.model;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.AppDataBase;
import com.example.vladimir.contactreader.Contact;
import com.example.vladimir.contactreader.ContactDao;
import com.example.vladimir.contactreader.presenter.DetailsPresenter;
import com.example.vladimir.contactreader.view.DetailsModelInterface;

import java.util.List;

public class Details  {
    public DetailsPresenter detailsPresenter;

    public Details(DetailsPresenter detailsPresenter) {
        this.detailsPresenter = detailsPresenter;
    }

//    @Override
    public void putKey(int key) {
        AppDataBase dataBase = App.getInstance().getDataBase();
        ContactDao contactDao = dataBase.contactDao();
        List<Contact> listContact = contactDao.getAll();
        String name = listContact.get(key).getName();
        String surname = listContact.get(key).getSurname();
        String email = listContact.get(key).getEmail();
        String phone = listContact.get(key).getPhone();
        detailsPresenter.getDataEmail(email);
        detailsPresenter.getDataPhone(phone);
        detailsPresenter.getDataName(name, surname);
    }
}
