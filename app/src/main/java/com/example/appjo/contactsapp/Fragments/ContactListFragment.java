package com.example.appjo.contactsapp.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appjo.contactsapp.Adapters.ContactsAdapter;
import com.example.appjo.contactsapp.Models.Contacts;
import com.example.appjo.contactsapp.R;
import com.example.appjo.contactsapp.ViewModels.ContactListViewModel;

import java.util.List;

public class ContactListFragment extends Fragment {

    private static final String TAG = ContactListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ContactsAdapter mAdapter;
    private ContactListViewModel mViewModel;

    //creates new instance of ContactListFragment
    public static ContactListFragment newInstance(){
        return new ContactListFragment();
    }


    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_list_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(ContactListViewModel.class);
        mViewModel.init();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView = rootView.findViewById(R.id.contacts_rv);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ContactsAdapter(this.getActivity());
        mRecyclerView.setAdapter(mAdapter);
        viewModelAttach();
        return rootView;
    }

    public void viewModelAttach(){
        mViewModel.getContactsListData().observe(this, data -> {
           if (data != null){
               mAdapter.setContactData(data);
           }
        });
    }
}
