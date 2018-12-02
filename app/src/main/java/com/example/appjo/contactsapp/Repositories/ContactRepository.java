package com.example.appjo.contactsapp.Repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appjo.contactsapp.Database.AppDatabase;
import com.example.appjo.contactsapp.Database.MessageListDao;
import com.example.appjo.contactsapp.Database.MessageListEntity;
import com.example.appjo.contactsapp.Models.Contacts;
import com.example.appjo.contactsapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ContactRepository {
    private static final String TAG = ContactRepository.class.getSimpleName();
    private MutableLiveData<List<Contacts>> contactsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> status = new MutableLiveData<>();
    private static LiveData<PagedList<MessageListEntity>> messageListData;
    private static String BASE_URL = "https://appjocontactsmsapp.herokuapp.com/sms";
    public static ContactRepository mInstance;
    private Context mContext;
    private Request request;
    private OkHttpClient mClient = new OkHttpClient();
    private MessageListDao messageListDao;

    private ContactRepository(Application application) {
        mContext = application.getApplicationContext();
        AppDatabase db = AppDatabase.getDatabase(application);
        messageListDao = db.messageListDao();
    }

    //singleton repository class
    public static ContactRepository getInstance(Application application){
        if (mInstance == null){
            mInstance = new ContactRepository(application);
        }
        return mInstance;
    }

    public LiveData<PagedList<MessageListEntity>> getMessageList(){
        messageListData = new LivePagedListBuilder<>(messageListDao.getAllTimeSortedMessages(), 10).build();
        return messageListData;
    }

    //used for sending otp to the user
    public MutableLiveData<String> sendOtpMessage(String name, String body, String recipient){
        RequestBody formBody = new FormBody.Builder()
                .add("To", recipient)
                .add("Body", body)
                .build();
        request = new Request.Builder()
                .url(BASE_URL)
                .post(formBody)
                .build();
        try {
            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.v(TAG, "failure");
                    status.postValue("failure");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.v(TAG, "Response" + response);
                    Log.v(TAG, "success");
                    status.postValue("success");
                    Calendar calendar = Calendar.getInstance();
                    Long date = calendar.getTimeInMillis();
                    MessageListEntity entity = new MessageListEntity();
                    entity.setName(name);
                    entity.setTimestamp(date);
                    entity.setOtp(body);
                    messageListDao.save(entity);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    //retrieving all contacts from the static json file.
    public MutableLiveData<List<Contacts>> getContacts(){
        String data = loadJSON();
        try {
            JSONObject object = new JSONObject(data);
            JSONArray array = object.getJSONArray("clients");
            List<Contacts> mList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                String id = item.getString("id");
                String first = item.getString("first");
                String last = item.getString("last");
                String phone = item.getString("phone");
                mList.add(new Contacts(id, first, last, phone));
            }
            contactsLiveData.setValue(mList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return contactsLiveData;
    }

    //used to load data from json file
    private String loadJSON() {
        String json = "";
        try {
            InputStream is = mContext.getAssets().open("contact-list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
