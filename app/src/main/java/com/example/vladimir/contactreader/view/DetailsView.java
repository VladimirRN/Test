package com.example.vladimir.contactreader.view;

import com.arellomobile.mvp.MvpView;

public interface DetailsView extends MvpView {
    void setDetailsName(String name, String surname);
    void setDetailsPhone(String phone);
    void setDetailsEmail(String email);
    void setLat(double lat);
    void setLng(double lng);
}
