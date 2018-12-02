package com.example.appjo.contactsapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = MessageListEntity.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MessageListDao messageListDao();
        private static AppDatabase mInstance;

        public synchronized static AppDatabase getDatabase(final Context context){
            if (mInstance == null){
                synchronized (AppDatabase.class) {
                    if (mInstance == null){
                        mInstance = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "otp_verified_contacts").build();
                    }
                }
            }
            return mInstance;
    }
}
