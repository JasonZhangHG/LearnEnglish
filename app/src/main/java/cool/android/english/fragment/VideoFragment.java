package cool.android.english.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cool.android.english.R;
import cool.android.english.adapter.VideoAdapter;
import cool.android.english.bean.VideoBean;
import cool.android.english.utils.ActivityUtil;

public class VideoFragment extends Fragment implements View.OnTouchListener {

    @BindView(R.id.rlv_video_fragment) RecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;
    private List<VideoBean> mListenerList = new ArrayList<>();
    Unbinder unbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(this);
        getListenerList();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    public void getListenerList() {
        BmobQuery<VideoBean> query = new BmobQuery<>();
        query.setLimit(50).order("createdAt")
                .findObjects(new FindListener<VideoBean>() {
                    @Override
                    public void done(List<VideoBean> listenerList, BmobException e) {
                        if (e == null) {
                            LogUtils.d("ListenerFragment BmobQuery success:" + listenerList);
                            mListenerList = listenerList;
                            if (mVideoAdapter == null) {
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                mVideoAdapter = new VideoAdapter();
                                mVideoAdapter.setOnItemClickListener(mListenerClickListener);
                                mVideoAdapter.setDataSilently(mListenerList);
                                mRecyclerView.setAdapter(mVideoAdapter);
                            } else {
                                mVideoAdapter.setData(mListenerList);
                            }

                        } else {
                            LogUtils.d("ListenerFragment BmobQuery failed : " + e);
                        }
                    }
                });
    }

    private AdapterView.OnItemClickListener mListenerClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
            if (mVideoAdapter == null) { return; }
            VideoBean videoBean = mVideoAdapter.getItem(position);
            ActivityUtil.startPlayVideoActivity(VideoFragment.this, videoBean);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
