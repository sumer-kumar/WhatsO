package com.sumer.whatso.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.sumer.whatso.ChatDetailActivity;
import com.sumer.whatso.Models.User;
import com.sumer.whatso.R;

import java.util.ArrayList;

public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.ViewsHolder> {
    ArrayList<User> userList;
    Context context;

    public UserAdaptor(ArrayList<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_users,parent,false);
        return new ViewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewsHolder holder, int position) {
        User user = userList.get(position);

        //for image setting
        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.hacker).into(holder.image);
        //for name
        holder.name.setText(user.getUsername());
        holder.lastChat.setText("Last message");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatDetailActivity.class);
                intent.putExtra(ChatDetailActivity.USER_ID,user.getUserId());
                intent.putExtra(ChatDetailActivity.USER_PROFILE_PIC,user.getProfilePic());
                intent.putExtra(ChatDetailActivity.USER_NAME,user.getUsername());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewsHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView lastChat;

        public ViewsHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivProfilePic);
            name = itemView.findViewById(R.id.tvName);
            lastChat = itemView.findViewById(R.id.tvLastChat);

        }
    }
}
