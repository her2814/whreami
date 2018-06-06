package com.example.a1.whereami;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class DestinationStationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DestinationAdapter adapter;
    ArrayList<LineInfo> lineinfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.destination_layout);

        recyclerView = findViewById(R.id.destinationrecycler);
        setRecyclerView();
        //고려해보쇼
        Intent intent = getIntent();
        String carno = intent.getStringExtra("carno");
        String lineid = intent.getStringExtra("lineid");
        setRecyclerView();

        StationParsing stationParsing = new StationParsing(lineid, adapter);
        stationParsing.searchDestinationStop(lineinfos);
    }

    void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DestinationAdapter(this,lineinfos);
        Log.e("카운트", String.valueOf(adapter.getItemCount()));
        recyclerView.setAdapter(adapter);
    }
}
