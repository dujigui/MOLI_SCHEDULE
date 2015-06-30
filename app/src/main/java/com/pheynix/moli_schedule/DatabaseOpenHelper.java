package com.pheynix.moli_schedule;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pheynix on 6/29/15.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "schedule.db";
    private static final String TABLE_NAME = "schedule";

    private static final String UID = "_id";
    private static final String CATEGORY = "category";
    private static final String DETAIL = "detail";
    private static final String TIME_START = "time_start";
    private static final String TIME_LAST = "time_last";
    private static final String URGENCY = "urgency";
    private static final String VIBRATION = "vibration";
    private static final String VOLUME = "volume";
    private static final String CREATE_TALBE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME
            + "（"+ UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"//主键
            + CATEGORY + " TEXT,"//日程类别
            + DETAIL + " TEXT,"//日程详细信息
            + TIME_START + " INTEGER,"//日程开始时间
            + TIME_LAST + " INTEGER,"//日程持续时间
            + URGENCY + " INTEGER,"//日程重要紧急度，为1234
            + VIBRATION + " INTEGER,"//是否震动提示
            + VOLUME + " INTEGER;";//提示音大小1~00;

    //构造方法
    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseOpenHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TALBE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //构造方法传进来的int version != oldVersion时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
