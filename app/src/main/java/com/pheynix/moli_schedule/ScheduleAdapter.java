package com.pheynix.moli_schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pheynix on 6/24/15.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private Context mContext;
    private ArrayList<Schedule> schedules = new ArrayList<>();
    private Schedule currentSchedule;
    private ScheduleClickListener mListener;

    public ScheduleAdapter(Context mContext, ArrayList<Schedule> schedules,ScheduleClickListener listener) {
        this.mContext = mContext;
        this.schedules = schedules;
        mListener = listener;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.schedule_item, parent, false);
        ScheduleViewHolder scheduleViewHolder = new ScheduleViewHolder(view);
        return scheduleViewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        currentSchedule = schedules.get(position);

        holder.iv_urgency.setImageResource(getUrgencyImage());
        holder.tv_time_start.setText(getTimeStart());
        holder.tv_time_last.setText(getTimeLast());
        holder.tv_schedule_detail.setText(currentSchedule.getDetail());

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }




    //>>>>>>>>>>>>>>>>>>>>ViewHolder在此<<<<<<<<<<<<<<<<<<<<<<<<<<


    class ScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_urgency;
        TextView tv_schedule_detail, tv_time_start, tv_time_last;


        public ScheduleViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            iv_urgency = (ImageView) itemView.findViewById(R.id.iv_urgency);
            tv_schedule_detail = (TextView) itemView.findViewById(R.id.tv_schedule_detail);
            tv_time_start = (TextView) itemView.findViewById(R.id.tv_time_start);
            tv_time_last = (TextView) itemView.findViewById(R.id.tv_time_last);

        }

        @Override
        public void onClick(View v) {
            mListener.onScheduleClick(v,getPosition());
        }
    }


    //>>>>>>>>>>>>>>>>>>>>ViewHolder在此<<<<<<<<<<<<<<<<<<<<<<<<<<



    private int getUrgencyImage() {

        switch (currentSchedule.getUrgency()) {
            case 1:
                return R.drawable.ic_ring_1_1;
            case 2:
                return R.drawable.ic_ring_1_0;
            case 3:
                return R.drawable.ic_ring_0_1;
            case 4:
                return R.drawable.ic_ring_0_0;
            default:
                return R.drawable.ic_ring_1_1;
        }
    }

    private String getTimeStart() {
        String[] date = currentSchedule.getTime_start().split(" ");
        return date[0] + "年" + date[1] + "月" + date[2] + "日" + date[3] + "时" + date[4] + "分";
    }

    private String getTimeLast() {
        String[] time = currentSchedule.getTime_last().split(" ");
        if (time[0].equals(" ")) {
            return time[1] + "分";
        } else {
            return time[0] + "时" + time[1] + "分";
        }
    }
}
