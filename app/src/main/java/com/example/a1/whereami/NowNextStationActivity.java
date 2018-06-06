package com.example.a1.whereami;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NowNextStationActivity extends AppCompatActivity {
    TextView nowStation, nextStation ;
    String carno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_and_next_station_layout);

        nowStation = findViewById(R.id.nowstation);
        nextStation = findViewById(R.id.nextstation);
        Intent intent = getIntent();
        carno = intent.getStringExtra("carno");



    }
}
