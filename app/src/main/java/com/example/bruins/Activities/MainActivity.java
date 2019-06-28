package com.example.bruins.Activities;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bruins.ImageAdapter;
import com.example.bruins.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import static com.example.bruins.Activities.SplashActivity.uploads;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private SwipeRefreshLayout swipeView;

    private ProgressBar mProgressCircle;

    boolean firstStart;
    boolean firstStartFromActivity = false;
    public static String version;

    private boolean refreshToggle;

    private Handler handler;


    private DrawerLayout drawer;

    public static final String PATH = "uploads";


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstStartFromActivity = getIntent().getBooleanExtra("First Launch", false);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        firstStart = prefs.getBoolean("firstStart", true);
        if (firstStartFromActivity) {
            firstStart = true;
        }
        if (firstStart) {
            showStartDialog();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                version();
                return true;
            }
        });

        toolbar.inflateMenu(R.menu.main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);
        swipeView = findViewById(R.id.swipe);

        swipeView.setOnRefreshListener(this);

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {


                swipeView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                    }
                }, 500);
            }

            ;
        };


        mAdapter = new ImageAdapter(MainActivity.this, uploads);
        mRecyclerView.setAdapter(mAdapter);
        mProgressCircle.setVisibility(View.INVISIBLE);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StaffActivity.class);
                if (firstStart || firstStartFromActivity) intent.putExtra("First Launch", true);
                startActivity(intent);

            }
        });

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        version();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calendar) {
            Snackbar.make(drawer, "Coming Soon!", Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.nav_bellSchedule) {
            Intent intent = new Intent(MainActivity.this, MainSchedule.class);
            startActivity(intent);
        } else if (id == R.id.nav_clubs) {
            Intent clubsIntent = new Intent(getApplicationContext(), ClubsActivity.class);
            startActivity(clubsIntent);
            mProgressCircle.setVisibility(View.GONE);
        } else if (id == R.id.nav_council) {
            Snackbar.make(drawer, "Available When Elections Begin!", Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
            mProgressCircle.setVisibility(View.GONE);
        } else if (id == R.id.nav_post) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            if (firstStartFromActivity || firstStart) intent.putExtra("First Launch", true);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void showStartDialog() {
        if (!firstStartFromActivity) {

            new AlertDialog.Builder(this)
                    .setTitle("Welcome to the Official SCHS Bruins App!")
                    .setMessage("With this app, you will be able to receive school updates, email teachers," +
                            " view the bell schedule which includes a live countdown of when the next class starts, and more! If you are a " +
                            "club president, make sure to apply for an account to post updates on the home screen!")
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

    private void version() {
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new AlertDialog.Builder(this)
                .setTitle("Version")
                .setMessage(String.valueOf(version))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
        Log.d("DIALOG", "SHOWED");
    }

    @Override
    public void onRefresh() {

        swipeView.postDelayed(new Runnable() {

            @Override
            public void run() {
                swipeView.setRefreshing(true);
                handler.sendEmptyMessage(0);
            }
        }, 1000);
    }
}
