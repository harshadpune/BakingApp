package com.udacity.mybakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import com.udacity.mybakingapp.network.BakingDataList;
import com.udacity.mybakingapp.utils.AppConstants;

/**
 * Created by HARSHAD on 07/07/2018.
 */

public class BakingDescriptionFragActivity extends FragmentActivity{

    private BakingDataList bakingDataList;
//    private int position;
    private ViewPager mPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_view_pager);
//        position = getIntent().getIntExtra(AppConstants.SELECTED_POSITION,0);
        bakingDataList = (BakingDataList) getIntent().getSerializableExtra(AppConstants.SELECTED_RECIPE);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new BakingSlidePagerAdapter(getSupportFragmentManager()));

    }



    private class BakingSlidePagerAdapter extends FragmentStatePagerAdapter {
        public BakingSlidePagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            BakingDescriptionFragment bakingDescriptionFragment = new BakingDescriptionFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstants.SELECTED_RECIPE, bakingDataList.steps.get(position));
            bakingDescriptionFragment.setArguments(bundle);
            return bakingDescriptionFragment;
        }

        @Override
        public int getCount() {
            return bakingDataList.steps.size();
        }
    }
}
