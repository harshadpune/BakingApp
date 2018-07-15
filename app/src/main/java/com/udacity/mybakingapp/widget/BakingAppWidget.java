package com.udacity.mybakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;

import com.udacity.mybakingapp.R;
import com.udacity.mybakingapp.network.BakingDataList;
import com.udacity.mybakingapp.network.RetrofitAPIClient;
import com.udacity.mybakingapp.network.RetrofitAPIInterface;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.example.android.bakingappwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.bakingappwidget.EXTRA_ITEM";
    public static final String BACK_CLICKED = "com.example.android.bakingappwidget.BACK_CLICKED";

        @Override
        public void onReceive(Context context, Intent intent) {
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            if (intent.getAction().equals(TOAST_ACTION)) {
                views.setViewVisibility(R.id.llContainer, View.GONE);
                views.setViewVisibility(R.id.llIngredientsContainer, View.VISIBLE);
                views.setTextViewText(R.id.tvIngredientDetails, ""+ intent.getStringExtra(EXTRA_ITEM));
                Intent toastIntent = new Intent(context, BakingAppWidget.class);
                toastIntent.setAction(BakingAppWidget.BACK_CLICKED);
                toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                toastIntent.setData(Uri.parse(toastIntent.toUri(Intent.URI_INTENT_SCHEME)));
                PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, 0);
                views.setOnClickPendingIntent(R.id.ivLeftArrow, toastPendingIntent);
            }else if(intent.getAction().equals(BACK_CLICKED)){
                views.setViewVisibility(R.id.llContainer, View.VISIBLE);
                views.setViewVisibility(R.id.llIngredientsContainer, View.GONE);
                views.setTextViewText(R.id.tvIngredientDetails, "");
            }
            mgr.updateAppWidget(appWidgetId, views);
            super.onReceive(context, intent);
        }


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, List<BakingDataList> bakingDataLists) {

       RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        for(int i=0; i<bakingDataLists.size(); i++) {
            RemoteViews bakingWidgetItem = new RemoteViews(context.getPackageName(), R.layout.baking_widget_item);
            bakingWidgetItem.setTextViewText(R.id.tvWidgetItem, ""+bakingDataLists.get(i).name);
            Intent toastIntent = new Intent(context, BakingAppWidget.class);
            toastIntent.setAction(BakingAppWidget.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            List<BakingDataList.Ingredients> ingredients = bakingDataLists.get(i).ingredients;
            String listOfIngredients = "";

            for (int j=0; j<ingredients.size(); j++) {
                BakingDataList.Ingredients ingredient= ingredients.get(j);
                if(!TextUtils.isEmpty(listOfIngredients)){
                    listOfIngredients += "\n"+ingredient.quantity+" "+ingredient.measure+" "+ingredient.ingredient;
                }else{
                    listOfIngredients += ""+ingredient.quantity+" "+ingredient.measure+" "+ingredient.ingredient;
                }
            }

            toastIntent.putExtra(BakingAppWidget.EXTRA_ITEM, listOfIngredients);
            toastIntent.setData(Uri.parse(toastIntent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, 0);
            bakingWidgetItem.setOnClickPendingIntent(R.id.tvWidgetItem, toastPendingIntent);
            views.addView(R.id.llContainer, bakingWidgetItem);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        RetrofitAPIInterface retrofitAPIInterface = RetrofitAPIClient.getClient().create(RetrofitAPIInterface.class);
        final Call<List<BakingDataList>> bakingDataList = retrofitAPIInterface.getBakingDataList();

        bakingDataList.enqueue(new Callback<List<BakingDataList>>() {
            @Override
            public void onResponse(Call<List<BakingDataList>> call, Response<List<BakingDataList>> response) {
                List<BakingDataList> bakingDataLists = response.body();
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

