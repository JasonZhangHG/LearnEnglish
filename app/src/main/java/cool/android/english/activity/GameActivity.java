package cool.android.english.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.KeyboardUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cool.android.english.R;
import cool.android.english.base.BaseActivity;
import cool.android.english.base.ICallback;
import cool.android.english.dialog.OpenErrorDialog;
import cool.android.english.utils.ActivityUtil;
import cool.android.english.utils.ToastHelper;

public class GameActivity extends BaseActivity {

    @BindView(R.id.edt_pwd)
    EditText mPwdEditText;
    @BindView(R.id.btn_login)
    Button mLoginButton;
    @BindView(R.id.ll_input)
    LinearLayout mInputAllView;

    private String mNetPwd;
    private boolean isLoginSuccess;

    private OpenErrorDialog mOpenErrorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        readTxt(null);
    }

    public void readTxt(final ICallback<String> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*
                     * 通过URL取得HttpURLConnection 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
                     * <uses-permission android:name="android.permission.INTERNET" />
                     */
                    URL url = new URL("http://f.fumani.net/km/1.txt");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(60 * 1000);
                    conn.setReadTimeout(60 * 1000);
                    // 取得inputStream，并进行读取
                    InputStream input = conn.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(input));
                    String line = null;
                    StringBuffer sb = new StringBuffer();
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    mNetPwd = sb.toString();
                    Log.i("GameActivity", "netPwd success : " + sb.toString());
                    if (callback != null) {
                        callback.onResult(mNetPwd);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("GameActivity", "netPwd 222 : " + e);
                    if (callback != null) {
                        callback.onResult("");
                    }
                }
            }
        }).start();
    }

    @OnClick(R.id.btn_login)
    public void loginClicked() {
        final String inputString = mPwdEditText.getText().toString();
        if (TextUtils.isEmpty(inputString)) {
            ToastHelper.showShortMessage("密码不能为空");
        } else {
            if (TextUtils.isEmpty(mNetPwd)) {
                readTxt(new ICallback<String>() {
                    @Override
                    public void onResult(final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (TextUtils.isEmpty(s)) {
                                    ToastHelper.showShortMessage("获取网络密码失败");
                                } else {
                                    if (mNetPwd.equals(inputString)) {
                                        ToastHelper.showShortMessage("登录成功");
                                        mPwdEditText.setText("");
                                        isLoginSuccess = true;
                                        mInputAllView.setVisibility(View.GONE);
                                        KeyboardUtils.hideSoftInput(mPwdEditText);
                                    } else {
                                        ToastHelper.showShortMessage("密码错误");
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });
            } else {
                if (mNetPwd.equals(inputString)) {
                    ToastHelper.showShortMessage("登录成功");
                    isLoginSuccess = true;
                    mInputAllView.setVisibility(View.GONE);
                    mPwdEditText.setText("");
                    KeyboardUtils.hideSoftInput(mPwdEditText);
                } else {
                    ToastHelper.showShortMessage("密码错误");
                }
            }
        }
    }

    @OnClick({R.id.tv_01, R.id.tv_02, R.id.tv_03, R.id.tv_04, R.id.tv_05, R.id.tv_06, R.id.tv_07, R.id.tv_08, R.id.tv_09,
            R.id.tv_10, R.id.tv_11, R.id.tv_12, R.id.tv_13, R.id.tv_14, R.id.tv_15, R.id.tv_16, R.id.tv_17, R.id.tv_18,
            R.id.tv_19, R.id.tv_20, R.id.tv_21, R.id.tv_22, R.id.tv_23, R.id.tv_24, R.id.tv_99})
    public void onItemClicked() {
        if (isLoginSuccess) {
            ToastHelper.showShortMessage("正在写入，请稍等");
            mInputAllView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showOpenErrorDialog();
                }
            }, 3000);
        } else {
            ToastHelper.showShortMessage("请先登录");
        }
    }

    @OnClick(R.id.tv_introduce)
    public void onIntroduceClicked() {
        ActivityUtil.intentToWebViewActivity(this, "http://f.fumani.net/km/1.html");
    }

    @OnClick(R.id.tv_exit)
    public void onExitClicked() {
        finish();
    }

    public void showOpenErrorDialog() {
        if (mOpenErrorDialog == null) {
            mOpenErrorDialog = new OpenErrorDialog();
        }
        mOpenErrorDialog.tryShow(getSupportFragmentManager());
    }
}