package com.example.a1.whereami;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class StatinListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StatinListAdapter adapter;
    EditText stationNameEt;
    Button search_button;
    ArrayList<Station> stations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_metro_station_list);

        search_button = findViewById(R.id.searchbutton);
        stationNameEt = findViewById(R.id.stationName);
        recyclerView = findViewById(R.id.stationList);
        setRecyclerView();
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stations.clear();
                String stname = stationNameEt.getText().toString();
                StationParsing stationParsing = new StationParsing(stname, adapter);
                stationParsing.execute(stations);

            }
        });


    }
    void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StatinListAdapter(this,stations);
        Log.e("카운트", String.valueOf(adapter.getItemCount()));
        recyclerView.setAdapter(adapter);
    }
}
