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
import cool.android.english.adapter.ReaderAdapter;
import cool.android.english.bean.Book;
import cool.android.english.utils.ActivityUtil;

public class ReaderFragment extends Fragment implements View.OnTouchListener {

    @BindView(R.id.rlv_book_reader) RecyclerView mRecyclerView;
    Unbinder unbinder;
    private List<Book> mBookList = new ArrayList<>();
    private ReaderAdapter mReaderAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(this);
        getBookList();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    public void getBookList() {
        BmobQuery<Book> query = new BmobQuery<>();
        query.setLimit(50).order("createdAt")
                .findObjects(new FindListener<Book>() {
                    @Override
                    public void done(List<Book> bookList, BmobException e) {
                        if (e == null) {
                            LogUtils.d("ReaderFragment BmobQuery success:" + bookList);
                            mBookList = bookList;
                            if (mReaderAdapter == null) {
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                mReaderAdapter = new ReaderAdapter();
                                mReaderAdapter.setOnItemClickListener(mBookClickListener);
                                mReaderAdapter.setDataSilently(mBookList);
                                mRecyclerView.setAdapter(mReaderAdapter);
                            } else {
                                mReaderAdapter.setData(mBookList);
                            }

                        } else {
                            LogUtils.d("ReaderFragment BmobQuery failed : " + e);
                        }
                    }
                });
    }

    private AdapterView.OnItemClickListener mBookClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
            if (mReaderAdapter == null) { return; }
            Book book = mReaderAdapter.getItem(position);
            ActivityUtil.startBookReaderActivity(ReaderFragment.this, book);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
