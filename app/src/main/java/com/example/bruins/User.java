package com.example.bruins;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class User {

    private String mUsername;
    private String mEmail;
    private String mPassword;
    private String mProfilePic = "";

    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    private DatabaseReference mDatabaseReference;
    private StorageReference storageReference;

    public User() {

    }

    public User(String email, String username, String password, String profilePic) {
        mUsername = username;
        mEmail = email;
        mPassword = password;
        mProfilePic = profilePic;


        mDatabaseReference = mDatabase.child(email);
        mDatabaseReference.child("email").setValue(mEmail);
        mDatabaseReference.child("username").setValue(mUsername);
        mDatabaseReference.child("password").setValue(mPassword);
        mDatabaseReference.child("profilePic").setValue(mProfilePic);

        storageReference = FirebaseStorage.getInstance().getReference();

        if (profilePic.equals("")) {
            storageReference.child("profilepic.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    mProfilePic = uri.toString();
                    Log.d("PROFILEPIC", mProfilePic);
                    mDatabaseReference.child("profilePic").setValue(mProfilePic);
                }
            });
        }
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

    public String getProfilePic() {
        return mProfilePic;
    }

    public void setProfilePic(String mProfilePic) {
        this.mProfilePic = mProfilePic;
    }
}