package com.example.vladimir.contactreader.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Contact {
    public Contact(@NonNull Long id, String displayName, String lookupKey, String name, String surname,
                   String phone, String email, Double lat, Double lng, String mimetype, String address, boolean isSelected) {
        this.id = id;
        this.displayName = displayName;
        this.lookupKey = lookupKey;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.lat = lat;
        this.lng = lng;
        this.mimetype = mimetype;
        this.address = address;
        this.isSelected = isSelected;
    }

    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "id")
    public Long id;
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
    @ColumnInfo(name = "lat")
    public Double lat;
    @ColumnInfo(name = "lng")
    public Double lng;
    @ColumnInfo(name = "mimetype")
    public String mimetype;
    @ColumnInfo(name = "address")
    public String address;
    public boolean isSelected;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLookupKey() {
        return lookupKey;
    }

    public void setLookupKey(String lookupKey) {
        this.lookupKey = lookupKey;
    }

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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
