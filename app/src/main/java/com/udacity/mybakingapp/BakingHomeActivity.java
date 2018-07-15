package com.udacity.mybakingapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.udacity.mybakingapp.databinding.ActivityBakingHomeBinding;
import com.udacity.mybakingapp.network.RetrofitAPIClient;
import com.udacity.mybakingapp.network.RetrofitAPIInterface;
import com.udacity.mybakingapp.network.BakingDataList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BakingHomeActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private GridLayoutManager gm;
    private ActivityBakingHomeBinding mActivityBakingHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_home);
        mActivityBakingHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_baking_home);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-2.
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        getBakingData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    }

    private void getBakingData() {
        RetrofitAPIInterface retrofitAPIInterface = RetrofitAPIClient.getClient().create(RetrofitAPIInterface.class);
        Call<List<BakingDataList>> bakingDataList = retrofitAPIInterface.getBakingDataList();

        bakingDataList.enqueue(new Callback<List<BakingDataList>>() {
            @Override
            public void onResponse(Call<List<BakingDataList>> call, Response<List<BakingDataList>> response) {
                List<BakingDataList> bakingDataLists = response.body();
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.item_list);
                assert recyclerView != null;
                recyclerView.setAdapter(new BakingRecyclerAdapter(BakingHomeActivity.this, bakingDataLists));
                if(mTwoPane) {
                    gm = new GridLayoutManager(BakingHomeActivity.this, 3);
                }else{
                    gm = new GridLayoutManager(BakingHomeActivity.this, 1);
                }
                recyclerView.setLayoutManager(gm);
            }

            @Override
            public void onFailure(Call<List<BakingDataList>> call, Throwable t) {

            }
        });


    }

}
