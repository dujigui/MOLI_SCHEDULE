package com.pheynix.moli_schedule;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private TabLayout tabs;
    private MainContentPageAdapter mPageAdapter;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

        setUpDrawer();



    }

    private void initView() {
        //设置新的Toolbar
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_navigationView);
        //设置Viewpager和tabs
        mViewPager = (ViewPager) findViewById(R.id.id_viewPager);
        tabs = (TabLayout) findViewById(R.id.id_tabs);

        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());

        mPageAdapter = new MainContentPageAdapter(getSupportFragmentManager(),this);

        mViewPager.setAdapter(mPageAdapter);
        tabs.setupWithViewPager(mViewPager);
    }


    //设置汉堡包图标和菜单事件点击的响应逻辑
    private void setUpDrawer() {

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
//                不需要设置为check
//                menuItem.setChecked(true);

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
}
