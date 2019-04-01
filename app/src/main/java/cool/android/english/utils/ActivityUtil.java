package cool.android.english.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import cn.bmob.v3.BmobUser;
import cool.android.english.R;
import cool.android.english.activity.BookReaderActivity;
import cool.android.english.activity.LoginActivity;
import cool.android.english.activity.PlayListenerActivity;
import cool.android.english.activity.PlayVideoActivity;
import cool.android.english.activity.WebViewActivity;
import cool.android.english.base.CCApplication;
import cool.android.english.bean.Book;
import cool.android.english.bean.Listener;
import cool.android.english.bean.VideoBean;
import cool.android.english.constants.AppConstant;

public class ActivityUtil {

    public static boolean isFinishing(Activity activity) {
        return (activity == null || activity.isFinishing());
    }

    public static void startActivity(Activity activity, Class targetClass) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);
    }

    public static void startBookReaderActivity(Fragment fragment, Book book) {
        if (fragment == null || fragment.getActivity() == null) { return; }
        Intent intent = new Intent(fragment.getActivity(), BookReaderActivity.class);
        intent.putExtra(AppConstant.IntentKey.EXTRA_DATA, book);
        fragment.startActivity(intent);
        fragment.getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.slide_in_from_middle_to_middle);
    }

    public static void startPlayListenerActivity(Fragment fragment, Listener listener) {
        if (fragment == null || fragment.getActivity() == null) { return; }
        Intent intent = new Intent(fragment.getActivity(), PlayListenerActivity.class);
        intent.putExtra(AppConstant.IntentKey.EXTRA_DATA, listener);
        fragment.startActivity(intent);
        fragment.getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.slide_in_from_middle_to_middle);
    }

    public static void startPlayVideoActivity(Fragment fragment, VideoBean videoBean) {
        if (fragment == null || fragment.getActivity() == null) { return; }
        Intent intent = new Intent(fragment.getActivity(), PlayVideoActivity.class);
        intent.putExtra(AppConstant.IntentKey.EXTRA_DATA, videoBean);
        fragment.startActivity(intent);
        fragment.getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.slide_in_from_middle_to_middle);
    }

    public static void startLoginActivity(Activity activity) {
        BmobUser.logOut();
        CurrentUserHelper.getInstance().updateCurrentUser(null);
        if (activity == null) {
            Intent intent = new Intent(CCApplication.getInstance(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            CCApplication.getInstance().startActivity(intent);
        } else {
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    public static void intentToWebViewActivity(Activity activity, String url) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(AppConstant.IntentKey.INTENT_TO_WEBVIEW_ACTIVITY_WITH_URL, url);
        activity.startActivity(intent);
    }
}
