package com.hunau.feed_android.monitoring;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hunau.feed_android.R;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Vitamio视频播放框架Demo
 */
public class MonitoringActivity extends Fragment implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener{
    //视频地址
    private String path = "rtmp://47.94.238.110:1935/hls/test";/*"rtmp://rtmp01open.ys7.com/openlive/540cb65fc2d44910be8da208f7628781.hd";*/
    private Uri uri;
    private ProgressBar pb;
    private TextView downloadRateView, loadRateView;
    private CustomMediaController mCustomMediaController;
    private VideoView mVideoView;
    View a;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        a=inflater.inflate(R.layout.fragment_discover, container, false);
        return a;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = MonitoringActivity.this.getActivity().getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        //必须写这个，初始化加载库文件
        Vitamio.isInitialized(this.getActivity());
        //设置视频解码监听
//        if (!LibsChecker.checkVitamioLibs(this)) {
//            return;
//        }
        mVideoView = (VideoView) a.findViewById(R.id.buffer);
        mCustomMediaController=new CustomMediaController(this.getActivity(),mVideoView,this.getActivity());
        /* mCustomMediaController.setVideoName("白火锅 x 红火锅");*/
        pb = (ProgressBar) a.findViewById(R.id.probar);
        downloadRateView = (TextView) a.findViewById(R.id.download_rate);
        loadRateView = (TextView) a.findViewById(R.id.load_rate);
        initData();
    }

    //初始化控件
    private void initView() {

    }

    //初始化数据
    private void initData() {
        uri = Uri.parse(path);
        mVideoView.setVideoURI(uri);//设置视频播放地址
        mCustomMediaController.show(5000);
        mVideoView.setMediaController(mCustomMediaController);
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//高画质
        mVideoView.requestFocus();
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnBufferingUpdateListener(this);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    pb.setVisibility(View.VISIBLE);
                    downloadRateView.setText("");
                    loadRateView.setText("");
                    downloadRateView.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);

                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mVideoView.start();
                pb.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRateView.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRateView.setText(percent + "%");
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //屏幕切换时，设置全屏
        if (mVideoView != null){
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);
    }

}
