package com.shubham.roommatebudget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
/**
 * Created by admin on 7/9/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private ArrayList<CardDataModel> dataSet;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item;
        TextView cost;
        TextView time;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.item = (TextView) itemView.findViewById(R.id.item);
            this.cost = (TextView) itemView.findViewById(R.id.cost);
            this.time=(TextView)itemView.findViewById(R.id.time);
        }
    }
    public CustomAdapter(ArrayList<CardDataModel> data) {
        this.dataSet = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        view.setOnClickListener(DetailView.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView item = holder.item;
        TextView cost = holder.cost;
        TextView time=holder.time;
        item.setText(dataSet.get(listPosition).getItem());
        cost.setText(dataSet.get(listPosition).getCost());
        time.setText(dataSet.get(listPosition).getTime());
        // imageView.setImageResource(dataSet.get(listPosition).getImage());
        //logo.setImageBitmap(dataSet.get(listPosition).getImage());
    }
    @Override
    public int getItemCount()
    {
        return dataSet.size();
    }
}