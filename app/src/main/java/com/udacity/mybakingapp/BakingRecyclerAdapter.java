package com.udacity.mybakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.mybakingapp.network.BakingDataList;
import com.udacity.mybakingapp.utils.AppConstants;

import java.util.List;

/**
 * Created by HARSHAD on 07/07/2018.
 */

public class BakingRecyclerAdapter extends RecyclerView.Adapter<BakingRecyclerAdapter.ViewHolder> {


    private final List<BakingDataList> bakingDataLists;
    private final Context context;

    public BakingRecyclerAdapter(Context context, List<BakingDataList> bakingDataLists){
        this.context = context;
        this.bakingDataLists = bakingDataLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.baking_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvRecipeName.setText(""+bakingDataLists.get(position).name);
    }

    @Override
    public int getItemCount() {
        return bakingDataLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CardView cvRecipe;
        private final TextView tvRecipeName;

        public ViewHolder(View itemView) {
            super(itemView);
            cvRecipe = (CardView) itemView.findViewById(R.id.cvRecipe);
            tvRecipeName = (TextView) itemView.findViewById(R.id.tvRecipeName);
            cvRecipe.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cvRecipe:
                    Intent recipeIntent = new Intent(context, RecipeDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(AppConstants.SELECTED_RECIPE, bakingDataLists.get(getAdapterPosition()));
                    recipeIntent.putExtras(bundle);
                    context.startActivity(recipeIntent);
                    break;
            }
        }
    }
}
