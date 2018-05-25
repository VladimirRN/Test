package com.example.vladimir.contactreader.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface MainActivityView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void startDetailsFragmentForPhone(int itemKey);

    void startDetailsFragmentForTablet(int itemKey);
}
