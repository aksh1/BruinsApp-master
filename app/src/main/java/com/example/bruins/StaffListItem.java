package com.example.bruins;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StaffListItem extends AppCompatActivity {

    RelativeLayout layout;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_list_item);

        layout = (RelativeLayout) findViewById(R.id.constraint);
        textView = findViewById(R.id.email_txtView);


    }
}
