package com.udacity.mybakingapp;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by HARSHAD on 12/07/2018.
 */

public class ListViewWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {
        Log.d("Baking Widget","-------------------RemoteViewsFactory");
        return new ListviewRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
