package cool.android.english.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cool.android.english.R;
import cool.android.english.base.BaseActivity;
import cool.android.english.bean.VideoBean;
import cool.android.english.constants.AppConstant;
import cool.android.english.view.CustomVideoView;

public class PlayVideoActivity extends BaseActivity {

    @BindView(R.id.cvd_video_play_activity) CustomVideoView mCustomVideoView;
    @BindView(R.id.tv_cover_play_video_activity) TextView mCoverPlay;
    private VideoBean videoBean;
    private int mClickCount;
    private boolean isOnPreparedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);
        videoBean = (VideoBean) getIntent().getSerializableExtra(AppConstant.IntentKey.EXTRA_DATA);
        LogUtils.d("PlayListenerActivity  mBook : " + videoBean);
        if (videoBean == null) {
            onBackPressed();
            return;
        }
        playVideo(videoBean.getValue());
    }

    public void playVideo(String url) {
        mCustomVideoView.setVideoPath(url);
        mCustomVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isOnPreparedListener = true;
                mCustomVideoView.start();

            }
        });
        mCustomVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mCustomVideoView.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.exit_stop_original_place, R.anim.exit_to_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.tv_cover_play_video_activity)
    public void onPauseClicked() {
        if (!isOnPreparedListener) {return;}
        mClickCount++;
        if (mClickCount % 2 == 1 && mCustomVideoView != null) {
            mCustomVideoView.pause();
        } else if (mCustomVideoView != null && isOnPreparedListener) {
            mCustomVideoView.start();
        }
    }
}
