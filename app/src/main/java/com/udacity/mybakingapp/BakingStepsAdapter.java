package com.udacity.mybakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.mybakingapp.network.BakingDataList;
import com.udacity.mybakingapp.utils.AppConstants;

/**
 * Created by HARSHAD on 07/07/2018.
 */

class BakingStepsAdapter extends RecyclerView.Adapter<BakingStepsAdapter.ViewHolder> {
    private Context context;
    private BakingDataList bakingDataList;

    public BakingStepsAdapter(Context context, BakingDataList bakingDataList) {
        this.context = context;
        this.bakingDataList = bakingDataList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_details_item_row, null);
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

        public ViewHolder(View itemView) {
            super(itemView);
            tvStepName = (TextView) itemView.findViewById(R.id.tvStepName);
            tvStepName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent bakingDetailsFragActivity = new Intent(context, BakingDescriptionFragActivity.class);
            bakingDetailsFragActivity.putExtra(AppConstants.SELECTED_RECIPE, bakingDataList);
            bakingDetailsFragActivity.putExtra(AppConstants.SELECTED_POSITION, getAdapterPosition());
            context.startActivity(bakingDetailsFragActivity);
        }
    }
}
