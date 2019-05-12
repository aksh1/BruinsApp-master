package com.example.bruins;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.FirebaseApp;

import java.util.List;

public class MainStaff extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private Context mContext;
    private RecyclerView_Config.StaffAdapter mStaffAdapter;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_main);



        constraintLayout = findViewById(R.id.constraint);

        Snackbar.make(constraintLayout,
                "Retrieving Staff Data", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();



        FirebaseApp.initializeApp(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
        new FirebaseDatabaseHelper().readStaff(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Staff> staff, List<String> keys) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new RecyclerView_Config().setConfig(mRecyclerView, MainStaff.this, staff, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });


    }


}
