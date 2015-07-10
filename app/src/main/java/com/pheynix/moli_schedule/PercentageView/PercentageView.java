package com.pheynix.moli_schedule.PercentageView;


import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pheynix.moli_schedule.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PercentageView extends Fragment {
    private ImageView bg;
    private ImageView front;
    private TextView tv_left;
    private TextView tv_right;
    private GradientDrawable drawable_bg;
    private GradientDrawable drawable_front;
    private int height = 100;
    private int width = 10;
    private int temp_percentage;

    private int color_pink = 0xffE91E63;
    private int color_purple = 0xffE040FB;
    private int color_purple_deep = 0xff7C4DFF;
    private int color_indigo = 0xff536DFE;
    private int color_blue = 0xff448AFF;
    private int color_green = 0xff4CAF50;
    private int color_yellow = 0xffFFEB3B;
    private int color_orange = 0xffFF9800;
    private int color_brown = 0xff795548;
    private int color_blue_grey = 0xff607D8B;

    public static final List<Integer> colors = new ArrayList<>();


    public PercentageView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_percentage_view, container, false);
        bg = (ImageView) view.findViewById(R.id.percentage_view_bg);
        front = (ImageView) view.findViewById(R.id.percentage_view_front);
        tv_left = (TextView) view.findViewById(R.id.percentage_view_text_view_left);
        tv_right = (TextView) view.findViewById(R.id.percentage_view_text_view_right);

        for (int i = 0 ; i < 5 ; i++){

            colors.add(color_yellow);
            colors.add(color_green);
            colors.add(color_brown);
            colors.add(color_pink);
            colors.add(color_blue);
            colors.add(color_purple);
            colors.add(color_orange);
            colors.add(color_indigo);
            colors.add(color_purple_deep);
            colors.add(color_blue_grey);
        }



        getWidthAndHeight();

        //0x + ?? + FFFFFF : 0x + 透明度 ＋ rgb颜色
        setColor(0xffF47537);

        return view;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    front.setLayoutParams(new RelativeLayout.LayoutParams(width * temp_percentage / 100, height));

                    if (temp_percentage < 91) {
                        tv_right.setVisibility(View.VISIBLE);
                        tv_right.setText(temp_percentage + "%");
                        tv_left.setVisibility(View.GONE);
                    } else {
                        tv_left.setVisibility(View.VISIBLE);
                        tv_left.setText(temp_percentage + "%");
                        tv_right.setVisibility(View.GONE);
                    }

                    break;
            }

            super.handleMessage(msg);
        }
    };

    public void setPercentage(final int percentage) {


        //非100%时，左边有5dp的倒角，右边无
        //非100%时，左边有5dp的倒角，右边有
        if (percentage != 100) {
            float[] floats = {
                    (float) DimensionUtil.dpToPx(5), (float) DimensionUtil.dpToPx(5),
                    (float) DimensionUtil.dpToPx(0), (float) DimensionUtil.dpToPx(0),
                    (float) DimensionUtil.dpToPx(0), (float) DimensionUtil.dpToPx(0),
                    (float) DimensionUtil.dpToPx(5), (float) DimensionUtil.dpToPx(5)};

            drawable_front.setCornerRadii(floats);
        } else {
            drawable_front.setCornerRadius(DimensionUtil.dpToPx(5));
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < percentage + 1; i++) {
                    try {
                        temp_percentage = i;
                        Thread.sleep(20);
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

//        front.setLayoutParams(new RelativeLayout.LayoutParams(width * percentage / 100, height));
    }

    //    http://stackoverflow.com/questions/17823451/set-android-shape-color-programmatically
    public void setColor(int color) {

        drawable_bg = (GradientDrawable) bg.getBackground();
        drawable_bg.setColor(color);
        drawable_bg.setAlpha(100);


        drawable_front = (GradientDrawable) front.getBackground();
        drawable_front.setColor(color);

    }

    //应该添加public static final int MATCH_PARENT;
    public void setWidth(int dp) {
        bg.setLayoutParams(new RelativeLayout.LayoutParams(DimensionUtil.dpToPx(dp), bg.getLayoutParams().height));
    }

    public void setHeight(int dp) {
        bg.setLayoutParams(new RelativeLayout.LayoutParams(bg.getLayoutParams().width, DimensionUtil.dpToPx(dp)));
        front.setLayoutParams(new RelativeLayout.LayoutParams(bg.getLayoutParams().width, DimensionUtil.dpToPx(dp)));
    }


    private void getWidthAndHeight() {
        ViewTreeObserver viewTreeObserver = bg.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                height = bg.getMeasuredHeight();
                width = bg.getMeasuredWidth();

                return true;
            }
        });
    }


    //产生随机的颜色序列，不是很需要
    private void getColors() {
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        while (numbers.size() < 11) {
            int number = random.nextInt(10);
            if (!numbers.contains(number)) {
                numbers.add(number);
            }
        }

        for (int i = 0 ; i < numbers.size(); i++) {
            colors.add(this.colors.get(numbers.get(i)));
        }

        this.colors.clear();

        for (int i = 0 ; i < 5 ; i++){
            this.colors.addAll(colors);
        }
    }
}