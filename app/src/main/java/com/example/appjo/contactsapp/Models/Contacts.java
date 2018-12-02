package com.example.appjo.contactsapp.Models;

//This is a model class for user contacts
public class Contacts {
    private String id;
    //first name
    private String first;
    //last name
    private String last;
    //user phone
    private String phone;

    public Contacts(String mId, String mFirst, String mLast, String mPhone){
        this.id = mId;
        this.first = mFirst;
        this.last = mLast;
        this.phone = mPhone;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getPhone() {
        return phone;
    }
}
