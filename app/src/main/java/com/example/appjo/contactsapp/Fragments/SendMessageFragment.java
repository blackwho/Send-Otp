package com.example.appjo.contactsapp.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.appjo.contactsapp.R;
import com.example.appjo.contactsapp.ViewModels.SendMessageViewModel;

import java.util.Random;

public class SendMessageFragment extends Fragment {
    private SendMessageViewModel viewModel;
    private EditText otpMessageEditText;
    private TextView otpTextView;
    public SendMessageFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String phone = "";
        String name = "";
        if (getArguments() != null){
            phone = getArguments().getString("phone");
            name = getArguments().getString("name");
        }
        final String contactName = name;
        final String number = phone;
        viewModel = ViewModelProviders.of(this).get(SendMessageViewModel.class);
        View rootView = inflater.inflate(R.layout.send_mesage_fragment, container, false);
        otpMessageEditText = rootView.findViewById(R.id.otp_message_ev);
        otpTextView = rootView.findViewById(R.id.otp_display_tv);
        Button sendButton = rootView.findViewById(R.id.send_sms_button);
        ImageButton imageButton = rootView.findViewById(R.id.otp_refresh_button);
        otpTextView.setText(String.valueOf(randomizeOtp()));

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    String messageBody = otpMessageEditText.getText().toString();
                    if (messageBody != null && !messageBody.equals("")){
                        String body = otpMessageEditText.getText().toString() + otpTextView.getText();
                        viewModel.sendMessage(contactName, body, number).observe(getActivity(), data -> {
                            Log.v("Check", "data" + data);
                            if (data != null){
                                if (data.equals("success")){
                                    Toast.makeText(getActivity(), "Message sent!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(), "Sending failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(getActivity(), "Please put a message", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int otp = randomizeOtp();
                otpTextView.setText(String.valueOf(otp));
            }
        });

        return rootView;
    }

    private int randomizeOtp(){
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return otp;
    }
}

