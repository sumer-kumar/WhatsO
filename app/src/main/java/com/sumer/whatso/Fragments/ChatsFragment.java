package com.sumer.whatso.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sumer.whatso.Models.User;
import com.sumer.whatso.R;
import com.sumer.whatso.SignInActivity;
import com.sumer.whatso.adapters.UserAdaptor;
import com.sumer.whatso.databinding.FragmentChatsBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatsFragment extends Fragment {
    public ChatsFragment() {
        // Required empty public constructor
    }
    FragmentChatsBinding binding;
    ArrayList<User> userList = new ArrayList<>();
    FirebaseDatabase database ;
//    public static final String USER = "User";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatsBinding.inflate(inflater, container, false);
        UserAdaptor adaptor = new UserAdaptor(userList,getContext());
        binding.rvChats.setAdapter(adaptor);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvChats.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();

        database.getReference().child(SignInActivity.USER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    user.setUserId(dataSnapshot.getKey());
                    userList.add(user);
                }
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}