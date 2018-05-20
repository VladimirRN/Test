package com.example.vladimir.contactreader.presenter;

import android.content.Context;
import android.database.Cursor;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.model.ContactModel;
import com.example.vladimir.contactreader.view.ContactView;


@InjectViewState
public class ContactPresenter extends MvpPresenter<ContactView> {

   public ContactModel model;



   // Здесь я так понимаю не должно быть Contexta.. Но как правильно подружить Loader c MVP я не совсем понял.

    public ContactPresenter(Context context, android.support.v4.app.LoaderManager loader) {
        this.model = new ContactModel(context, loader, this);
    }

    public void showContacts(Cursor cursor) {
        getViewState().showContacts(cursor);
    }
}
