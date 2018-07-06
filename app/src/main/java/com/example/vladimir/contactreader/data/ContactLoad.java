package com.example.vladimir.contactreader.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.vladimir.contactreader.app.App;
import com.example.vladimir.contactreader.app.AppDataBase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class ContactLoad implements ContactLoading {
    private static final String TAG = "TAG";
    private final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
    private final String[] PROJECTIONDETAILS = {
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Email.DATA,
            ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME};
    private final String SELECTIONDETAILS = ContactsContract.Data.LOOKUP_KEY + " = ?";
    private final AppDataBase database;
    private Context context;
    private Cursor cursor;
    private String[] selectionArgs = {""};
    private final String SORT_ORDER = ContactsContract.Data.MIMETYPE;

    @Inject
    public ContactLoad(Context context, AppDataBase appDataBase)
    {
        this.database = appDataBase;
        this.context = context;
    }

    @Override
    public Observable<List<Contact>> getContacts() {
        return Observable.create(new ObservableOnSubscribe<List<Contact>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Contact>> ObservableEmitter) throws Exception {
                try {
                    ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
                    cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                    }
                    List<Contact> myList = new ArrayList<>();
                    while (!cursor.isAfterLast()) {
                        myList.add(new Contact(Long.parseLong(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))),
                                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)),
                                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)), null, null, null, null, null, null, null, null, false));
                        cursor.moveToNext();
                    }
                    cursor.close();
                    List<Contact> listContactFromDB = database.getContactDao().getAll();
                    if (listContactFromDB.size() != 0) {

                    } else {
                        database.getContactDao().insert(myList);
                    }
                    List<Contact> newListContactfromDB = database.getContactDao().getAll();
                    ObservableEmitter.onNext(newListContactfromDB);
                } catch (NumberFormatException e) {
                    ObservableEmitter.onError(e);
                } finally {
                    ObservableEmitter.onComplete();
                }
            }
        });
    }

    @Override
    public Observable<String> getDeatailsContact(Contact contact) {
        return Observable.create(emitter -> {
            try {
                selectionArgs[0] = contact.getLookupKey();
                long id = contact.getId();
                ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
                cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, PROJECTIONDETAILS, SELECTIONDETAILS, selectionArgs, SORT_ORDER);
                if (cursor.moveToFirst()) {
                    do {
                        String mime = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.Data.MIMETYPE));
                        switch (mime) {
                            case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                                String surname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                                database.getContactDao().updateName(name, (int) id);
                                database.getContactDao().updateSurname(surname, (int) id);
                                break;
                            case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                database.getContactDao().updatePhone(number, (int) id);
                                break;
                            case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                                String address = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                                database.getContactDao().updateEmail(address, (int) id);
                                break;
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } catch (Exception e) {
                emitter.onError(e);
            } finally {
                emitter.onComplete();
            }
        });
    }
}
