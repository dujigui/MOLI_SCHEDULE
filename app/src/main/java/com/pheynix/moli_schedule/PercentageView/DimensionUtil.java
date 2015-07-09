package com.pheynix.moli_schedule.PercentageView;

import android.content.res.Resources;

/**
 * Created by pheynix on 7/9/15.
 * http://stackoverflow.com/questions/8295986/how-to-calculate-dp-from-pixels-in-android-programmatically
 */
public class DimensionUtil {

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
