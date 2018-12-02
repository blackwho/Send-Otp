package com.example.appjo.contactsapp.Adapters;

import android.arch.paging.AsyncPagedListDiffer;
import android.arch.paging.PagedList;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appjo.contactsapp.Database.MessageListEntity;
import com.example.appjo.contactsapp.R;

import java.util.Date;

public class MessageSummaryAdapter extends RecyclerView.Adapter<MessageSummaryAdapter.MessageViewHolder> {
    private Context mContext;
    private final AsyncPagedListDiffer<MessageListEntity> mDiffer
            = new AsyncPagedListDiffer(this, MESSAGE_SUMMARY_DIFF_CALLBACK);

    public MessageSummaryAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.message_summary_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        MessageViewHolder viewHolder = new MessageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageListEntity messageList = mDiffer.getItem(position);
        holder.bind(messageList);
    }

    @Override
    public int getItemCount() {
        return mDiffer.getItemCount();
    }

    public void setMessageSummaryList(PagedList<MessageListEntity> pagedList){
        mDiffer.submitList(pagedList);
        notifyDataSetChanged();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView listContactName;
        private TextView listContactDate;
        private TextView listContactOtp;

        public MessageViewHolder(View itemView){
            super(itemView);
            listContactName = itemView.findViewById(R.id.message_list_name_tv);
            listContactDate = itemView.findViewById(R.id.message_list_date_tv);
            listContactOtp = itemView.findViewById(R.id.message_list_otp_tv);
        }

        public void bind(MessageListEntity data){
            Date date = new Date(data.getTimestamp());
            listContactName.setText(data.getName());
            listContactDate.setText(String.valueOf(date));
            listContactOtp.setText(data.getOtp());
        }
    }

    public static final DiffUtil.ItemCallback<MessageListEntity> MESSAGE_SUMMARY_DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MessageListEntity>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull MessageListEntity oldItem, @NonNull MessageListEntity newItem) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldItem.getId() == newItem.getId();
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull MessageListEntity oldItem, @NonNull MessageListEntity newItem) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldItem.equals(newItem);
                }
            };
}