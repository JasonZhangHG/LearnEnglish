package cool.android.english.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cool.android.english.R;
import cool.android.english.base.BaseActivity;
import cool.android.english.base.TabEntity;
import cool.android.english.fragment.ListenerFragment;
import cool.android.english.fragment.MeFragment;
import cool.android.english.fragment.ReaderFragment;
import cool.android.english.fragment.TranslateFragment;
import cool.android.english.fragment.VideoFragment;
import cool.android.english.utils.ToastHelper;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_layout_main_activity) CommonTabLayout mCommonTabLayout;
    @BindView(R.id.ll_main_activity) LinearLayout activityMain;

    private TranslateFragment mTranslateFragment;
    private ReaderFragment mReaderFragment;
    private ListenerFragment mListenerFragment;
    private VideoFragment mVideoFragment;
    private MeFragment mMeFragment;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private String[] mTitles = {"翻译", "阅读", "听力", "视频", "我的"};
    private int[] mIconUnselectIds = {R.drawable.icon_message_unpress, R.drawable.icon_job_unpress, R.drawable.icon_discover_unpress, R.drawable.icon_me_unpress, R.drawable.icon_me_unpress};
    private int[] mIconSelectIds = {R.drawable.icon_message_press, R.drawable.icon_job_press, R.drawable.icon_discover_press, R.drawable.icon_me_press, R.drawable.icon_me_press};
    private long firstBack = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //第一：默认初始化
        Bmob.initialize(this, "50fce2799c4f7c973de087d7b2cf6f37");
        initTab();
        initFragment();
    }

    private void initTab() {
        for (int i = 0; i < mIconSelectIds.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mCommonTabLayout.setTabData(mTabEntities);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    public void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (mTranslateFragment == null) {
            mTranslateFragment = new TranslateFragment();
            transaction.add(R.id.ll_main_activity, mTranslateFragment);
        }
        if (mReaderFragment == null) {
            mReaderFragment = new ReaderFragment();
            transaction.add(R.id.ll_main_activity, mReaderFragment);
        }
        if (mListenerFragment == null) {
            mListenerFragment = new ListenerFragment();
            transaction.add(R.id.ll_main_activity, mListenerFragment);
        }
        if (mVideoFragment == null) {
            mVideoFragment = new VideoFragment();
            transaction.add(R.id.ll_main_activity, mVideoFragment);
        }

        if (mMeFragment == null) {
            mMeFragment = new MeFragment();
            transaction.add(R.id.ll_main_activity, mMeFragment);
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        mCommonTabLayout.setCurrentTab(currentTabPosition);
    }

    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                transaction.show(mTranslateFragment);
                transaction.hide(mReaderFragment);
                transaction.hide(mListenerFragment);
                transaction.hide(mVideoFragment);
                transaction.hide(mMeFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                transaction.show(mReaderFragment);
                transaction.hide(mTranslateFragment);
                transaction.hide(mListenerFragment);
                transaction.hide(mVideoFragment);
                transaction.hide(mMeFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                transaction.show(mListenerFragment);
                transaction.hide(mTranslateFragment);
                transaction.hide(mReaderFragment);
                transaction.hide(mVideoFragment);
                transaction.hide(mMeFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 3:
                transaction.show(mVideoFragment);
                transaction.hide(mTranslateFragment);
                transaction.hide(mReaderFragment);
                transaction.hide(mListenerFragment);
                transaction.hide(mMeFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 4:
                transaction.show(mMeFragment);
                transaction.hide(mVideoFragment);
                transaction.hide(mTranslateFragment);
                transaction.hide(mReaderFragment);
                transaction.hide(mListenerFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstBack < 2000) {
            super.onBackPressed();
        } else {
            firstBack = System.currentTimeMillis();
            ToastHelper.showShortMessage(R.string.back_btn_exit_pop);
        }
    }

}