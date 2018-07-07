package com.udacity.mybakingapp.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by HARSHAD on 07/07/2018.
 */

public interface RetrofitAPIInterface {

    @GET("baking.json")
    Call<List<BakingDataList>> getBakingDataList();
}
