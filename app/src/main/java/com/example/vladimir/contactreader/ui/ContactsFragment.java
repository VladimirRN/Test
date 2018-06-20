package com.example.vladimir.contactreader.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.CustomAdapter;
import com.example.vladimir.contactreader.MyDecoration;
import com.example.vladimir.contactreader.R;
import com.example.vladimir.contactreader.model.db.Contact;
import com.example.vladimir.contactreader.presenter.ContactPresenter;
import com.example.vladimir.contactreader.view.ContactView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;


public class ContactsFragment extends MvpAppCompatFragment implements
        ContactView, android.support.v7.widget.SearchView.OnQueryTextListener {


    @InjectPresenter
    ContactPresenter contactPresenter;
    @Inject
    public Provider<ContactPresenter> presenterProvider;
    @ProvidePresenter
    ContactPresenter provideContactPresenter() {
        return presenterProvider.get();
    }

    private ClickOnItem callback;
    private static final String TAG = "TAG";
    private CustomAdapter customAdapter;
    private boolean isTablet;
    private ProgressBar progressBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        App app = (App) getActivity().getApplication();
        app.getContactComponent().inject(this);
        try {
            callback = (ClickOnItem) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_layout, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler);
        progressBar = rootView.findViewById(R.id.progressBar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomAdapter.ItemClickListener itemClickListener = idItem -> {
            isTablet = getResources().getBoolean(R.bool.isTablet);
            if (isTablet) {
                getIdItemTablet(idItem);
            } else {
                getIdItemPhone(idItem);
            }
        };
        customAdapter = new CustomAdapter(itemClickListener);
        recyclerView.setAdapter(customAdapter);
        MyDecoration myDecoration = new MyDecoration(getContext());
        recyclerView.addItemDecoration(myDecoration);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void showContacts(List<Contact> contact) {
        customAdapter.setContacts(contact);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        customAdapter.filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        customAdapter.filter(newText);
        return false;
    }

    public interface ClickOnItem {
        void itemClickInTablet(long id);

        void itemCLickInPhone(long id);
    }

    public void getIdItemTablet(long id) {
        callback.itemClickInTablet(id);
    }

    public void getIdItemPhone(long id) {
        callback.itemCLickInPhone(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contactPresenter.disposeFragment();
    }
}





