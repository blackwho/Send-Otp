package com.example.appjo.contactsapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.appjo.contactsapp.Models.Contacts;
import com.example.appjo.contactsapp.Repositories.ContactRepository;

import java.util.List;

public class ContactListViewModel extends AndroidViewModel {

    private ContactRepository contactRepo;
    private MutableLiveData<List<Contacts>> contactsListData;

    //initiating view model and getting an instance of repository
    public ContactListViewModel(@NonNull Application context){
        super(context);
        this.contactRepo = ContactRepository.getInstance(context);
    }

    public void init(){
        contactsListData = contactRepo.getContacts();
    }

    public MutableLiveData<List<Contacts>> getContactsListData() {
        return contactsListData;
    }
}
