package com.example.bruins;

import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Objects;


public class FragmentTwo extends Fragment {

    Handler handler;
    Runnable runnable;
    private TextView txtTimerDay, txtTimerHour, txtTimerMinute, txtTimerSecond;
    private TextView tvEvent;
    IntentFilter s_intentFilter;
    TextView tdate;

    public FragmentTwo() {
        // Required empty public constructor

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tdate = (TextView) Objects.requireNonNull(getView()).findViewById(R.id.textView1);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ss a");
                                SimpleDateFormat minus = new SimpleDateFormat("hh-mm-ss a");
                                String dateString = sdf.format(date);
                                String futureDate = minus.format(date);
                                tdate.setText(futureDate);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
//        t.start();
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tdate.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tdate.setText("done!");
            }
        }.start();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_two, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        txtTimerDay = (TextView) getView().findViewById(R.id.txtTimerDay);
        txtTimerHour = (TextView) getView().findViewById(R.id.txtTimerHour);
        txtTimerMinute = (TextView) getView().findViewById(R.id.txtTimerMinute);
        txtTimerSecond = (TextView) getView().findViewById(R.id.txtTimerSecond);
        tvEvent = (TextView) getView().findViewById(R.id.tvhappyevent);

    }

    }

