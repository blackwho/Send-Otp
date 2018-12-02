package com.example.appjo.contactsapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appjo.contactsapp.R;

public class ContactInfoFragment extends Fragment {
    private static final String TAG = ContactInfoFragment.class.getSimpleName();
    public OnClickSendMessage mHandler;
    private String number;
    private String name;

    public interface OnClickSendMessage{
        void onClickSendMessage(String name, String phone);
    }
    public ContactInfoFragment(){}
    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mHandler = (OnClickSendMessage) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " Host Activity must implement OnClickSendMessage");
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String first = "";
        String last = "";
        String phone = "";
        if (getArguments() != null){
            first = getArguments().getString("first");
            last = getArguments().getString("last");
            phone = getArguments().getString("phone");
            number = phone;
        }
        View rootView = inflater.inflate(R.layout.contact_info_fragment, container, false);
        TextView contactInfoName = rootView.findViewById(R.id.contact_info_name_tv);
        TextView contactInfoPhone = rootView.findViewById(R.id.contact_info_phone_tv);
        Button contactInfoSendMessageButton = rootView.findViewById(R.id.contact_info_send_message);
        name = first + " " + last;
        contactInfoName.setText(name);
        contactInfoPhone.setText(phone);
        contactInfoSendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.onClickSendMessage(name, number);
            }
        });
        return rootView;
    }
}
