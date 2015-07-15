package com.pheynix.moli_schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pheynix.moli_schedule.CustomView.PercentageBar;

import java.util.ArrayList;

/**
 * Created by pheynix on 7/10/15.
 */

public class SummaryDailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final LayoutInflater inflater;
    private final Context mContext;
    private ArrayList<SummaryDailyItem> items;

    public SummaryDailyAdapter(Context context,ArrayList<SummaryDailyItem> items) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.summary_daily_item, null);
        PercentageBar percentageBar = (PercentageBar) item.findViewById(R.id.pb_summary_daily_item_percentage);

        if (viewType == 1){
            return new PercentageViewHolder(item);
        }else {
            return new TimeViewHolder(item);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TimeViewHolder) {
            ((TimeViewHolder) holder).percentageBar.setValue(items.get(position).getValue());
            ((TimeViewHolder) holder).percentageBar.setIsPercentage(false);
            ((TimeViewHolder) holder).percentageBar.setColorBackground(0xFFF1F566);
            ((TimeViewHolder) holder).percentageBar.setColorFront(0xFFF1F566);

            ((TimeViewHolder) holder).textView.setText(items.get(position).getName());
        } else if (holder instanceof PercentageViewHolder) {
            ((PercentageViewHolder) holder).percentageBar.setValue(items.get(position).getValue());
            ((PercentageViewHolder) holder).percentageBar.setColorFront(0xFFF1F566);
            ((PercentageViewHolder) holder).percentageBar.setColorBackground(0xFFF1F566);
            ((PercentageViewHolder) holder).percentageBar.setValue(items.get(position).getValue());
            ((PercentageViewHolder) holder).textView.setText(items.get(position).getName());
        }
    }

    @Override
    public int getItemViewType(int position) {

        //TimeViewHolder
        if (position == 0 || position == 1){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public static class TimeViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private PercentageBar percentageBar;

        public TimeViewHolder(View itemView) {
            super(itemView);

            percentageBar = (PercentageBar) itemView.findViewById(R.id.pb_summary_daily_item_percentage);
            textView = (TextView) itemView.findViewById(R.id.tv_summary_daily_item_name);

            percentageBar.setIsPercentage(false);
        }
    }


    public static class PercentageViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private PercentageBar percentageBar;

        public PercentageViewHolder(View itemView) {
            super(itemView);

            percentageBar = (PercentageBar) itemView.findViewById(R.id.pb_summary_daily_item_percentage);
            textView = (TextView) itemView.findViewById(R.id.tv_summary_daily_item_name);

        }
    }
}