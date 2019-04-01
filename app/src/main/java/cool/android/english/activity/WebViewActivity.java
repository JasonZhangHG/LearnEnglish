package cool.android.english.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.MailTo;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import cool.android.english.R;
import cool.android.english.base.BaseActivity;
import cool.android.english.constants.AppConstant;
import cool.android.english.utils.ActivityUtil;
import cool.android.english.utils.DialogUtils;


public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wv_web_view_activity) WebView mWebView;
    private Dialog mProgressDialog;
    private String mURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent == null) {onBackPressed();}
        mURL = intent.getExtras().getString(AppConstant.IntentKey.INTENT_TO_WEBVIEW_ACTIVITY_WITH_URL);
        if (TextUtils.isEmpty(mURL)) {onBackPressed();}
        initWebView();
        initData();
    }

    private void initWebView() {
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setInitialScale(25);// 为25%，最小缩放等级
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (ActivityUtil.isFinishing(WebViewActivity.this)) {
                    return;
                }
                if (mProgressDialog == null) {
                    mProgressDialog = DialogUtils.getInstance().getProgressDialog(WebViewActivity.this);
                }

                if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                    mProgressDialog.show();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    if (!isFinishing()) {
                        mProgressDialog.dismiss();
                    }
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /*
                  兼容email发送
                 */
                if (url.startsWith("mailto:")) {
                    MailTo mt = MailTo.parse(url);
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{mt.getTo()});
                    i.putExtra(Intent.EXTRA_SUBJECT, mt.getSubject());
                    i.putExtra(Intent.EXTRA_CC, mt.getCc());
                    i.putExtra(Intent.EXTRA_TEXT, mt.getBody());
                    startActivity(i);
                    view.reload();
                    return true;
                } else {
                    return false;
                }
            }

        });
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    private void initData() {
        if (TextUtils.isEmpty(mURL)) {onBackPressed();}
        mWebView.loadUrl(mURL);
    }

    @Override
    protected void onDestroy() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = null;
        }
        super.onDestroy();
    }
}
