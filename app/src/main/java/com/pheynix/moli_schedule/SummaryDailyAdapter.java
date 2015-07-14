package com.pheynix.moli_schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
//        if (viewType == 0) {
//            return new RatioViewHolder(inflater.inflate(R.layout.item_image, parent, false));
//        } else {
//            return new TimeViewHolder(inflater.inflate(R.layout.item_text, parent, false));
//        }
        if (viewType == 0){
//            PercentageView percentageView = new PercentageView();
//            View view = percentageView.getView();

        }else {

        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof TimeViewHolder) {
//            ((TimeViewHolder) holder).mTextView.setText(mTitles[position]);
//        } else if (holder instanceof RatioViewHolder) {
//            ((RatioViewHolder) holder).mTextView.setText(mTitles[position]);
//        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0){
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

        public TimeViewHolder(View itemView) {
            super(itemView);
        }
    }


    public static class RatioViewHolder extends RecyclerView.ViewHolder {
        public RatioViewHolder(View itemView) {
            super(itemView);
        }
    }
}








//public class SummaryDailyAdapter extends RecyclerView.Adapter<SummaryDailyAdapter.SummaryDailyViewHolder> {
//    private Context mContext;
//    private LayoutInflater inflater;
//
//    public SummaryDailyAdapter(Context mContext) {
//        this.mContext = mContext;
//        inflater = LayoutInflater.from(mContext);
//    }
//
//    @Override
//    public SummaryDailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        switch (viewType){
//            case 0:
//
//                break;
//            case 1:
//
//                break;
//        }
//
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(SummaryDailyViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0){
//            return 0;
//        }else {
//            return 1;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public class SummaryDailyViewHolder extends RecyclerView.ViewHolder{
//        public SummaryDailyViewHolder(View itemView) {
//            super(itemView);
//        }
//
//    }
//}
