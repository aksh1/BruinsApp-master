package com.example.bruins;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class User {
    private String mUsername;


    public User() {

    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String name) {
        mUsername = name;
    }
}