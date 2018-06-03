package com.example.vladimir.contactreader.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.AppDataBase;
import com.example.vladimir.contactreader.Contact;
import com.example.vladimir.contactreader.ContactDao;
import com.example.vladimir.contactreader.presenter.ContactPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactLoad {
    private static final String TAG = "TAG";
    private final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
    private Context context;
    private ContactPresenter presenter;
    private Cursor cursor;


    public ContactLoad(Context context, ContactPresenter contactPresenter) {
        this.context = context;
        this.presenter = contactPresenter;
    }

    public Observable<List<String>> getContacts() {

        return Observable.create(emitter -> {
            ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
            cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, null);
            Log.d(TAG, "cursor loadint contact");
            cursor.moveToFirst();
            List<Contact> myList = new ArrayList<>();
            while (!cursor.isAfterLast()) {
                myList.add(new Contact(Long.parseLong(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))),
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)),
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)), null, null, null, null, null, null, null));
                cursor.moveToNext();
            }
            cursor.close();
            AppDataBase db = App.getInstance().getDataBase();
            ContactDao contactDao = db.contactDao();
            Log.d(TAG, "create database");
            List<Contact> listContactfromDB = contactDao.getAll();

            if (listContactfromDB.size() != 0) {
                contactDao.delete(listContactfromDB);
                contactDao.insert(myList);
            } else {
                contactDao.insert(myList);
            }

            List<Contact> listContactfromDBnew = contactDao.getAll();
            Log.d(TAG, "list size = " + listContactfromDB.size());
            List<String> listDisplayName = new ArrayList<>();
            for (int i = 0; i < listContactfromDBnew.size(); i++) {
                listDisplayName.add(listContactfromDBnew.get(i).getDisplayName());

            }
            emitter.onNext(listDisplayName);
            emitter.onComplete();
            DetailsModel detailsModel = new DetailsModel(context);
            detailsModel.getDeatailContact()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<String>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<String> strings) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        });
    }
}
