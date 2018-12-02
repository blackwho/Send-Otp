package com.example.appjo.contactsapp.Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appjo.contactsapp.Models.Contacts;
import com.example.appjo.contactsapp.R;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private final OnSelectContactListener mCallback;
    private Context mContext;
    private List<Contacts> contactsList;
    public ContactsAdapter(Context context){
        mCallback = (OnSelectContactListener) context;
        mContext = context;
    }

    // OnSelectContactListener interface, calls a method in the host activity named onContactClick()
    public interface OnSelectContactListener{
        void onContactClick(String first, String last, String phone);
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.contact_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        ContactsViewHolder viewHolder = new ContactsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (contactsList == null){
            return 0;
        }else {
            return contactsList.size();
        }
    }

    public void setContactData(List<Contacts> data){
        if (data != null){
            contactsList = data;
            notifyDataSetChanged();
        }
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView contactNameTv;
        public ContactsViewHolder(View itemView){
            super(itemView);
            contactNameTv = itemView.findViewById(R.id.contact_name_tv);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (contactsList.get(adapterPosition) != null){
                String first = contactsList.get(adapterPosition).getFirst();
                String last = contactsList.get(adapterPosition).getLast();
                String phone = contactsList.get(adapterPosition).getPhone();
                mCallback.onContactClick(first, last, phone);
            }

        }

        public void bind(int pos){
            if (contactsList.get(pos) != null){
                String name = contactsList.get(pos).getFirst() + " " + contactsList.get(pos).getLast();
                contactNameTv.setText(name);
            }
        }
    }
}
