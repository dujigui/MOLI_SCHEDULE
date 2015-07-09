package com.pheynix.moli_schedule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pheynix.moli_schedule.PercentageView.PercentageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryDailyFragment extends Fragment {
    private View view;


    public SummaryDailyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_summary_daily, container, false);

        final PercentageView percentageView = (PercentageView) getChildFragmentManager().findFragmentById(R.id.daily_percentage_view);

        if (((PercentageView) getChildFragmentManager().findFragmentById(R.id.daily_percentage_view)) == null){
            Log.e("pheynix","为毛");
        }

//        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                percentageView.setPercentage(progress);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        final EditText editText = (EditText) view.findViewById(R.id.et_percentage);
        Button button = (Button) view.findViewById(R.id.btn_change_percentage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                percentageView.setPercentage(Integer.parseInt(editText.getText().toString())
                percentageView.setPercentage(Integer.parseInt(editText.getText().toString()));
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        final PercentageView percentageView = (PercentageView) getFragmentManager().findFragmentById(R.id.daily_percentage_view);
//        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                percentageView.setPercentage(progress);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

    }
}
