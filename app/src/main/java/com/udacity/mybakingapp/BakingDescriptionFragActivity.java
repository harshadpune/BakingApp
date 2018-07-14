package com.udacity.mybakingapp;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.udacity.mybakingapp.databinding.ActivityBakingViewPagerBinding;
import com.udacity.mybakingapp.network.BakingDataList;
import com.udacity.mybakingapp.utils.AppConstants;

/**
 * Created by HARSHAD on 07/07/2018.
 */

public class BakingDescriptionFragActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private BakingDataList bakingDataList;
    private int position;
    private ActivityBakingViewPagerBinding activityBakingViewPagerBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_view_pager);
        activityBakingViewPagerBinding = DataBindingUtil.setContentView(this, R.layout.activity_baking_view_pager);
        position = getIntent().getIntExtra(AppConstants.SELECTED_POSITION,0);
        bakingDataList = (BakingDataList) getIntent().getSerializableExtra(AppConstants.SELECTED_RECIPE);
        activityBakingViewPagerBinding.pager.setAdapter(new BakingSlidePagerAdapter(getSupportFragmentManager()));
        activityBakingViewPagerBinding.pager.setCurrentItem(position);
        activityBakingViewPagerBinding.tvCounter.setText((position+1)+"/"+bakingDataList.steps.size());
        activityBakingViewPagerBinding.ivLeftArrow.setOnClickListener(this);
        activityBakingViewPagerBinding.ivRightArrow.setOnClickListener(this);
        activityBakingViewPagerBinding.pager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivLeftArrow:
                if(position != 0) {
                    position = position-1;
//                    Toast.makeText(this, "Previous Page", Toast.LENGTH_SHORT).show();
                    activityBakingViewPagerBinding.pager.setCurrentItem(position);
                    activityBakingViewPagerBinding.tvCounter.setText((position+1)+"/"+bakingDataList.steps.size());
                }
                break;
                
            case R.id.ivRightArrow:
                if(position+1 < bakingDataList.steps.size()) {
                    position = position+1;
//                    Toast.makeText(this, "Next Page", Toast.LENGTH_SHORT).show();
                    activityBakingViewPagerBinding.pager.setCurrentItem(position);
                    activityBakingViewPagerBinding.tvCounter.setText((position+1)+"/"+bakingDataList.steps.size());
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
        activityBakingViewPagerBinding.tvCounter.setText((position+1)+"/"+bakingDataList.steps.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
