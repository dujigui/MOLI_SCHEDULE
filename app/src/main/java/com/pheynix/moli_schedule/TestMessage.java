package com.pheynix.moli_schedule;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by pheynix on 6/28/15.
 */
public class TestMessage {
    public TestMessage(Context context,String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
