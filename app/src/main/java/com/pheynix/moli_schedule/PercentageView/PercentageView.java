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

import com.pheynix.moli_schedule.R;


public class PercentageView extends Fragment {
    private ImageView bg;
    private ImageView front;
    private GradientDrawable drawable_bg;
    private GradientDrawable drawable_front;
    private int height = 100;
    private int width = 10;
    private int temp_percentage;


    public PercentageView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_percentage_view, container, false);
        bg = (ImageView) view.findViewById(R.id.percentage_view_bg);
        front = (ImageView) view.findViewById(R.id.percentage_view_front);

        getWidthAndHeight();

        //0x + ?? + FFFFFF : 0x + 透明度 ＋ rgb颜色
        setColor(0xffF47537);

        return view;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 1:
                    front.setLayoutParams(new RelativeLayout.LayoutParams(width * temp_percentage / 100, height));
                    break;
            }

            super.handleMessage(msg);
        }
    };

    public void setPercentage(final int percentage){


        //非100%时，左边有5dp的倒角，右边无
        //非100%时，左边有5dp的倒角，右边有
        if (percentage != 100){
            float[] floats = {
                    (float) DimensionUtil.dpToPx(5), (float) DimensionUtil.dpToPx(5),
                    (float) DimensionUtil.dpToPx(0), (float) DimensionUtil.dpToPx(0),
                    (float) DimensionUtil.dpToPx(0), (float) DimensionUtil.dpToPx(0),
                    (float) DimensionUtil.dpToPx(5), (float) DimensionUtil.dpToPx(5)};

            drawable_front.setCornerRadii(floats);
        }else {
            drawable_front.setCornerRadius(DimensionUtil.dpToPx(5));
        }



        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0 ; i < percentage+1 ; i++){
                    try {
                        temp_percentage = i;
                        Thread.sleep(10);
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
    public void setColor(int color){

        drawable_bg = (GradientDrawable) bg.getBackground();
        drawable_bg.setColor(color);
        drawable_bg.setAlpha(100);


        drawable_front = (GradientDrawable) front.getBackground();
        drawable_front.setColor(color);

    }

    //应该添加public static final int MATCH_PARENT;
    public void setWidth(int dp){
        bg.setLayoutParams(new RelativeLayout.LayoutParams(DimensionUtil.dpToPx(dp),height));
    }

    public void setHeight(int dp){
        bg.setLayoutParams(new RelativeLayout.LayoutParams(width,DimensionUtil.dpToPx(dp)));
        front.setLayoutParams(new RelativeLayout.LayoutParams(width,DimensionUtil.dpToPx(dp)));
    }


    private void getWidthAndHeight(){
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
}
