package com.example.a1.whereami;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {
    Context context;
    ArrayList<LineInfo> lineinfos;

    public DestinationAdapter(Context context, ArrayList<LineInfo> lineinfos) {
        this.context = context;
        this.lineinfos = lineinfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.destination_info_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.stationNM.setText(lineinfos.get(position).getBstopnm());
        holder.carno.setText("");
        holder.busimage.setVisibility(View.INVISIBLE);
        if(lineinfos.get(position).getCarno()!=null) {
            holder.carno.setText(lineinfos.get(position).getCarno());
            holder.busimage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return lineinfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView stationNM;
        LinearLayout destinationLinear;
        ImageView busimage;
        TextView carno;

        public ViewHolder(View itemView) {
            super(itemView);
            stationNM = itemView.findViewById(R.id.busstationName);
            destinationLinear = itemView.findViewById(R.id.destinationLinear);
            busimage = itemView.findViewById(R.id.busicon);
            carno = itemView.findViewById(R.id.carno);

            destinationLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartDestinationVO startDestinationVO = StartDestinationVO.getInstance();
                    startDestinationVO.setDestinationStation(stationNM.getText().toString());
                    Intent intent = new Intent(context.getApplicationContext(),MainActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
