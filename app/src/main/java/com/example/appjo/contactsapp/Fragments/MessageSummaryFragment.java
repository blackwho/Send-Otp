package com.example.appjo.contactsapp.Fragments;

import android.arch.lifecycle.ViewModel;
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

import com.example.appjo.contactsapp.Adapters.MessageSummaryAdapter;
import com.example.appjo.contactsapp.R;
import com.example.appjo.contactsapp.ViewModels.MessageSummaryViewModel;

public class MessageSummaryFragment extends Fragment {

    private static final String TAG = MessageSummaryFragment.class.getSimpleName();
    private MessageSummaryAdapter mAdapter;
    private MessageSummaryViewModel viewModel;

    public MessageSummaryFragment(){}

    //creates new instance of MessageSummaryFragment
    public static MessageSummaryFragment newInstance(){
        return new MessageSummaryFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.message_summary_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(MessageSummaryViewModel.class);
        viewModel.init();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        RecyclerView recyclerView = rootView.findViewById(R.id.message_summary_rv);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MessageSummaryAdapter(this.getActivity());
        recyclerView.setAdapter(mAdapter);
        viewModelAttach();
        return rootView;
    }

    private void viewModelAttach(){
        viewModel.getMessageList().observe(this, pagedList -> {
                mAdapter.setMessageSummaryList(pagedList);
                });
    }
}
