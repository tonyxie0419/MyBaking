package com.example.android.mybaking.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mybaking.widget.BakingService;
import com.example.android.mybaking.R;
import com.example.android.mybaking.data.Step;
import com.example.android.mybaking.utilities.Utils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * Created by xie on 2017/12/28.
 */

public class RecipeDetailInfoFragment extends Fragment {

    private static final String TAG = RecipeDetailInfoFragment.class.getSimpleName();

    private int stepIndex;
    private ArrayList<Step> steps;

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;

    private TextView noVideoDisplay;

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChange;

    public RecipeDetailInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail_info, container, false);

        final AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);

        if (audioManager != null) {
            mAudioFocusChange = new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    switch (focusChange) {
                        case AudioManager.AUDIOFOCUS_LOSS:
                            //长时间丢失焦点,当其他应用申请的焦点为AUDIOFOCUS_GAIN时，
                            //会触发此回调事件，例如播放QQ音乐，网易云音乐等
                            //通常需要暂停音乐播放，若没有暂停播放就会出现和其他音乐同时输出声音
                            Log.d(TAG, "onAudioFocusChange: AudioManager.AUDIOFOCUS_LOSS");
                            if (mExoPlayer != null) {
                                mExoPlayer.setPlayWhenReady(false);
                            }
                            audioManager.abandonAudioFocus(mAudioFocusChange);
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                            Log.d(TAG, "onAudioFocusChange: AudioManager.AUDIOFOCUS_LOSS_TRANSIENT");
                            //短暂性丢失焦点，当其他应用申请AUDIOFOCUS_GAIN_TRANSIENT或AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE时，
                            //会触发此回调事件，例如播放短视频，拨打电话等。
                            //通常需要暂停音乐播放
                            if (mExoPlayer != null) {
                                mExoPlayer.setPlayWhenReady(false);
                            }
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                            //短暂性丢失焦点并作降音处理
                            Log.d(TAG, "onAudioFocusChange: AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:");
                            mExoPlayer.setPlayWhenReady(false);
                            break;
                        case AudioManager.AUDIOFOCUS_GAIN:
                            //当其他应用申请焦点之后又释放焦点会触发此回调
                            //可重新播放音乐
                            Log.d(TAG, "onAudioFocusChange: AudioManager.AUDIOFOCUS_GAIN:");
                            mExoPlayer.setPlayWhenReady(true);
                            break;
                    }
                }
            };
            audioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }

        mPlayerView = rootView.findViewById(R.id.playerView);
        noVideoDisplay = rootView.findViewById(R.id.tv_no_video_display);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Utils.isPad(getContext())) {
                TextView displayStepInstruction = rootView.findViewById(R.id.tv_recipe_step_instruction);
                if (steps != null) {
                    Step currentStep = steps.get(stepIndex);
                    BakingService.startActionUpdateBakingWidgets(getContext(), steps, stepIndex);
                    displayStepInstruction.setText(currentStep.getDescription());
                    String videoUrl = currentStep.getVideoURL();
                    if (!TextUtils.isEmpty(videoUrl)) {
                        showVideo();
                        initializePlayer(videoUrl);
                    }
                }
            } else {
                if (steps != null) {
                    Step currentStep = steps.get(stepIndex);
                    BakingService.startActionUpdateBakingWidgets(getContext(), steps, stepIndex);
                    String videoUrl = currentStep.getVideoURL();
                    if (!TextUtils.isEmpty(videoUrl)) {
                        showVideo();
                        initializePlayer(videoUrl);
                    } else {
                        showNoVideoDisplay();
                    }
                }
            }
        } else if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            TextView displayStepInstruction = rootView.findViewById(R.id.tv_recipe_step_instruction);
            if (steps != null) {
                Step currentStep = steps.get(stepIndex);
                BakingService.startActionUpdateBakingWidgets(getContext(), steps, stepIndex);
                displayStepInstruction.setText(currentStep.getDescription());
                String videoUrl = currentStep.getVideoURL();
                if (!TextUtils.isEmpty(videoUrl)) {
                    showVideo();
                    initializePlayer(videoUrl);
                }else{
                    showNoVideoDisplay();
                }
            }
        }
        return rootView;
    }

    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    private void initializePlayer(String url) {
        Log.d(TAG, "initializePlayer: " + Uri.parse(url));
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    getContext(), Util.getUserAgent(getContext(), "MyBaking"), bandwidthMeter);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(
                    Uri.parse(url),
                    dataSourceFactory,
                    extractorsFactory,
                    null,
                    null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
        mExoPlayer = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        BakingService.startActionUpdateBakingWidgets(getContext(), steps, stepIndex);
    }

    private void showNoVideoDisplay() {
        mPlayerView.setVisibility(View.INVISIBLE);
        noVideoDisplay.setVisibility(View.VISIBLE);
    }

    private void showVideo() {
        mPlayerView.setVisibility(View.VISIBLE);
        noVideoDisplay.setVisibility(View.INVISIBLE);
    }
}
