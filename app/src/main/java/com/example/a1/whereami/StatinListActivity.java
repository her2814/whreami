package com.example.a1.whereami;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;

import java.util.ArrayList;

public class StatinListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StatinListAdapter adapter;
    ArrayList<Station> stations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_metro_station_list);

        recyclerView = findViewById(R.id.stationList);
        setData();
        setRecyclerView();
        Log.e("스테이션",stations.get(0).getBusstopname());
    }
    void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StatinListAdapter(this,stations);
        Log.e("카운트", String.valueOf(adapter.getItemCount()));
        recyclerView.setAdapter(adapter);
    }

    void setData(){
        //삽입될코드
        //테스트 코드
        Station station = new Station(1234,4567,"47896","4568");
        stations.add(station);
        stations.add(station);
        stations.add(station);
        stations.add(station);
    }
}
