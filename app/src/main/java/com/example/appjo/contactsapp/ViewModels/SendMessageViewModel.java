package com.example.appjo.contactsapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.appjo.contactsapp.Repositories.ContactRepository;

public class SendMessageViewModel extends AndroidViewModel {
    private ContactRepository contactRepo;
    private MutableLiveData<String> status;

    public SendMessageViewModel(@NonNull Application context){
        super(context);
        this.contactRepo = ContactRepository.getInstance(context);
    }

    public MutableLiveData<String> sendMessage(String name, String body, String recipient){
        status = contactRepo.sendOtpMessage(name, body, recipient);
        return status;
    }

}
