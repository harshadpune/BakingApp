package com.udacity.mybakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.udacity.mybakingapp.network.BakingDataList;
import com.udacity.mybakingapp.utils.AppConstants;

/**
 * Created by HARSHAD on 07/07/2018.
 */

public class BakingDescriptionFragment extends Fragment {

    private VideoView vvPlayer;
    private TextView tvStepDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_baking_description, container, false);

        Bundle bundle = getArguments();
        BakingDataList.Steps steps = (BakingDataList.Steps) bundle.getSerializable(AppConstants.SELECTED_RECIPE);
        vvPlayer = (VideoView) rootView.findViewById(R.id.vvPlayer);
        vvPlayer.setVideoURI(Uri.parse(steps.videoURL));
        vvPlayer.start();

        tvStepDescription = (TextView) rootView.findViewById(R.id.tvStepDescription);
        tvStepDescription.setText(steps.description);
        return rootView;
    }

}
