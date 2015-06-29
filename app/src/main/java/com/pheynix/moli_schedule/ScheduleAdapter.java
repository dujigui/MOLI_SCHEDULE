package com.pheynix.moli_schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pheynix on 6/24/15.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private Context mContext;
    private List<ScheduleInfo> schedules = new ArrayList<ScheduleInfo>();

    public ScheduleAdapter(Context mContext, List<ScheduleInfo> schedules) {
        this.mContext = mContext;
        this.schedules = schedules;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.schedule_item, parent,false);
        ScheduleViewHolder scheduleViewHolder = new ScheduleViewHolder(view);
        return scheduleViewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        ScheduleInfo currentSchedule = schedules.get(position);
        holder.detail.setText(currentSchedule.getDetail());
//        holder.ic_done.setImageDrawable(mContext.getResources().getDrawable(currentSchedule.getImgId()));

//        holder.ic_done.setImageResource(currentSchedule.getImgId());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder{
        TextView detail;
        ImageView ic_done;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            ic_done = (ImageView) itemView.findViewById(R.id.id_schedule_done);
            detail = (TextView) itemView.findViewById(R.id.id_schedule_info);
        }
    }
}
