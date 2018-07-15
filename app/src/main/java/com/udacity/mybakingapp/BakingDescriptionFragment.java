package com.udacity.mybakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class BakingDescriptionFragment extends Fragment implements View.OnKeyListener{

    private SimpleExoPlayerView vvPlayer;
    private TextView tvStepDescription;
    private SimpleExoPlayer mExoPlayer;
    private BakingDataList.Steps steps;
    private LinearLayout llDescriptionContainer;
    private LinearLayout llNavigationBar;
    private boolean isLandscape = false;
    private boolean isPortrait = false;
    private boolean isStartVideo;
    private ImageView ivPlaceHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_baking_description, container, false);

        Bundle bundle = getArguments();
         steps = (BakingDataList.Steps) bundle.getSerializable(AppConstants.SELECTED_RECIPE);
         isStartVideo =  bundle.getBoolean(AppConstants.START_VIDEO,false);
         llDescriptionContainer= (LinearLayout) rootView.findViewById(R.id.llDescriptionContainer);
        llNavigationBar= (LinearLayout) rootView.findViewById(R.id.llNavigationBar);
        vvPlayer = (SimpleExoPlayerView) rootView.findViewById(R.id.vvPlayer);
        ivPlaceHolder = (ImageView) rootView.findViewById(R.id.ivPlaceHolder);
        if(TextUtils.isEmpty(steps.videoURL)){
            vvPlayer.setVisibility(View.INVISIBLE);
            ivPlaceHolder.setVisibility(View.VISIBLE);
        }else{
            initializePlayer();
        }

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
            if(isStartVideo)
                mExoPlayer.setPlayWhenReady(true);
            else
                mExoPlayer.setPlayWhenReady(false);
            vvPlayer.setPlayer(mExoPlayer);
        }
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(mExoPlayer != null) {
            if (isVisibleToUser) {
                isStartVideo = true;
                initializePlayer();
            }
            else{
                    isStartVideo = false;
                    mExoPlayer.setPlayWhenReady(false);
//                    mExoPlayer.stop();
//                    mExoPlayer.release();
//                    mExoPlayer = null;
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK )
        {
            isStartVideo = false;
            mExoPlayer.setPlayWhenReady(false);
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
            return true;
        }
        return false;
    }

}
