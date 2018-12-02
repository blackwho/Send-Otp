package com.example.appjo.contactsapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.example.appjo.contactsapp.Database.MessageListEntity;
import com.example.appjo.contactsapp.Repositories.ContactRepository;

public class MessageSummaryViewModel extends AndroidViewModel {

    private ContactRepository contactRepo;
    private LiveData<PagedList<MessageListEntity>> messageList;
    public MessageSummaryViewModel(@NonNull Application context){
        super(context);
        this.contactRepo = ContactRepository.getInstance(context);
    }

    public void init(){
        messageList = contactRepo.getMessageList();
    }

    public LiveData<PagedList<MessageListEntity>> getMessageList(){
        return messageList;
    }
}
