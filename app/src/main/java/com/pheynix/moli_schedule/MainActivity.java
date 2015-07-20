package com.pheynix.moli_schedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pheynix.moli_schedule.Model.Category;
import com.pheynix.moli_schedule.Model.Schedule;
import com.pheynix.moli_schedule.SummaryFragment.DailySummaryFragment;
import com.pheynix.moli_schedule.SummaryFragment.MonthlySummaryFragment;
import com.pheynix.moli_schedule.SummaryFragment.WeeklySummaryFragment;
import com.pheynix.moli_schedule.Util.DBUtil;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private TabLayout tabs;
    private MainActivityViewPagerAdapter mPageAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DBUtil dbUtil;
    private FragmentManager fragmentManager;
    private TextView tv_point;
    private double point = 0;

    private ArrayList<Schedule> schedules;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbUtil = new DBUtil(this);

        if (isFirstLaunch()){
            initializeCategory();
        }
        
        initializeView();

        initializeDrawer();


    }



    private void initializeView() {
        //设置新的Toolbar
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(mToolbar);

        //NavigationView
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_navigationView);

        //设置Viewpager和tabs
        mViewPager = (ViewPager) findViewById(R.id.id_viewPager);
        tabs = (TabLayout) findViewById(R.id.id_tabs);
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());

        mPageAdapter = new MainActivityViewPagerAdapter(getSupportFragmentManager(),this);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.addOnPageChangeListener(this);
        tabs.setupWithViewPager(mViewPager);

        fragmentManager = getSupportFragmentManager();

        tv_point = (TextView) findViewById(R.id.tv_point_main_activity);

        dbUtil = new DBUtil(this);
        schedules = dbUtil.getAllSchedules();
        final int sizeOfSchedules = schedules.size();

        new Thread(new Runnable() {
            @Override
            public void run() {



                for (int i = 0 ; i < sizeOfSchedules ; i++){
                    Message msg = new Message();
                    switch (schedules.get(i).getUrgency()){
                        case 1:
                            msg.what = 1;
                            msg.arg1 = i;
                            msg.arg1 = i;
                            msg.arg1 = i;
                            mHandler.sendMessage(msg);
                            break;
                        case 2:
                            msg.what = 2;
                            msg.arg1 = i;
                            mHandler.sendMessage(msg);
                            break;
                        case 3:
                            msg.what = 3;
                            msg.arg1 = i;
                            mHandler.sendMessage(msg);
                            break;
                        case 4:
                            msg.what = 4;
                            msg.arg1 = i;
                            mHandler.sendMessage(msg);
                            break;

                    }

                }
            }
        }).start();


    }


    //设置汉堡包图标和菜单事件点击的响应逻辑
    private void initializeDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.id_drawer_share:
                        Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.id_drawer_settings:
                        Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.id_drawer_about:
                        Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.id_contact_us:
                        Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                }

                mDrawerLayout.closeDrawers();

                return true;
            }
        });

        mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }






    //add onPageChangeListener
    //滑动过程调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    //滑动前调用
    @Override
    public void onPageSelected(int position) {
        //重复代码，考虑重构
        if (position == 1){
            if (fragmentManager.findFragmentByTag("DailySummaryFragment") == null){
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container_fragment_summary, new DailySummaryFragment(), "DailySummaryFragment");
                transaction.commit();
                mViewPager.setCurrentItem(1);
            }
        }
        if (position == 0){

            if (fragmentManager.findFragmentByTag("DailySummaryFragment") != null){
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(fragmentManager.findFragmentByTag("DailySummaryFragment"));
                transaction.commit();
            }
            if (fragmentManager.findFragmentByTag("WeeklySummaryFragment") != null){
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(fragmentManager.findFragmentByTag("WeeklySummaryFragment"));
                transaction.commit();
            }
            if (fragmentManager.findFragmentByTag("MonthlySummaryFragment") != null){
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(fragmentManager.findFragmentByTag("MonthlySummaryFragment"));
                transaction.commit();
            }
        }
    }

    //0,1,2 == idle,dragging,setting，滑动状态
    @Override
    public void onPageScrollStateChanged(int state) {
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //在SummaryFragment才让OptionMenu生效
//        if (isSummaryFragment){
//
//        }

        switch (item.getItemId()){
            case R.id.action_daily:
                if (fragmentManager.findFragmentByTag("DailySummaryFragment") == null){
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.container_fragment_summary, new DailySummaryFragment(), "DailySummaryFragment");
                    transaction.commit();
                }
                mViewPager.setCurrentItem(1);
                break;
            case R.id.action_weekly:
                if (fragmentManager.findFragmentByTag("WeeklySummaryFragment") == null){
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.container_fragment_summary, new WeeklySummaryFragment(), "WeeklySummaryFragment");
                    transaction.commit();
                }
                mViewPager.setCurrentItem(1);
                break;
            case R.id.action_monthly:
                if (fragmentManager.findFragmentByTag("MonthlySummaryFragment") == null){
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.container_fragment_summary,new MonthlySummaryFragment(),"MonthlySummaryFragment");
                    transaction.commit();
                }
                mViewPager.setCurrentItem(1);
                break;
        }

        return true;
    }


    //初始化日程类别，首次启动程序时调用
    private void initializeCategory() {
        Category category_normal = new Category("普通日程",0,0,"-1",0);
        dbUtil.addCategory(category_normal);

        Category category_new = new Category("新建日程",0,0,"-1",0);
        dbUtil.addCategory(category_new);
    }

    //是否第一次启动此应用，用SharePreferences的方式保存
    public boolean isFirstLaunch() {

        SharedPreferences sharedPreferences = getSharedPreferences("pre_first_launch", Context.MODE_PRIVATE);
        boolean isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch",true);

        if (isFirstLaunch){
            sharedPreferences.edit().putBoolean("isFirstLaunch",false).commit();
        }

        return isFirstLaunch;
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1:
                    point = point + 2*(schedules.get(msg.arg1).getTime_recorded()/(1000*60));
                    break;
                case 2:
                    point = point + 1.5*schedules.get(msg.arg1).getTime_recorded()/(1000*60);
                    break;
                case 3:
                    point = point + 1*schedules.get(msg.arg1).getTime_recorded()/(1000*60);
                    break;
                case 4:
                    point = point + 0.5*schedules.get(msg.arg1).getTime_recorded()/(1000*60);
                    break;
            }

            tv_point.setText((int)point+"");
        }
    };

}
