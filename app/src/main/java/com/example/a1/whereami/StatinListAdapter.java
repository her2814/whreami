package com.example.a1.whereami;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StatinListAdapter extends RecyclerView.Adapter<StatinListAdapter.ViewHolder> {
    Context context;
    ArrayList<Station> stations = new ArrayList<>();

    public StatinListAdapter(Context applicationContext, ArrayList<Station> stations) {
        this.context = applicationContext;
        this.stations = stations;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bus_metro_station_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Station station = stations.get(position);
        holder.busstopName.setText(station.getBusstopname());
        holder.busstopId.setText(String.valueOf(station.getBosstopId()));
        holder.distance.setText(String.valueOf(station.getDistance()));
        holder.stoptype.setText(station.getStoptype());
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView busstopId,busstopName,stoptype,distance;
        LinearLayout stationlinear;
        public ViewHolder(View itemView) {
            super(itemView);
            busstopId = itemView.findViewById(R.id.stationnum);
            busstopName = itemView.findViewById(R.id.busname);
            stoptype = itemView.findViewById(R.id.stationtype);
            distance = itemView.findViewById(R.id.distance);
            stationlinear = itemView.findViewById(R.id.stationlinear);

            stationlinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,StatinListActivity.class);
                    intent.putExtra("station",busstopName.getText().toString());

                    context.startActivity(intent);
                }
            });
        }
    }
}
