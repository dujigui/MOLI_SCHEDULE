package com.pheynix.moli_schedule.ScheduleFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheynix.moli_schedule.R;
import com.pheynix.moli_schedule.Util.DateTimeUtil;
import com.pheynix.moli_schedule.Model.Schedule;

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
//        holder.tv_time_start.setText(DateTimeUtil.longToDate(currentSchedule.getTime_start()));
        holder.tv_time_start.setText(DateTimeUtil.longToWord(currentSchedule.getTime_start()));
        holder.tv_time_last.setText(DateTimeUtil.longToTime(currentSchedule.getTime_last()));
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

            iv_urgency = (ImageView) itemView.findViewById(R.id.iv_urgency);
            tv_schedule_detail = (TextView) itemView.findViewById(R.id.tv_schedule_detail);
            tv_time_start = (TextView) itemView.findViewById(R.id.tv_time_start);
            tv_time_last = (TextView) itemView.findViewById(R.id.tv_time_last);

            iv_urgency.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.iv_urgency:
                    mListener.onUrgencyClick(v,getPosition());
                    break;
                default:
                    mListener.onScheduleClick(v,getPosition());
                    break;
            }

        }
    }


    //>>>>>>>>>>>>>>>>>>>>ViewHolder在此<<<<<<<<<<<<<<<<<<<<<<<<<<



    private int getUrgencyImage() {

        switch (currentSchedule.getStatus()){
            case 1:
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
            case 2:
                break;
            case 3:
                switch (currentSchedule.getUrgency()){
                    case 1:
                        return R.drawable.ic_done_1_1;
                    case 2:
                        return R.drawable.ic_done_1_0;
                    case 3:
                        return R.drawable.ic_done_0_1;
                    case 4:
                        return R.drawable.ic_done_0_0;
                    default:
                        return R.drawable.ic_done_1_1;
                }
            default:
                return R.drawable.ic_ring_1_1;
        }
        return R.drawable.ic_ring_1_1;
    }
}
