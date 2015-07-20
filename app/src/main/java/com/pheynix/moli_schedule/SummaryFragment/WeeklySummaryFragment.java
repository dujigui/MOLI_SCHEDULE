package com.pheynix.moli_schedule.SummaryFragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheynix.moli_schedule.Model.SummaryDailyItem;
import com.pheynix.moli_schedule.R;
import com.pheynix.moli_schedule.Util.DBUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklySummaryFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private WeeklySummaryAdapter mAdapter;
    private DBUtil dbUtil;


    public WeeklySummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary_weekly, container, false);



        initialize(view);


        return view;
    }

    private void initialize(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_weekly_summary);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        dbUtil = new DBUtil(getActivity());
        ArrayList<ArrayList<SummaryDailyItem>> rawData = queryDatabase();
        ArrayList<String> titles = getTitles(rawData);


        //图封装进ArrayList准备传送
        ArrayList<LineChartData> charts = new ArrayList<>();


        //第i张表
        for (int i = 0 ; i < rawData.get(1).size() ; i++){

            List<PointValue> points = new ArrayList<>();

            //第i张表第j个点
            for (int j = 0 ; j < 7 ; j++){
                PointValue point = new PointValue(j,rawData.get(j).get(i).getValue());
                points.add(point);
            }
            //点封装进线
            Line line = new Line(points);
            line.setColor(Color.CYAN);
            line.setFilled(true);
            line.setCubic(true);

            //线封装进图
            List<Line> lines = new ArrayList<>();
            lines.add(line);
            LineChartData data = new LineChartData(lines);

            data = addAxes(data,i);


            charts.add(data);


        }

        mAdapter = new WeeklySummaryAdapter(getActivity(),charts,titles);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<String> getTitles(ArrayList<ArrayList<SummaryDailyItem>> rawData) {

        ArrayList<String> titles = new ArrayList<>();

        for (int i = 0 ; i < rawData.get(0).size() ; i++){
            String title = rawData.get(0).get(i).getName();
            titles.add(title);
        }

        return titles;
    }

    private ArrayList<ArrayList<SummaryDailyItem>> queryDatabase() {

        ArrayList<ArrayList<SummaryDailyItem>> all = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        all.add(dbUtil.getSummaryItems(calendar));
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        all.add(dbUtil.getSummaryItems(calendar));
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
        all.add(dbUtil.getSummaryItems(calendar));
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
        all.add(dbUtil.getSummaryItems(calendar));
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
        all.add(dbUtil.getSummaryItems(calendar));
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
        all.add(dbUtil.getSummaryItems(calendar));
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
        all.add(dbUtil.getSummaryItems(calendar));

        return all;

    }


    private LineChartData addAxes(LineChartData data,int position) {

        ArrayList<Float> pointsX = new ArrayList<>();
        for (int i = 0 ; i < 7 ; i++){
            pointsX.add((float)i);
        }

        ArrayList<String> labelsX = new ArrayList<>();
        labelsX.add("日");
        labelsX.add("一");
        labelsX.add("二");
        labelsX.add("三");
        labelsX.add("四");
        labelsX.add("五");
        labelsX.add("六");

        Axis axisX = Axis.generateAxisFromCollection(pointsX,labelsX);
        data.setAxisXBottom(axisX);


        ArrayList<Float> pointsY = new ArrayList<>();

        for (int i = 0 ; i < 11 ; i++){
            pointsY.add(10f*i);
        }

        ArrayList<String> labelsY = new ArrayList<>();
        if (position == 0 || position == 1){
            for (int i = 0 ; i < 11 ; i++){
                labelsY.add(48*i +"");
            }
        }else {
            for (int i = 0 ; i < 11 ; i++){
                labelsY.add(10*i +"");
            }
        }

        Axis axisY = Axis.generateAxisFromCollection(pointsY,labelsY);
        axisY.setHasLines(true);

        data.setAxisYLeft(axisY);



        return data;
    }


}
