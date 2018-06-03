package com.example.vladimir.contactreader.view;

import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface ContactView extends MvpView {
    void showContacts(List<String> list);
    void showProgress();
    void hideProgress();
}
