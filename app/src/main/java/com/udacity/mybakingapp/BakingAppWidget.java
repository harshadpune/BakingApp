package com.udacity.mybakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.udacity.mybakingapp.network.BakingDataList;
import com.udacity.mybakingapp.network.RetrofitAPIClient;
import com.udacity.mybakingapp.network.RetrofitAPIInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

//    public static final String SHOW_DETAILS = "SHOW_DETAILS";
    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("received", "---------------Received"+intent.getAction());
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            if (intent.getAction().equals(TOAST_ACTION)) {
//                int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(context,BakingAppWidget.class));
                int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                Log.e("received", "---------------Received"+intent.getAction());
//                mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
//                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
//                views.setRemoteAdapter(appWidgetId, R.id.list_view, intent);
//                views.setViewVisibility(R.id.tvWidgetItem, View.VISIBLE);
            }
            super.onReceive(context, intent);
        }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, List<BakingDataList> bakingDataLists) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object

        Log.d("Baking Widget","-------------------updateAppWidget");
        Intent intent =  new Intent(context, ListViewWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));


        String[] recipeNames = new String[bakingDataLists.size()];
        for(int i=0; i<bakingDataLists.size(); i++) {
            recipeNames[i] = bakingDataLists.get(i).name;
        }

        intent.putExtra("SomeKey", recipeNames);
        /*List<BakingDataList> thumbList = new ArrayList<BakingDataList>();
        BakingDataList bakingDataList = new BakingDataList();
        bakingDataList.name = "My Name";
        thumbList.add(bakingDataList);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SomeKey", (Serializable) thumbList);
        intent.putExtras(bundle);*/


//        intent.putExtra("TryIt", "What the hell it works");


        Log.d("Baking Widget","-------------------before RemoteViews");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        views.setRemoteAdapter(appWidgetId, R.id.list_view, intent);

        views.setEmptyView(R.id.list_view, R.id.id_text);

        /*Intent intent2 = new Intent(context, BakingAppWidget.class);
        intent2.setAction(SHOW_DETAILS);
        intent2.putExtra("APP_WIDGET_ID",appWidgetId);
        intent2.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent2,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(appWidgetId, pendingIntent);*/

        Intent toastIntent = new Intent(context, BakingAppWidget.class);
        toastIntent.setAction(BakingAppWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, 0);
//        views.setOnClickPendingIntent(R.id.list_view, toastPendingIntent);

       /* for(int i=0; i<bakingDataLists.size(); i++) {
            RemoteViews bakingWidgetItem = new RemoteViews(context.getPackageName(), R.layout.baking_widget_item);
            bakingWidgetItem.setTextViewText(R.id.tvWidgetItem, "----------My Text");
            views.addView(R.id.llContainer, bakingWidgetItem);
        }*/
//        views.setOnClickPendingIntent(R.id.tvWidgetItem, toastPendingIntent);

//        views.setTextViewText(R.id.appwidget_text, widgetText);
//            views.setad
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        Log.d("Baking Widget","-------------------onUpdate");
       /* for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/



        RetrofitAPIInterface retrofitAPIInterface = RetrofitAPIClient.getClient().create(RetrofitAPIInterface.class);
        final Call<List<BakingDataList>> bakingDataList = retrofitAPIInterface.getBakingDataList();

        bakingDataList.enqueue(new Callback<List<BakingDataList>>() {
            @Override
            public void onResponse(Call<List<BakingDataList>> call, Response<List<BakingDataList>> response) {
                Log.d("TAG", "------------RV Response Received");
                List<BakingDataList> bakingDataLists = response.body();
                for (BakingDataList bakingDataList1 : bakingDataLists) {
                    Log.d("TAG", "------------RV Response ID " + bakingDataList1.id);
                    Log.d("TAG", "------------RV Response Name " + bakingDataList1.name);
                }
                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId, bakingDataLists);
                }
            }

            @Override
            public void onFailure(Call<List<BakingDataList>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }



}

