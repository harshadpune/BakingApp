package com.udacity.mybakingapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import com.udacity.mybakingapp.databinding.RecipieDetailsBinding;
import com.udacity.mybakingapp.network.BakingDataList;
import com.udacity.mybakingapp.utils.AppConstants;

import java.util.List;

/**
 * Created by HARSHAD on 07/07/2018.
 */

public class RecipeDetailsActivity extends AppCompatActivity {

    private RecipieDetailsBinding mRecipieDetailsBinding;
    private BakingDataList bakingDataList;
    private boolean mTowPane = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipie_details);
        if(findViewById(R.id.baking_details_container) != null){
            mTowPane = true;
        }
        mRecipieDetailsBinding = DataBindingUtil.setContentView(this,R.layout.recipie_details);
        Bundle bundle = getIntent().getExtras();
        bakingDataList = (BakingDataList) bundle.getSerializable(AppConstants.SELECTED_RECIPE);
        setIngredients();
        setAdapter();
        if(mTowPane)
        setFirstItemIfTwoPane();

    }



    private void setIngredients() {
        List<BakingDataList.Ingredients> ingredients = bakingDataList.ingredients;

        String listOfIngredients = "";

        for (BakingDataList.Ingredients ingredient: ingredients) {
            if(!TextUtils.isEmpty(listOfIngredients)){
                listOfIngredients += "\n"+ingredient.quantity+" "+ingredient.measure+" "+ingredient.ingredient;
            }else{
                listOfIngredients += ""+ingredient.quantity+" "+ingredient.measure+" "+ingredient.ingredient;
            }
        }

        mRecipieDetailsBinding.tvIngredientList.setText(""+listOfIngredients);
    }

    private void setAdapter() {
        mRecipieDetailsBinding.rvRecipeList.setAdapter(new BakingStepsAdapter(this,bakingDataList, mTowPane));
    }

    private void setFirstItemIfTwoPane() {
        BakingDescriptionFragment bakingDescriptionFragment = new BakingDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.SELECTED_RECIPE, bakingDataList.steps.get(0));
        bakingDescriptionFragment.setArguments(bundle);
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.baking_details_container, bakingDescriptionFragment)
                .commit();
    }
}
