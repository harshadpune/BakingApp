package com.udacity.mybakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.mybakingapp.network.BakingDataList;
import com.udacity.mybakingapp.network.RetrofitAPIClient;
import com.udacity.mybakingapp.network.RetrofitAPIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HARSHAD on 12/07/2018.
 */

class ListviewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    private final Context context;
    private final Intent intent;
    private static String[] bakingDataLists;


    public ListviewRemoteViewsFactory(Context applicationContext, Intent intent) {
        Log.d("TAG","------------ in ListviewRemoteViewsFactory");
        this.context = applicationContext;
        this.intent = intent;
        bakingDataLists = intent.getStringArrayExtra("SomeKey");
    }

    @Override
    public void onCreate() {
        Log.d("TAG","------------ in Oncrate");
    }

    @Override
    public void onDataSetChanged() {
        Log.d("TAG","------------ Dataset Changed");
//        getBakingData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(bakingDataLists != null) {
            Log.d("TAG", "------------RV getCount not null"+bakingDataLists.length);
            return bakingDataLists.length;
        }
        else {
            Log.d("TAG", "------------RV getCount "+0);
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d("TAG", "------------ GetViewAt ");
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.baking_widget_item);
        String data =  bakingDataLists[position];
        rv.setTextViewText(R.id.tvWidgetItem, data) ;
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
            return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
