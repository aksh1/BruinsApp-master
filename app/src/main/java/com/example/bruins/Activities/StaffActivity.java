package com.example.bruins.Activities;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.bruins.R;
import com.example.bruins.Staff;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private StaffAdapter mAdapter;


    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private List<Staff> mStaff;

    private boolean firstStart;

    public static final String PATH = "staff";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        firstStart = getIntent().getBooleanExtra("First Launch", false);

        if (firstStart){
            showStartDialog();
        }



        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.recyclerview);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);



        mProgressCircle = findViewById(R.id.progress_circle);

        mStaff = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(PATH);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Staff staff = postSnapshot.getValue(Staff.class);
                    mStaff.add(staff);
                }
                mAdapter = new StaffAdapter(mStaff);

                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(mAdapter);

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_search){
        }else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            if(firstStart){
                intent.putExtra("First Launch", true);
            }
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showStartDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Staff Directory")
                .setMessage("This page lists all the staff of SCHS. Click on a name to email them.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}
