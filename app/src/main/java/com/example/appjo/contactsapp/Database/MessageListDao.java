package com.example.appjo.contactsapp.Database;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.icu.text.Replaceable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MessageListDao {

    //inserts name, otp and date as entity
    @Insert(onConflict = REPLACE)
    void save(MessageListEntity entity);

    //queries the contact messages by sorting them in order of time
    @Query("SELECT * FROM messageList ORDER BY timestamp DESC")
    DataSource.Factory<Integer, MessageListEntity> getAllTimeSortedMessages();
}
