package com.udacity.mybakingapp;

import android.content.res.Configuration;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.mybakingapp.network.BakingDataList;
import com.udacity.mybakingapp.utils.AppConstants;

/**
 * Created by HARSHAD on 07/07/2018.
 */

public class BakingDescriptionFragment extends Fragment {

    private SimpleExoPlayerView vvPlayer;
    private TextView tvStepDescription;
    private SimpleExoPlayer mExoPlayer;
    private BakingDataList.Steps steps;
    private LinearLayout llDescriptionContainer;
    private LinearLayout llNavigationBar;
    private boolean isLandscape = false;
    private boolean isPortrait = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_baking_description, container, false);

        Bundle bundle = getArguments();
         steps = (BakingDataList.Steps) bundle.getSerializable(AppConstants.SELECTED_RECIPE);
         llDescriptionContainer= (LinearLayout) rootView.findViewById(R.id.llDescriptionContainer);
        llNavigationBar= (LinearLayout) rootView.findViewById(R.id.llNavigationBar);
        vvPlayer = (SimpleExoPlayerView) rootView.findViewById(R.id.vvPlayer);
        if(TextUtils.isEmpty(steps.videoURL)){
            vvPlayer.setVisibility(View.GONE);
        }else {
            initializePlayer();
        }
//        vvPlayer.setVideoURI(Uri.parse(steps.videoURL));
//        vvPlayer.start();

//        if(savedInstanceState !=null)
//            mExoPlayer.seekTo(savedInstanceState.getLong(AppConstants.CURRENT_PLAY_POSITION));

        tvStepDescription = (TextView) rootView.findViewById(R.id.tvStepDescription);
        tvStepDescription.setText(steps.description);
        return rootView;
    }

    private void initializePlayer() {
        if(mExoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);

            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(steps.videoURL), new DefaultDataSourceFactory(getActivity(),userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
            vvPlayer.setPlayer(mExoPlayer);
        }
    }



 /*   @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        int orientation = getScreenOrientation();
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE && !isLandscape){
            isLandscape = true;
            isPortrait = false;
//        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
//            mExoPlayer.ful
            Toast.makeText(getActivity(), "Landscape Mode", Toast.LENGTH_SHORT).show();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vvPlayer.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            vvPlayer.setLayoutParams(params);
            llDescriptionContainer.setVisibility(View.GONE);
            llNavigationBar.setVisibility(View.GONE);
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT && !isPortrait){
            isPortrait = true;
            isLandscape = false;
            Toast.makeText(getActivity(), "Portrait Mode", Toast.LENGTH_SHORT).show();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vvPlayer.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = 200;
            vvPlayer.setLayoutParams(params);
            llDescriptionContainer.setVisibility(View.VISIBLE);
            llNavigationBar.setVisibility(View.VISIBLE);
        }

    }*/

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(mExoPlayer != null) {
            if (isVisibleToUser) {
                String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(steps.videoURL), new DefaultDataSourceFactory(getActivity(),userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
//                mExoPlayer.setPlayWhenReady(false);
                mExoPlayer.setPlayWhenReady(true);
            }
            else{
                mExoPlayer.setPlayWhenReady(false);
                mExoPlayer.stop();
                mExoPlayer.release();
                mExoPlayer = null;
            }
        }
    }

    public int getScreenOrientation()
    {
        Display getOrient = getActivity().getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if(getOrient.getWidth()==getOrient.getHeight()){
            orientation = Configuration.ORIENTATION_SQUARE;
        } else{
            if(getOrient.getWidth() < getOrient.getHeight()){
                orientation = Configuration.ORIENTATION_PORTRAIT;
            }else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }
}
