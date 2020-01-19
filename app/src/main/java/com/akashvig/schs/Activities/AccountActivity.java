package com.akashvig.schs.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.akashvig.schs.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String account;

    private String email;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        account = getIntent().getStringExtra("Account");
        setTitle("My Profile");

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }
}


