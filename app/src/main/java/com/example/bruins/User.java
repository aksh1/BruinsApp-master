package com.example.bruins;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {

    private String mUsername;
    private String mEmail;
    private String mPassword;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    DatabaseReference mDatabaseReference;

    public User(){

    }

    public User(String email, String username, String password) {
        mUsername = username;
        mEmail = email;
        mPassword = password;

        String regx = "@.";
        char[] ca = regx.toCharArray();
        for (char c : ca) {
            email = email.replace(""+c, "");
        }

        mDatabaseReference = mDatabase.child(email);
        mDatabaseReference.child("email").setValue(mEmail);
        mDatabaseReference.child("username").setValue(mUsername);
        mDatabaseReference.child("password").setValue(mPassword);

    }


    public String getEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }



    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getUsername() {
        return String.valueOf(mDatabaseReference.child("username"));
    }

    public void setUsername(String name) {
        mUsername = name;
    }
}