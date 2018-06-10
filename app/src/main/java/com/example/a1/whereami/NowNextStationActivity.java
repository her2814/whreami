package com.example.a1.whereami;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class NowNextStationActivity extends AppCompatActivity {
    TextView nowStation, nextStation ;
    String lineid;
    String carno;
    private BroadcastReceiver broadcastReceiver= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_and_next_station_layout);

        nowStation = findViewById(R.id.nowstation);
        nextStation = findViewById(R.id.nextstation);
        Intent intent = getIntent();
        carno = intent.getStringExtra("carno");
        lineid = intent.getStringExtra("lineid");
        StartDestinationVO startDestinationVO = StartDestinationVO.getInstance();
        String nextstation = startDestinationVO.getNextStation();
        String startsation = startDestinationVO.getStartStation();
        String destinationstation = startDestinationVO.getDestinationStation();

        nowStation.setText(startsation);
        nextStation.setText(nextstation);

        Log.i("원하는 정보 : ", "carno : " + carno + "" + "nextStation" + nextStation + "startstation" + startsation + "destination" + destinationstation);

        intent = new Intent(this,MoniterStationService.class);
        intent.putExtra("lineid",lineid);
        intent.putExtra("carno",carno);
        this.startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
        Log.e("프로바이더 호출","프로바이더 생성");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver();
        Log.e("프로바이더 호출","프로바이더 제거");
    }

    private void registerReceiver(){
        if(broadcastReceiver != null) return;

        final IntentFilter theFilter = new IntentFilter();
        theFilter.addAction("changeStation");

        this.broadcastReceiver = new BroadcastReceiver() {
            StartDestinationVO startDestinationVO = StartDestinationVO.getInstance();

            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals("changeStation")){
                    nowStation.setText(startDestinationVO.getStartStation());
                    nextStation.setText(startDestinationVO.getNextStation());
                }
            }
        };
        this.registerReceiver(this.broadcastReceiver,theFilter);
    }

    private void unregisterReceiver(){
        if(broadcastReceiver!=null){
            this.unregisterReceiver();
            broadcastReceiver = null;
        }
    }
}
