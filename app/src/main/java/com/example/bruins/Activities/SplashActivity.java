package com.example.bruins.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bruins.ImageAdapter;
import com.example.bruins.R;
import com.example.bruins.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.bruins.Activities.MainActivity.PATH;

public class SplashActivity extends AppCompatActivity {
    public static List<Upload> uploads = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        uploads = new ArrayList<>();
        final DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference(PATH);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
//                        mSplashAdapter = new ImageAdapter(MainActivity.this, mUploads);
                Collections.reverse(uploads);
                // Now we are moving to next page
                mDatabaseRef.removeEventListener(this);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
//                        mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        /* This 'finish()' is for exiting the app when back button pressed
         *  from Home page which is ActivityHome
         */
    }
}
