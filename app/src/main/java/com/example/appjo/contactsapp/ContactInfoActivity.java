package com.example.appjo.contactsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appjo.contactsapp.Fragments.ContactInfoFragment;
import com.example.appjo.contactsapp.Fragments.SendMessageFragment;

public class ContactInfoActivity extends AppCompatActivity implements ContactInfoFragment.OnClickSendMessage{
    private String first;
    private String last;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        first = getIntent().getStringExtra("first");
        last = getIntent().getStringExtra("last");
        phone = getIntent().getStringExtra("phone");
        //creating a bundle to pass data to fragment
        Bundle bundle = new Bundle();
        bundle.putString("first", first);
        bundle.putString("last", last);
        bundle.putString("phone", phone);
        ContactInfoFragment fragment = new ContactInfoFragment();
        fragment.setArguments(bundle);
        //setting fragment manager to start transaction
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, "ContactInfoFragment").commit();
    }

    @Override
    public void onClickSendMessage(String name, String phone) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("phone", phone);
        SendMessageFragment fragment = new SendMessageFragment();
        fragment.setArguments(bundle);
        //setting fragment manager to start transaction
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, "SendMessageFragment").addToBackStack(null).commit();
    }
}
