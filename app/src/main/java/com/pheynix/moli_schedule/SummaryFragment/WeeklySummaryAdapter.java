package com.pheynix.moli_schedule.SummaryFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pheynix.moli_schedule.R;

import java.util.ArrayList;

import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by pheynix on 7/19/15.
 */
public class WeeklySummaryAdapter extends RecyclerView.Adapter<WeeklySummaryAdapter.ChartViewHolder>{
    ArrayList<LineChartData> charts;
    ArrayList<String> titles;
    Context context;
    LayoutInflater inflater;

    public WeeklySummaryAdapter(Context context, ArrayList<LineChartData> charts , ArrayList<String> titles) {
        this.context = context;
        this.charts = charts;
        this.titles = titles;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ChartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ChartViewHolder(inflater.inflate(R.layout.weekly_summary_item,parent,false));

    }

    @Override
    public void onBindViewHolder(ChartViewHolder holder, int position) {
        holder.lineChartView.setLineChartData(charts.get(position));

        final Viewport v = new Viewport(holder.lineChartView.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 0;
        v.right = 7 - 1;
        holder.lineChartView.setMaximumViewport(v);
        holder.lineChartView.setCurrentViewport(v);

        holder.textView.setText(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return charts.size();
    }

    class ChartViewHolder extends RecyclerView.ViewHolder{
        LineChartView lineChartView;
        TextView textView;


        public ChartViewHolder(View itemView) {
            super(itemView);

            lineChartView = (LineChartView) itemView.findViewById(R.id.lc_weekly_summary);
            textView = (TextView) itemView.findViewById(R.id.tv_weekly_summary);


        }
    }


}
