package com.sumer.whatso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.sumer.whatso.databinding.ActivityChatDetailBinding;

public class ChatDetailActivity extends AppCompatActivity {

    public static final String USER_ID = "userid";
    public static final String USER_NAME = "username";
    public static final String USER_PROFILE_PIC = "profilepic";
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

        String senderId = auth.getUid();
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
    }
}