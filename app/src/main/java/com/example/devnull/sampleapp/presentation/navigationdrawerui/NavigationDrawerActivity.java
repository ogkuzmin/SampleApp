/*
 *  Copyright (c) Ascom (Sweden) AB. All rights reserved.
 */
package com.example.devnull.sampleapp.presentation.navigationdrawerui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.devnull.sampleapp.R;
import com.example.devnull.sampleapp.presentation.samplelistui.SampleListFragment;

public class NavigationDrawerActivity extends AppCompatActivity {

    private final static String LOG_TAG = NavigationDrawerActivity.class.getSimpleName();

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private FrameLayout mContentFrameLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private int mSelectedPosition;
    private static final String SELECTED_FRAGMENT_POSITION_KEY_NAME = "SELECTED_FRAGMENT_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_layout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mContentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);

        mPlanetTitles = getResources().getStringArray(R.array.navigation_drawer_items_name);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navigation_drawer_element, R.id.navigation_item_text_view, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        if (savedInstanceState != null) {
            mSelectedPosition = savedInstanceState.getInt(SELECTED_FRAGMENT_POSITION_KEY_NAME, 1);
        } else {
            mSelectedPosition = 1;
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initFragmentByPosition(mSelectedPosition);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Bundle bundle = new Bundle();
        bundle.putInt(SELECTED_FRAGMENT_POSITION_KEY_NAME, mSelectedPosition);
        onSaveInstanceState(bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initFragmentByPosition(int position) {

        Log.v(LOG_TAG, "::initFragmentByPosition() with position " + position);

        Fragment fragment = null;

        switch (position) {
            case 1:
                fragment = new SampleListFragment();
                break;
            case 2:
                fragment = new Fragment();
                break;
            case 3:
                fragment = new Fragment();
            case 4:
                fragment = new Fragment();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment).commit();
        // Highlight the selected item, update the title, and close the drawer

        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
        getSupportActionBar().setTitle(mPlanetTitles[position - 1]);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
            initFragmentByPosition(position);
        }
    }


}
