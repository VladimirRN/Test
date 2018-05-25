package com.example.vladimir.contactreader;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity
public class Contact {
//    public Contact(@NonNull Long id, String displayName) {
//        this.id = id;
//        this.displayName = displayName;
//    }
//
//    public Contact(@NonNull Long id, String displayName, String name, String surname, String phone, String email, String geo, String address) {
//        this.id = id;
//        this.displayName = displayName;
//        this.name = name;
//        this.surname = surname;
//        this.phone = phone;
//        this.email = email;
//        this.geo = geo;
//        this.address = address;
//    }
//@Ignore
//    public Contact(@NonNull Long id, String displayName, String phone, String email) {
//        this.id = id;
//        this.displayName = displayName;
//        this.phone = phone;
//        this.email = email;
//    }

    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "id")
    public Long id;
//@Ignore
//    public Contact(@NonNull Long id, String displayName, String phone, String email, String mimetype) {
//        this.id = id;
//        this.displayName = displayName;
//        this.phone = phone;
//        this.email = email;
//        this.mimetype = mimetype;
//    }

//    public Contact(@NonNull Long id, String displayName) {
//        this.id = id;
//        this.displayName = displayName;
//    }

//    public Contact(@NonNull Long id, String displayName, String name, String surname, String phone, String email, String geo, String address, String mimetype) {
//        this.id = id;
//        this.displayName = displayName;
//        this.name = name;
//        this.surname = surname;
//        this.phone = phone;
//        this.email = email;
//        this.geo = geo;
//        this.address = address;
//        this.mimetype = mimetype;
//    }

    public String getLookupKey() {
        return lookupKey;
    }

    public void setLookupKey(String lookupKey) {
        this.lookupKey = lookupKey;
    }

    public Contact(@NonNull Long id, String displayName, String lookupKey, String name, String surname, String phone, String email, String geo, String address, String mimetype) {
        this.id = id;
        this.displayName = displayName;
        this.lookupKey = lookupKey;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.geo = geo;
        this.address = address;
        this.mimetype = mimetype;
    }

    @ColumnInfo(name = "displayName")
    public String displayName;
    @ColumnInfo(name = "lookupKey")
    public String lookupKey;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "surname")
    public String surname;
    @ColumnInfo(name = "phone")
    public String phone;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "geo")
    public String geo;
    @ColumnInfo(name = "address")
    public String address;
    @ColumnInfo(name = "mimetype")
    public String mimetype;



    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }




    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
