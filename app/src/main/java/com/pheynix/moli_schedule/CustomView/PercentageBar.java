package com.pheynix.moli_schedule.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;

/**
 * Created by pheynix on 7/14/15.
 */
public class PercentageBar extends View {
    private static final int DEFAULT_VIEW_WIDTH = 560;
    private static final int DEFAULT_VIEW_HEIGHT = 80;

    private float viewWidth;
    private float viewHeight;
    private float cornerRadius;
    private float value;
    private float tempValue;

    private int colorBackground;
    private int colorFront;

    private Paint paintBackground;
    private Paint paintFront;

    private boolean isInitialize = false;
    private boolean isPercentage = true;

    Rect rectText;
    String displayText;



    private void initialize(Canvas canvas){

        viewWidth = canvas.getWidth();
        viewHeight = canvas.getHeight();

        cornerRadius = 10;
        value = 0;

        colorBackground = 0xFF7DC4B7;
        colorFront = 0xFF7DC4B7;

        paintBackground = new Paint();
        paintFront = new Paint();

        paintBackground.setStyle(Paint.Style.FILL);
        paintBackground.setColor(colorBackground);
        paintBackground.setAlpha(100);
        paintBackground.setAntiAlias(true);
        paintFront.setStyle(Paint.Style.FILL);
        paintFront.setColor(colorFront);
        paintFront.setAntiAlias(true);

        rectText = new Rect();

    }



    public PercentageBar(Context context) {
        super(context);
    }

    public PercentageBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentageBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isInitialize){
            initialize(canvas);
            isInitialize = true;
        }

        //画背景
        RectF rectFBackground = new RectF(0, 0, viewWidth, viewHeight);
        canvas.drawRoundRect(rectFBackground, cornerRadius, cornerRadius, paintBackground);

        //画百分比的圆角矩形
        RectF rectFFront = new RectF(0, 0, viewWidth * (value / 100), viewHeight);
        canvas.drawRoundRect(rectFFront, cornerRadius, cornerRadius, paintFront);

        //如果未到尽头，画百分比右边的矩形
        if ((viewWidth-viewWidth*(value /100)) > cornerRadius){

            RectF rectFCorner = new RectF(viewWidth*(value /100)- cornerRadius,0,viewWidth*(value /100),viewHeight);
            canvas.drawRect(rectFCorner,paintFront);

        }



        paintFront.setTextSize(40);

        if (isPercentage){
            displayText = (int) value + "%";
        }else {
            displayText = new DecimalFormat("0.0").format((value/100)*8*60) + "m";
        }

        paintFront.getTextBounds(displayText,0,displayText.length(),rectText);



        //获得文字的尺寸


        //画文字，文字颜色由百分比的长度决定
        if (viewWidth-(viewWidth*(value /100)+5+5) > rectText.width()){

            canvas.drawText(displayText,viewWidth*(value /100)+5,viewHeight/2+rectText.height()/2,paintFront);

        }else {

            paintFront.setColor(Color.WHITE);
            canvas.drawText(displayText,viewWidth-rectText.width()-5-5,viewHeight/2+rectText.height()/2,paintFront);
            paintFront.setColor(colorFront);

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width  = getDimension(DEFAULT_VIEW_WIDTH, widthMeasureSpec);
        int height = getDimension(DEFAULT_VIEW_HEIGHT, heightMeasureSpec);

        viewWidth = width;
        viewHeight = height;

        setMeasuredDimension(width, height);
    }

    private int getDimension(int defaultDimension,int measureSpec){

        int result;

        switch (MeasureSpec.getMode(measureSpec)){
            case MeasureSpec.EXACTLY:
                result = MeasureSpec.getSize(measureSpec);
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(defaultDimension,MeasureSpec.getSize(measureSpec));
                break;
            default:
                result = defaultDimension;
                break;
        }
        return result;
    }

    public void setCornerRadius(float radius){
        cornerRadius = radius;
    }

    public void setColorFront(int color){
        colorFront = color;
    }

    public void setColorBackground(int color){
        colorBackground = color;
    }

    public void setIsPercentage(boolean isPercentage){
        this.isPercentage = isPercentage;

    }

    public void setValue(final float value){

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (double i = 0 ; i <= value ; i = i + 0.1){
                    try {

                        tempValue = (float)i;
                        Thread.sleep(5);
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            value = tempValue;
            invalidate();
            super.handleMessage(msg);
        }
    };

}
