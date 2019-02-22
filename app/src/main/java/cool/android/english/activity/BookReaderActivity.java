package cool.android.english.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cool.android.english.R;
import cool.android.english.base.BaseActivity;
import cool.android.english.bean.Book;
import cool.android.english.constants.AppConstant;
import cool.android.english.utils.ResourceUtil;

public class BookReaderActivity extends BaseActivity {

    @BindView(R.id.tv_text_value_read_book_activity) TextView mBookValue;
    @BindView(R.id.btn_chang_night_read_book_activity) Button mChangNight;
    @BindView(R.id.btn_chang_day_read_book_activity) Button mChangDay;
    @BindView(R.id.rv_book_reader) RelativeLayout rvBookReader;
    @BindView(R.id.tv_title_read_book_activity) TextView mTitle;
    @BindView(R.id.tv_writer_read_book_activity) TextView mWriter;
    @BindView(R.id.btn_chang_bg_color_read_book_activity) Button btnChangBgColorReadBookActivity;
    private Book mBook;
    private int changBGClickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        ButterKnife.bind(this);
        mBook = (Book) getIntent().getSerializableExtra(AppConstant.IntentKey.EXTRA_DATA);
        LogUtils.d("BookReaderActivity  mBook : " + mBook);
        if (mBook == null) {
            onBackPressed();
            return;
        }
        mTitle.setText(mBook.getTitle());
        mWriter.setText(mBook.getWriter());
        mBookValue.setText(mBook.getValue());
    }

    @OnClick(R.id.btn_chang_night_read_book_activity)
    public void changNight() {
        rvBookReader.setBackgroundColor(ResourceUtil.getColor(R.color.black60));
    }

    @OnClick(R.id.btn_chang_day_read_book_activity)
    public void changDay() {
        rvBookReader.setBackgroundColor(ResourceUtil.getColor(R.color.white));
    }

    @OnClick(R.id.btn_chang_bg_color_read_book_activity)
    public void changBGColorClicked() {
        if (changBGClickCount == 0) {
            rvBookReader.setBackgroundColor(ResourceUtil.getColor(R.color.video_manage_activity_select_all_text_color));
            changBGClickCount++;
        } else if (changBGClickCount == 1) {
            rvBookReader.setBackgroundColor(ResourceUtil.getColor(R.color.green3));
            changBGClickCount++;
        } else if (changBGClickCount == 2) {
            rvBookReader.setBackgroundColor(ResourceUtil.getColor(R.color.attention_others_activity_log_in_text_color));
            changBGClickCount++;
        } else if (changBGClickCount == 3) {
            rvBookReader.setBackground(ResourceUtil.getDrawable(R.drawable.welecome_bg));
            changBGClickCount++;
        } else if (changBGClickCount == 4) {
            rvBookReader.setBackgroundColor(ResourceUtil.getColor(R.color.white));
            changBGClickCount = 0;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.exit_stop_original_place, R.anim.exit_to_right);
    }

}
