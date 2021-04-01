package com.sumer.whatso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.sumer.whatso.Models.Message;
import com.sumer.whatso.adapters.ChatAdapter;
import com.sumer.whatso.databinding.ActivityChatDetailBinding;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {
    public static final String CHATS = "chats";
    public static final String USER_ID = "userid";
    public static final String USER_NAME = "username";
    public static final String USER_PROFILE_PIC = "profilepic";
    public static String senderId;
    private FirebaseDatabase database;
    private FirebaseAuth auth ;
    private ActivityChatDetailBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        senderId = auth.getUid();
        String recieverId = getIntent().getStringExtra(USER_ID);
        String userName = getIntent().getStringExtra(USER_NAME);
        String profilePic = getIntent().getStringExtra(USER_PROFILE_PIC);

        binding.tvUsername.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.hacker).into(binding.ivProfilePic);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final  ArrayList<Message> msgList = new ArrayList<>();

        final ChatAdapter adapter = new ChatAdapter(msgList,this);
        binding.rvChatsDetails.setAdapter(adapter);
        binding.rvChatsDetails.setLayoutManager(new LinearLayoutManager(this));

        final String senderRoom = senderId + recieverId;
        final String receiverRoom = recieverId+senderId;

        binding.ivSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = binding.etmEnterMessage.getText().toString();
                final Message msg = new Message(senderId,message);
                msg.setTimestamp(new Date().getTime());
                binding.etmEnterMessage.setText("");

                database.getReference().child(CHATS).child(senderRoom).push().setValue(msg)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                database.getReference().child(CHATS).child(receiverRoom).push().setValue(msg)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                            }
                        });
            }
        });
        database.getReference().child(CHATS).child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        msgList.clear();
                        for(DataSnapshot snap : snapshot.getChildren())
                        {
                            Message msg = snap.getValue(Message.class);
//                            Toast.makeText(ChatDetailActivity.this, ""+msg.getMessage(), Toast.LENGTH_SHORT).show();
                            msgList.add(msg);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}