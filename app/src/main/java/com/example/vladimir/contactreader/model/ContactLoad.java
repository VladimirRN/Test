package com.example.vladimir.contactreader.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.model.db.AppDataBase;
import com.example.vladimir.contactreader.model.db.Contact;
import com.example.vladimir.contactreader.model.db.ContactDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class ContactLoad {
    private static final String TAG = "TAG";
    private final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
    private Context context;
    private Cursor cursor;


    public ContactLoad(Context context) {
        this.context = context;
    }

    public Observable<List<String>> getContacts() {
        return Observable.create(emitter -> {
            ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
            cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, null);
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
            List<Contact> listContactfromDB = contactDao.getAll();

            if (listContactfromDB.size() != 0) {
                contactDao.delete(listContactfromDB);
                contactDao.insert(myList);
            } else {
                contactDao.insert(myList);
            }

            List<Contact> newListContactfromDB = contactDao.getAll();
            List<String> listDisplayName = new ArrayList<>();
            for (int i = 0; i < newListContactfromDB.size(); i++) {
                listDisplayName.add(newListContactfromDB.get(i).getDisplayName());
            }
            emitter.onNext(listDisplayName);
            emitter.onComplete();
        });
    }
}
