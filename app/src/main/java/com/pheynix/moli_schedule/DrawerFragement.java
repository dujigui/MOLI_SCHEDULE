package com.pheynix.moli_schedule;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragement extends Fragment {
    private boolean isUserLearned;
    private boolean isFromSavedInstanceState;
    private final static String PRE_USER_LEARN_DRAWER = "is_user_learn_drawer";
    private final static String KEY = "key";

    public DrawerFragement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            isFromSavedInstanceState = true ;
        }
        isUserLearned = Boolean.parseBoolean(readFromPreferences());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }


    public void setUp(int drawerLayoutId,Toolbar toolbar) {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                (DrawerLayout)getActivity().findViewById(drawerLayoutId), toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isUserLearned){
                    isUserLearned = true ;
                    saveToPreferences();
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        if (!isUserLearned && !isFromSavedInstanceState){
            DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(drawerLayoutId);
            drawerLayout.openDrawer(getActivity().findViewById(R.id.id_drawer_fragment_in_drawer_layout));
            isUserLearned = true;
            saveToPreferences();
        }

        ((DrawerLayout) getActivity().findViewById(drawerLayoutId)).setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    private void saveToPreferences(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PRE_USER_LEARN_DRAWER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY,isUserLearned+"");
        editor.commit();
    }

    private String readFromPreferences(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PRE_USER_LEARN_DRAWER,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY,"false");
    }
}
