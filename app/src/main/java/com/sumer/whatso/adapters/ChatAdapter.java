package com.sumer.whatso.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.sumer.whatso.Models.Message;
import com.sumer.whatso.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{

    ArrayList<Message> msgList;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;
    public ChatAdapter(ArrayList<Message> msgList, Context context) {
        this.msgList = msgList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        if(msgList.get(position).getUid().equals(FirebaseAuth.getInstance().getUid()))
            return SENDER_VIEW_TYPE;
        else
            return RECEIVER_VIEW_TYPE;

//        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_chat_block,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.reciever_chat_block,parent,false);
            return new RecieverViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message msg = msgList.get(position);
        if(holder.getClass() == SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).smsg.setText(msg.getMessage());
//            ((SenderViewHolder)holder).stime.setText(msg.getTimestamp().toString());
        }
        else
        {
            ((RecieverViewHolder)holder).rmsg.setText(msg.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView rmsg, rtime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            rmsg = itemView.findViewById(R.id.tvRecieverMessage);
            rtime = itemView.findViewById(R.id.tvRecieverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView smsg,stime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            smsg = itemView.findViewById(R.id.tvSenderMessage);
            stime = itemView.findViewById(R.id.tvSenderTime);
        }
    }
}
