package com.example.bruins;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;


import java.util.List;

public class MainStaff extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        new FirebaseDatabaseHelper().readStaff(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Staff> staff, List<String> keys) {
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
