package com.example.bruins;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
//        View view = getLayoutInflater().inflate(R.layout.activity_main,null); // get reference to root activity view
//        setContentView(view);
//
//        view.setOnClickListener(new View.OnClickListener() {
//            float zoomFactor = 2f;
//            boolean zoomedOut = false;
//
//            @Override
//            public void onClick(View v) {
//                if(zoomedOut) {
//                    v.setScaleX(1);
//                    v.setScaleY(1);
//                    zoomedOut = false;
//                }
//                else {
//                    v.setScaleX(zoomFactor);
//                    v.setScaleY(zoomFactor);
//                    zoomedOut = true;
//                }
//            }
//        });
    }
}
