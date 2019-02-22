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
import cool.android.english.bean.Listener;
import cool.android.english.constants.AppConstant;

public class PlayListenerActivity extends BaseActivity {

    @BindView(R.id.tv_title_play_listener) TextView mTitlePlayListener;
    @BindView(R.id.tv_introduce_play_listener) TextView mIntroducePlayListener;
    @BindView(R.id.tv_cover_play_listener_activity) TextView mCoverPlayListenerActivity;
    private Listener mListener;
    private MediaPlayer mMediaPlayer;
    private int mClickCount;
    private boolean isOnPreparedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_listener);
        ButterKnife.bind(this);
        mListener = (Listener) getIntent().getSerializableExtra(AppConstant.IntentKey.EXTRA_DATA);
        LogUtils.d("PlayListenerActivity  mBook : " + mListener);
        if (mListener == null) {
            onBackPressed();
            return;
        }
        mTitlePlayListener.setText(mListener.getTitle());
        mIntroducePlayListener.setText(mListener.getIntroduce());
        playListener(mListener.getValue());
    }

    public void playListener(String url) {
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.setLooping(true);
            LogUtils.d("PlayListenerActivity playListener start url : " + url);
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    isOnPreparedListener = true;
                    LogUtils.d("PlayListenerActivity playListener setOnPreparedListener");
                    mMediaPlayer.start();

                }
            });
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    LogUtils.d("PlayListenerActivity playListener onCompletion");
                    mMediaPlayer.setLooping(true);
                    mMediaPlayer.start();
                }
            });

            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            LogUtils.d("PlayListenerActivity playListener Exception : " + e);
        }
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
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    @OnClick(R.id.tv_cover_play_listener_activity)
    public void onPauseClicked() {
        if (!isOnPreparedListener) {return;}
        mClickCount++;
        if (mClickCount % 2 == 1 && mMediaPlayer != null) {
            mMediaPlayer.pause();
        } else if (mMediaPlayer != null && isOnPreparedListener) {
            mMediaPlayer.start();
        }
    }
}
