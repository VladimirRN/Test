package com.example.vladimir.contactreader.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.view.MainActivityView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainActivityView> {

    public void itemClickInPhone(Long key){
        getViewState().startDetailsFragmentForPhone(key);
    }

    public void itemClickInTablet(Long key) {
        getViewState().startDetailsFragmentForTablet(key);
    }
}
