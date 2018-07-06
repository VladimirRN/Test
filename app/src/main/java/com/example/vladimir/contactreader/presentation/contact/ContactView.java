package com.example.vladimir.contactreader.presentation.contact;

import com.arellomobile.mvp.MvpView;

public interface ContactView extends MvpView {
    void setDetailsName(String name, String surname);
    void setDetailsPhone(String phone);
    void setDetailsEmail(String email);
    void setAddress(String address);
}
