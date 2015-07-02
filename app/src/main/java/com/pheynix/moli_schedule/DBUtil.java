package com.pheynix.moli_schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by pheynix on 6/29/15.
 */
public class DBUtil {

    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase db;

    //构造函数
    public DBUtil(Context context,String databaseName,SQLiteDatabase.CursorFactory factory,int databaseVersion) {
        openHelper = new DatabaseOpenHelper(context,databaseName,factory,databaseVersion);
        db = openHelper.getWritableDatabase();
    }

    //构造函数
    public DBUtil(Context context,int databaseVersion) {
        openHelper = new DatabaseOpenHelper(context,databaseVersion);
        db = openHelper.getWritableDatabase();
    }

    //构造函数
    public DBUtil(Context context) {
        openHelper = new DatabaseOpenHelper(context);
        db = openHelper.getWritableDatabase();
    }


    public void insertData(Schedule schedule){
        ContentValues values = new ContentValues();
        db.insert(DatabaseOpenHelper.TABLE_NAME_SCHEDULE,null,values);

        return ;
    }

    public ArrayList<String> getAllCategoryName(){
        Cursor cursor = db.query(openHelper.TABLE_NAME_CATEGORY, new String[]{ openHelper.CATEGORY_NAME}, null, null, null, null, null);
        ArrayList<String> list = new ArrayList<>();

        while (cursor.moveToNext()){
            String s = cursor.getString(cursor.getColumnIndex(openHelper.CATEGORY_NAME));
            list.add(s);
        }

        return list;
    }

    public void addCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(openHelper.CATEGORY_NAME,category.getCategory_name());
        values.put(openHelper.TIME_TARGET,category.getTime_target());
        values.put(openHelper.TIME_SUMMARY,category.getTime_summary());
        values.put(openHelper.PERIODICITY,category.getPeriodicity());
        values.put(openHelper.TIME_CYCLE,category.getTime_cycle());

        db.insert(openHelper.TABLE_NAME_CATEGORY,null,values);

    }

    public  void addSchedule(Schedule schedule){
        ContentValues values = new ContentValues();
        values.put(openHelper.CATEGORY,schedule.getCategory());
        values.put(openHelper.DETAIL,schedule.getDetail());
        values.put(openHelper.TIME_START,schedule.getTime_start());
        values.put(openHelper.TIME_LAST,schedule.getTime_last());
        values.put(openHelper.URGENCY,schedule.getUrgency());
        values.put(openHelper.VIBRATION,schedule.isVibration());
        values.put(openHelper.VOLUME,schedule.getVolume());

        db.insert(openHelper.TABLE_NAME_SCHEDULE,null,values);
    }

    public ArrayList<Schedule> getAllSchedules(){
        ArrayList<Schedule> schedules = new ArrayList<>();
        Cursor cursor = db.query(openHelper.TABLE_NAME_SCHEDULE,
                new String[]{openHelper.CATEGORY,openHelper.DETAIL,openHelper.TIME_START
                        ,openHelper.TIME_LAST,openHelper.URGENCY,openHelper.VIBRATION,openHelper.VOLUME},
                null,null,null,null,null);
        while (cursor.moveToNext()){
            Schedule schedule = new Schedule();
            schedule.setCategory(cursor.getString(cursor.getColumnIndex(openHelper.CATEGORY)));
            schedule.setDetail(cursor.getString(cursor.getColumnIndex(openHelper.DETAIL)));
            schedule.setTime_start(cursor.getString(cursor.getColumnIndex(openHelper.TIME_START)));
            schedule.setTime_last(cursor.getString(cursor.getColumnIndex(openHelper.TIME_LAST)));
            schedule.setUrgency(cursor.getInt(cursor.getColumnIndex(openHelper.URGENCY)));
            schedule.setVibration(getVibration(cursor));//数据库保存值为1或0，有时间测试Boolean.parse是否有效
            schedule.setVolume(cursor.getInt(cursor.getColumnIndex(openHelper.VOLUME)));
            schedules.add(schedule);
        }

        return schedules;
    }

    private boolean getVibration(Cursor cursor) {

        switch (cursor.getInt(cursor.getColumnIndex(openHelper.VIBRATION))){
            case 1:
                return true;
            case 0:
                return false;
            default:
                return true;
        }
    }


    class DatabaseOpenHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "moli.db";

        private static final String TABLE_NAME_SCHEDULE = "schedule";
        private static final String UID = "_id";
        private static final String CATEGORY = "category";
        private static final String DETAIL = "detail";
        private static final String TIME_START = "time_start";
        private static final String TIME_LAST = "time_last";
        private static final String URGENCY = "urgency";
        private static final String VIBRATION = "vibration";
        private static final String VOLUME = "volume";
        private static final String CREATE_TABLE_SCHEDULE = "CREATE TABLE "+ TABLE_NAME_SCHEDULE+ " ("
                + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "//主键
                + CATEGORY + " TEXT, "//日程类别
                + DETAIL + " TEXT, "//日程详细信息
                + TIME_START + " INTEGER, "//日程开始时间
                + TIME_LAST + " INTEGER, "//日程持续时间
                + URGENCY + " INTEGER, "//日程重要紧急度，为1234
                + VIBRATION + " INTEGER, "//是否震动提示
                + VOLUME + " INTEGER)";//提示音大小1~00;


        private static final String TABLE_NAME_CATEGORY = "category";
        private static final String CATEGORY_NAME = "category_name";
        private static final String TIME_TARGET = "time_target";
        private static final String TIME_SUMMARY = "time_summary";
        private static final String PERIODICITY = "periodicity";
        private static final String TIME_CYCLE = "time_cycle";

        //db.execSQL("CREATE TABLE person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age SMALLINT)");
//        private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "+ TABLE_NAME_CATEGORY+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + CATEGORY_NAME + " TEXT, " + TIME_TARGET + " TEXT, " + TIME_SUMMARY + " TEXT, " + PERIODICITY + " TEXT, " + TIME_CYCLE + " TEXT)";
        private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "+ TABLE_NAME_CATEGORY
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY_NAME + " TEXT, "
                + TIME_TARGET + " TEXT, "
                + TIME_SUMMARY + " TEXT, "
                + PERIODICITY + " TEXT, "
                + TIME_CYCLE + " TEXT)";


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
                db.execSQL(CREATE_TABLE_SCHEDULE);
                db.execSQL(CREATE_TABLE_CATEGORY);
//                initCategory();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //构造方法传进来的int version != oldVersion时调用
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_SCHEDULE);
                db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_CATEGORY);
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
