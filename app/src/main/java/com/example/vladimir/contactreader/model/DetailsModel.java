package com.example.vladimir.contactreader.model;

import android.arch.persistence.room.util.StringUtil;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.AppDataBase;
import com.example.vladimir.contactreader.Contact;
import com.example.vladimir.contactreader.ContactDao;
import com.example.vladimir.contactreader.presenter.ContactPresenter;
import com.example.vladimir.contactreader.presenter.DetailsPresenter;
import com.example.vladimir.contactreader.view.DetailsModelInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DetailsModel implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "TAG" ;
    private final String SORT_ORDER = ContactsContract.Data.MIMETYPE;
    private  final String SELECTION =  ContactsContract.Data.LOOKUP_KEY + " = ?";  //" IN (?)";" IN (?,?,?)
    private  String tmp =  ContactsContract.Data.LOOKUP_KEY + " = ?";  //" IN (?)";" IN (?,?,?)

    //private final String SELECTION = "lookupKey =?";
    private final String[] PROJECTION = {
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Email.DATA,
            ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME};
    //private static final String SELECTION = ContactsContract.Data.LOOKUP_KEY + " = ?";
   // private final ContactPresenter contactPresenter;
    private Context context;
    private LoaderManager loaderManager;
    private String[] selectionArgs = {""};
    private ArrayList<String> list;
    private AppDataBase db;
    private int id;
    //private ContactDao contactDao = db.contactDao();

    public DetailsModel(Context context, LoaderManager loaderManager) {
        this.context = context;
        this.loaderManager = loaderManager;
        //loaderManager.initLoader(1, null, this);
    }

    public List<String> createListId(){
        Log.d(TAG, "createListId");
        AppDataBase db = App.getInstance().getDataBase();
        ContactDao contactDao = db.contactDao();
        List<String> listd = new ArrayList<>();
        //List<Long>
        List<Contact> listContact = contactDao.getAll();
        for (int i = 0; i < listContact.size(); i++) {
            Long idd = listContact.get(i).getId();
            listd.add(String.valueOf(idd));
           // Log.d(TAG, idd + "");
            //return  listd;
        }
        Log.d(TAG, "list long size " + listd.size());
        //String[] arr = list.toArray(new String[list.size()]);
        selectionArgs = listd.toArray(new String[listd.size()]);
        return listd;
    }

//    public void initloader(int id, Bundle arg, LoaderManager.LoaderCallbacks<Object> callback){
//        List<Long> idList = createListId();
//        for (int i = 0; i < idList.size() ; i++) {
//               long iddd = idList.get(i);
//               loaderManager.initLoader((int) iddd, null,this);
//        }
//        //loaderManager.initLoader(1, null, this);
//    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "massiv oncreateloader " + Arrays.toString(selectionArgs));
        Log.d(TAG, "oncreate loader selection string =  " + SELECTION);
        //tring where = ContactsContract.Contacts._ID + " IN (?)";
        return new CursorLoader(
                context,
                ContactsContract.Data.CONTENT_URI,
                PROJECTION,
                SELECTION,
               // null,
                selectionArgs,
//                null,
                SORT_ORDER

        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
       // ArrayList<String> list = new ArrayList<>();
        AppDataBase db = App.getInstance().getDataBase();
        Log.d(TAG, "Load finish " + cursor.getCount());
        ContactDao contactDao = db.contactDao();
//        List<Long> listId = new ArrayList<>();
       List<Contact> listContact = contactDao.getAll();
//        for (int i = 0; i < listContact.size() ; i++) {
//            long x = listContact.get(i).getId();
//            listId.add(x);
//
//        }
        //List<String> phoneList = new ArrayList<>();
        int idLoad = loader.getId();
        long x = listContact.get(idLoad).getId();
        Log.d(TAG, "id contact in loadfinish = " + x);
        if (cursor.moveToFirst()) {
            do {
                String mime = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.Data.MIMETYPE));
                switch (mime) {
                    case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                        String surname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                        //detailsPresenter.getDataName(name, surname);
                        Log.d(TAG, "name and surname = " + name +" " + surname);
                        contactDao.updateName(name, (int) x);
                        contactDao.updateSurname(surname, (int) x);
                        //list.add(name);

                        //list.add(surname);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
//                        if (cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) == null) {
//                            String number = "";
//                        }
                        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d(TAG, "number= " + number);
                       // phoneList.add(number);
                        contactDao.updatePhone(number, (int) x);
//                        Contact contact = contactDao.getById(id);
//                        Log.d(TAG, "phone from db " + contact.getPhone());
                        //detailsPresenter.getDataPhone(number);
                        break;
                    case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                        String address = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                        //detailsPresenter.getDataEmail(address);
                        contactDao.updateEmail(address, (int) x);
                        Log.d(TAG,"address =" + address);
//                        Contact contact = contactDao.getById(id);
//                        Log.d(TAG, "phone from db " + contact.getEmail());
                        //list.add()
                        break;

                }

            } while (cursor.moveToNext());

        }
        onLoadCallback(loader.getId());

//        List<Contact> listContact = contactDao.getAll();
//        List<Long> listId = new ArrayList<>();
//        for (int i = 0; i < listContact.size(); i++) {
//            String idd = listContact.get(i).getLookupKey();
//            Long d = listContact.get(i).getId();
//            selectionArgs[0] = idd;//listContact.get(i).getLookupKey();
//            //Log.d(TAG, "список ид = "+ d);
//            loaderManager.restartLoader(i, null, this);
//        }


//        Contact contact = contactDao.getById(id);
//        Log.d(TAG, "phone from db " + contact.getPhone() + " email " + contact.getEmail()
//        + " name " + contact.getName() + " surname " + contact.getSurname());


//        List<Contact> myList = new ArrayList<>();
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            myList.add(new Contact(cursor.getPosition(),cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)),
//                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME))));
//            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
//            String surname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
//Log.d(TAG, name + " " + surname);
//cursor.moveToNext();
//        }
//        cursor.close();
//        AppDataBase db = App.getInstance().getDataBase();
//        ContactDao contactDao = db.contactDao();
//        contactDao.insert(myList);
//        Log.d(TAG, "create database");
//        List<Contact> list = contactDao.getAll();
//        Log.d(TAG, "list size = " + list.size());
//        for(int i = 0; i < myList.size(); i++) {
//            System.out.println(myList.get(i));
//        }

//        for (int i = 0; i < listId.size() ; i++) {
//            long id = listId.get(i);
//            String phone = phoneList.get(i);
//            Log.d(TAG, "phone is finish load = " + phone);
//            contactDao.updatePhone(phone, (int) id);
//        }
//        List<Contact> listAfterFinishLoad = contactDao.getAll();
//        for (int i = 0; i < listAfterFinishLoad.size(); i++) {
//            String phone = listAfterFinishLoad.get(i).getPhone();
//            Log.d(TAG, "phone after load = " + phone);
//
//        }
    }

    public void onLoadCallback (int idLoader) {
        Log.d(TAG, "id Loader = " + idLoader);
        AppDataBase db = App.getInstance().getDataBase();
        ContactDao contactDao = db.contactDao();
        List<Contact> listContact = contactDao.getAll();
        if (idLoader < listContact.size()-1){
            int id = idLoader + 1;
            //Contact contact = contactDao.getById(idLoader);
            String key = listContact.get(id).getLookupKey();
            selectionArgs[0] = key;
            loaderManager.restartLoader(id, null, this);
        } else {
            List<Contact> newListContact = contactDao.getAll();
            for (int i = 0; i < newListContact.size(); i++) {
                String phone = newListContact.get(i).getPhone();
                String l = newListContact.get(i).getLookupKey();
                String email = newListContact.get(i).getEmail();
                String name = newListContact.get(i).getName();
                Log.d(TAG, "phone from db = " + phone + " lookyp = " + l + "email  = " + email  + " " + name);
            }
        }
    }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

//    @Override
//   public void putKey(int key) {
////
//        AppDataBase db = App.getInstance().getDataBase();
//        ContactDao contactDao = db.contactDao();
//       Contact contact = contactDao.getById(key);
//       //createListId();
////       Log.d(TAG, "key in getByID " + key);
//       //String name = contact.getName();
//       ///String surname = contact.getSurname();
//      // detailsPresenter.getDataName(name, surname);
//       //id = key;
//        List<String> listd = new ArrayList<>();
//        List<Contact> listContact = contactDao.getAll();
//        List<Long> listId = new ArrayList<>();
//        Log.d(TAG, "list size =  " + listContact.size());
//
//        for (int i = 0; i < listContact.size(); i++) {
//            String idd = listContact.get(i).getLookupKey();
//            Long d  = listContact.get(i).getId();
//            //selectionArgs[0] = idd;//listContact.get(i).getLookupKey();
//            //Log.d(TAG, "список ид = "+ d);
//
////            try {
////                TimeUnit.SECONDS.sleep(1);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//           // loaderManager.restartLoader(i, null, this);
//
//            listId.add(d);
//            listd.add(idd);
//            // Log.d(TAG, idd + "");
//            //return  listd;
//        }
//       //selectionArgs = listd.toArray(new String[listd.size()]);
//        //Log.d(TAG, "massiv " + Arrays.toString(selectionArgs));
//        String idd = listContact.get(0).getLookupKey();
//       //selectionArgs[0] = contact.getLookupKey();
//        selectionArgs[0] = idd;
//        String or = " OR ";
//        //Log.d(TAG, "massiv " + Arrays.toString(selectionArgs));
////        for (int i = 0; i <selectionArgs.length -1 ; i++) {
////            SELECTION += or + tmp;
////        }
////        for (int i = 0; i <listId.size() ; i++) {
////            selectionArgs[0] = contact.getLookupKey();
////
////        }
//        loaderManager.initLoader(0, null, this);
//    }
    public void startLoader() {

        AppDataBase db = App.getInstance().getDataBase();
        ContactDao contactDao = db.contactDao();
        //Contact contact = contactDao.getById(key);
        //createListId();
//       Log.d(TAG, "key in getByID " + key);
        //String name = contact.getName();
        ///String surname = contact.getSurname();
        // detailsPresenter.getDataName(name, surname);
        //id = key;
        List<String> listd = new ArrayList<>();
        List<Contact> listContact = contactDao.getAll();
        List<Long> listId = new ArrayList<>();
        Log.d(TAG, "list size =  " + listContact.size());

        for (int i = 0; i < listContact.size(); i++) {
            String idd = listContact.get(i).getLookupKey();
            Long d  = listContact.get(i).getId();
            //selectionArgs[0] = idd;//listContact.get(i).getLookupKey();
            //Log.d(TAG, "список ид = "+ d);

//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            // loaderManager.restartLoader(i, null, this);

            listId.add(d);
            listd.add(idd);
            // Log.d(TAG, idd + "");
            //return  listd;
        }
        //selectionArgs = listd.toArray(new String[listd.size()]);
        //Log.d(TAG, "massiv " + Arrays.toString(selectionArgs));
        String idd = listContact.get(0).getLookupKey();
        //selectionArgs[0] = contact.getLookupKey();
        selectionArgs[0] = idd;
        String or = " OR ";
        //Log.d(TAG, "massiv " + Arrays.toString(selectionArgs));
//        for (int i = 0; i <selectionArgs.length -1 ; i++) {
//            SELECTION += or + tmp;
//        }
//        for (int i = 0; i <listId.size() ; i++) {
//            selectionArgs[0] = contact.getLookupKey();
//
//        }
        loaderManager.initLoader(0, null, this);
    }


}


