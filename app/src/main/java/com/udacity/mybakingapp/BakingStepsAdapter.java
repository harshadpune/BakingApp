package com.udacity.mybakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.mybakingapp.network.BakingDataList;
import com.udacity.mybakingapp.utils.AppConstants;

/**
 * Created by HARSHAD on 07/07/2018.
 */

class BakingStepsAdapter extends RecyclerView.Adapter<BakingStepsAdapter.ViewHolder> {
    private final boolean mTwoPane;
    private RecipeDetailsActivity mParentActivity;
    private BakingDataList bakingDataList;

    public BakingStepsAdapter(RecipeDetailsActivity context, BakingDataList bakingDataList, boolean mTowPane) {
        this.mParentActivity = context;
        this.bakingDataList = bakingDataList;
        this.mTwoPane = mTowPane;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mParentActivity).inflate(R.layout.recipe_details_item_row, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvStepName.setText(""+ bakingDataList.steps.get(position).shortDescription);
    }

    @Override
    public int getItemCount() {
        return bakingDataList.steps.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvStepName;
        private final LinearLayout llStep;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStepName = (TextView) itemView.findViewById(R.id.tvStepName);
            llStep = (LinearLayout) itemView.findViewById(R.id.llStep);
            llStep.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mTwoPane){
                BakingDescriptionFragment bakingDescriptionFragment = new BakingDescriptionFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConstants.SELECTED_RECIPE, bakingDataList.steps.get(getAdapterPosition()));
                bakingDescriptionFragment.setArguments(bundle);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.baking_details_container, bakingDescriptionFragment)
                        .commit();
            }else {
                Intent bakingDetailsFragActivity = new Intent(mParentActivity, BakingDescriptionFragActivity.class);
                bakingDetailsFragActivity.putExtra(AppConstants.SELECTED_RECIPE, bakingDataList);
                bakingDetailsFragActivity.putExtra(AppConstants.SELECTED_POSITION, getAdapterPosition());
                mParentActivity.startActivity(bakingDetailsFragActivity);
            }
        }
    }
}
