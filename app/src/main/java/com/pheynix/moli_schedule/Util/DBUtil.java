package com.pheynix.moli_schedule.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pheynix.moli_schedule.Item.Category;
import com.pheynix.moli_schedule.Item.Schedule;
import com.pheynix.moli_schedule.Item.SummaryDailyItem;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by pheynix on 6/29/15.
 */
public class DBUtil {

    //考虑是否需要和如何处理数据库出现的exception；
    //考虑是否需要和如何处理数据库出现的exception；
    //考虑是否需要和如何处理数据库出现的exception；
    //考虑是否需要和如何处理数据库出现的exception；
    //考虑是否需要和如何处理数据库出现的exception；

    //是否需要关闭db，openHelper
    //是否需要关闭db，openHelper
    //是否需要关闭db，openHelper

    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase db;


    //构造函数
    public DBUtil(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        openHelper = new DatabaseOpenHelper(context, databaseName, factory, databaseVersion);
        db = openHelper.getWritableDatabase();
    }


    //构造函数
    public DBUtil(Context context, int databaseVersion) {
        openHelper = new DatabaseOpenHelper(context, databaseVersion);
        db = openHelper.getWritableDatabase();
    }


    //构造函数
    public DBUtil(Context context) {
        openHelper = new DatabaseOpenHelper(context);
        db = openHelper.getWritableDatabase();
    }


    //检索所有的日程，应该改为检索昨天、今天、明天的日程；
    //或者新开一个方法实现上述功能，这个方法用来供用户检索所有日程
    public ArrayList<Schedule> getAllSchedules() {
        ArrayList<Schedule> schedules = new ArrayList<>();

        //可以null表示选择所有列
        Cursor cursor = db.query(openHelper.TABLE_NAME_SCHEDULE,
                null, null, null, null, null, openHelper.STATUS+" ASC, "+openHelper.TIME_START+" ASC ");

        while (cursor.moveToNext()) {
            Schedule schedule = new Schedule();
            schedule.setId(cursor.getInt(cursor.getColumnIndex(openHelper.UID)));
            schedule.setCategory(cursor.getString(cursor.getColumnIndex(openHelper.CATEGORY)));
            schedule.setDetail(cursor.getString(cursor.getColumnIndex(openHelper.DETAIL)));
            schedule.setTime_start(cursor.getLong(cursor.getColumnIndex(openHelper.TIME_START)));
            schedule.setTime_recorded(cursor.getLong(cursor.getColumnIndex(openHelper.TIME_RECORDED)));
            schedule.setTime_last(cursor.getLong(cursor.getColumnIndex(openHelper.TIME_LAST)));
            schedule.setUrgency(cursor.getInt(cursor.getColumnIndex(openHelper.URGENCY)));
            schedule.setVibration(getVibration(cursor));//数据库保存值为1或0，有时间测试Boolean.parse是否有效
            schedule.setVolume(cursor.getInt(cursor.getColumnIndex(openHelper.VOLUME)));
            schedule.setStatus(cursor.getInt(cursor.getColumnIndex(openHelper.STATUS)));
            schedules.add(schedule);
        }

        return schedules;
    }

    //getAllSchedules重构出来的方法
    //从数据库的数据获取震动提示的状态
    private boolean getVibration(Cursor cursor) {

        switch (cursor.getInt(cursor.getColumnIndex(openHelper.VIBRATION))) {
            case 1:
                return true;
            case 0:
                return false;
            default:
                return true;
        }

    }


    //增加日程
    public void addSchedule(Schedule schedule) {
        ContentValues values = new ContentValues();
        values.put(openHelper.CATEGORY, schedule.getCategory());
        values.put(openHelper.DETAIL, schedule.getDetail());
        values.put(openHelper.TIME_START, schedule.getTime_start());
        values.put(openHelper.TIME_RECORDED,schedule.getTime_recorded());
        values.put(openHelper.TIME_LAST, schedule.getTime_last());
        values.put(openHelper.URGENCY, schedule.getUrgency());
        values.put(openHelper.VIBRATION, schedule.isVibration());
        values.put(openHelper.VOLUME, schedule.getVolume());
        values.put(openHelper.STATUS, schedule.getStatus());//创建时状态为“未完成”

        db.insert(openHelper.TABLE_NAME_SCHEDULE, null, values);
    }


    //根据id删除一条日程
    public boolean deleteSchedule(int _id){
        long isSucceed = db.delete(openHelper.TABLE_NAME_SCHEDULE,"_id = ?",new String[]{_id+""});
        return isSucceed != -1;
    }


    //可以考虑和changeScheduleStatus合并
    //传入的schedule必须包含“_id”，使用"_id"定位数据库中的日程项
    public void alterSchedule(Schedule schedule){

        int id = schedule.getId();

        ContentValues values = new ContentValues();
        values.put(openHelper.CATEGORY, schedule.getCategory());
        values.put(openHelper.DETAIL, schedule.getDetail());
        values.put(openHelper.TIME_START, schedule.getTime_start());
        values.put(openHelper.TIME_LAST, schedule.getTime_last());
        values.put(openHelper.TIME_RECORDED,schedule.getTime_recorded());
        values.put(openHelper.URGENCY, schedule.getUrgency());
        values.put(openHelper.VIBRATION, schedule.isVibration());
        values.put(openHelper.VOLUME, schedule.getVolume());
        values.put(openHelper.STATUS, schedule.getStatus());

        db.update(openHelper.TABLE_NAME_SCHEDULE, values, openHelper.UID + " = ?", new String[]{id + ""});
    }


    //修改日程的状态
    public void changeScheduleStatus(int id, int flag) {

        ContentValues values = new ContentValues();
        values.put(openHelper.STATUS, flag);
        db.update(openHelper.TABLE_NAME_SCHEDULE, values, openHelper.UID + " = ?", new String[]{id + ""});

    }


    //获取所有日程类别名称
    public ArrayList<String> getAllCategoryName() {

        Cursor cursor = db.query(openHelper.TABLE_NAME_CATEGORY, new String[]{openHelper.CATEGORY_NAME}, null, null, null, null, null);
        ArrayList<String> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            String s = cursor.getString(cursor.getColumnIndex(openHelper.CATEGORY_NAME));
            list.add(s);
        }

        return list;
    }


    //增加日程别类
    public void addCategory(Category category) {

        ContentValues values = new ContentValues();

        values.put(openHelper.CATEGORY_NAME, category.getCategory_name());
        values.put(openHelper.TIME_TARGET, category.getTime_target());
        values.put(openHelper.TIME_SUMMARY, category.getTime_summary());
        values.put(openHelper.PERIODICITY, category.getPeriodicity());
        values.put(openHelper.TIME_CYCLE, category.getTime_cycle());

        db.insert(openHelper.TABLE_NAME_CATEGORY, null, values);

    }

    //查询日度总结需要的数据
    public ArrayList<SummaryDailyItem> getDailySummaryItems(){

        //初始化
        ArrayList<SummaryDailyItem> items = new ArrayList<>();
        ArrayList<String> names = getAllCategoryName();

        Calendar dayStart = Calendar.getInstance();
        Calendar dayEnd = Calendar.getInstance();

        dayStart.set(Calendar.HOUR_OF_DAY,0);
        dayStart.set(Calendar.MINUTE,0);
        dayStart.set(Calendar.SECOND,0);
        dayStart.set(Calendar.MILLISECOND,0);
        dayEnd.set(Calendar.DAY_OF_MONTH,dayEnd.get(Calendar.DAY_OF_MONTH)+1);
        dayEnd.set(Calendar.HOUR_OF_DAY,0);
        dayEnd.set(Calendar.MINUTE,0);
        dayEnd.set(Calendar.SECOND,0);
        dayEnd.set(Calendar.MILLISECOND,0);


        //查询“时间总计”数据
        Cursor cursor1 = db.query(openHelper.TABLE_NAME_SCHEDULE
                ,new String[]{openHelper.TIME_LAST}
                ,openHelper.TIME_START + ">? AND " + openHelper.TIME_START + "<?"
                ,new String[]{dayStart.getTimeInMillis()+"",dayEnd.getTimeInMillis()+""}
                ,null,null,null);
        items.add(new SummaryDailyItem("时间总计", getValue(cursor1,1)));
        cursor1.close();

        //查询“普通日程”数据
        Cursor cursor2 = db.query(openHelper.TABLE_NAME_SCHEDULE
                ,new String[]{openHelper.TIME_LAST}
                ,openHelper.TIME_START + ">? AND " + openHelper.TIME_START + "<? AND "
                + openHelper.CATEGORY + "=?"
                ,new String[]{dayStart.getTimeInMillis()+"",dayEnd.getTimeInMillis()+"","普通日程"}
                ,null,null,null);
        items.add(new SummaryDailyItem("普通日程", getValue(cursor2,1)));
        cursor2.close();

        //查询用户创建的日程类别的数据
        for (int i = 2 ; i < names.size() ; i++){
            //获取该日程类别今日累计时间
            Cursor cursor3 = db.query(openHelper.TABLE_NAME_SCHEDULE
                    ,new String[]{openHelper.TIME_LAST}
                    ,openHelper.TIME_START + ">? AND " + openHelper.TIME_START + "<? AND "
                    + openHelper.CATEGORY + "=?"
                    ,new String[]{dayStart.getTimeInMillis()+"",dayEnd.getTimeInMillis()+"",names.get(i)}
                    ,null,null,null);
            float f1 = getValue(cursor3,2);
            cursor3.close();

            //获取该日程类别当日规划时间
            Cursor cursor4 = db.query(openHelper.TABLE_NAME_CATEGORY,new String[]{openHelper.TIME_CYCLE}
                    ,openHelper.CATEGORY_NAME + "=?",new String[]{names.get(i)},null,null,null);
            float f2 = getValue(cursor4, 3);
            cursor4.close();

            //添加数据
            items.add(new SummaryDailyItem(names.get(i),100*f1/f2));

        }

        db.close();

        return items;

    }

    private float getValue(Cursor cursor,int flag){

        long sum = 0;
        long temp = 0;
        float result = 0.0f;

        while (cursor.moveToNext()){
            //取出
            switch (flag){
                case 1:
                case 2:
                    temp = cursor.getLong(cursor.getColumnIndex(openHelper.TIME_LAST));
                    break;
                case 3:
                    temp = cursor.getLong(cursor.getColumnIndex(openHelper.TIME_CYCLE));
                    break;
            }

            //转换成秒数,汇总
            sum = sum + temp/1000;

        }

        switch (flag){

            case 1:

                //转换成百分比中的数字，例如58%中的58
                result = (float)(100*sum/(8*60*60.0));

                break;

            case 2:
            case 3:

                //交由调用者自行转化
                result = sum;
                break;

        }

        return result;

    }



    //建立数据库
    class DatabaseOpenHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "moli.db";

        private static final String TABLE_NAME_SCHEDULE = "schedule";
        private static final String UID = "_id";
        private static final String CATEGORY = "category";
        private static final String DETAIL = "detail";
        private static final String TIME_START = "time_start";
        private static final String TIME_LAST = "time_last";
        private static final String TIME_RECORDED = "timer_recorded";
        private static final String URGENCY = "urgency";
        private static final String VIBRATION = "vibration";
        private static final String VOLUME = "volume";

        private static final String STATUS = "status";//增加表示日程状态的列，状态为“未完成”“已推迟”“已完成”

        private static final String CREATE_TABLE_SCHEDULE = "CREATE TABLE " + TABLE_NAME_SCHEDULE + " ("
                + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "//主键
                + CATEGORY + " TEXT, "//日程类别
                + DETAIL + " TEXT, "//日程详细信息
                + TIME_START + " INTEGER, "//日程开始时间,使用UNIX时间，单位为毫秒
                + TIME_LAST + " INTEGER, "//日程持续时间，使用UNIX时间，单位为毫秒
                + TIME_RECORDED + " INTEGER, "//日程已记录时间，使用UNIX时间，单位为毫秒
                + URGENCY + " INTEGER, "//日程重要紧急度，为1234
                + VIBRATION + " INTEGER, "//是否震动提示
                + VOLUME + " INTEGER, "//提示音大小1~00;
                + STATUS + " INTEGER)";//1、2、3分别是未完成、延期、已完成，目前只设计到13，延期功能待添加或不添加了


        private static final String TABLE_NAME_CATEGORY = "category";
        private static final String CATEGORY_NAME = "category_name";
        private static final String TIME_TARGET = "time_target";
        private static final String TIME_SUMMARY = "time_summary";
        private static final String PERIODICITY = "periodicity";
        private static final String TIME_CYCLE = "time_cycle";

        //db.execSQL("CREATE TABLE person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age SMALLINT)");
//        private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "+ TABLE_NAME_CATEGORY+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + CATEGORY_NAME + " TEXT, " + TIME_TARGET + " TEXT, " + TIME_SUMMARY + " TEXT, " + PERIODICITY + " TEXT, " + TIME_CYCLE + " TEXT)";
        private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_NAME_CATEGORY
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY_NAME + " TEXT, "//日程类的名称
                + TIME_TARGET + " INTEGER, "//日程类的目标总时间,使用UNIX时间，单位为毫秒
                + TIME_SUMMARY + " INTEGER, "//日程类目前已累积时间,使用UNIX时间，单位为毫秒
                + PERIODICITY + " TEXT, "//星期几有此日程
                + TIME_CYCLE + " INTEGER)";//此日程类循环每周期时间,使用UNIX时间，单位为毫秒


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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //构造方法传进来的int version != oldVersion时调用
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SCHEDULE);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CATEGORY);
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
