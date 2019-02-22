package cool.android.english.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import cool.android.english.R;
import cool.android.english.activity.BookReaderActivity;
import cool.android.english.activity.PlayListenerActivity;
import cool.android.english.bean.Book;
import cool.android.english.bean.Listener;
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
}
