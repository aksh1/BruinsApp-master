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
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.SearchManager;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView.OnQueryTextListener;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

public class MainStaff extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private RecyclerView mRecyclerView;
    private Context mContext;
    private RecyclerView_Config.StaffAdapter mStaffAdapter;
    private RelativeLayout constraintLayout;
    SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }


    public boolean onQueryTextChange(String query) {

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }


    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_main);

        final ProgressBar mProgressCircle = findViewById(R.id.progress_circle);

        mProgressCircle.setVisibility(View.VISIBLE);




        constraintLayout = findViewById(R.id.constraint);

        Snackbar.make(constraintLayout,
                "Retrieving Staff Data", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();



        FirebaseApp.initializeApp(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        new FirebaseDatabaseHelper().readStaff(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Staff> staff, List<String> keys) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new RecyclerView_Config().setConfig(mRecyclerView, MainStaff.this, staff, keys);
                mProgressCircle.setVisibility(View.INVISIBLE);
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


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private final SortedList.Callback<Staff> mCallback = new SortedList.Callback<Staff>() {

        @Override
        public void onInserted(int position, int count) {
            mStaffAdapter.notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            mStaffAdapter.notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            mStaffAdapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public int compare(Staff staff, Staff t21) {
            return 0;
        }

        @Override
        public void onChanged(int position, int count) {
            mStaffAdapter.notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Staff staff, Staff t21) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(Staff staff, Staff t21) {
            return false;
        }
    };

}


