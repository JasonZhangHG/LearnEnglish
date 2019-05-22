package cool.android.english.activity;

import android.os.Bundle;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cool.android.english.R;
import cool.android.english.base.BaseActivity;
import cool.android.english.bean.CurrentUser;
import cool.android.english.utils.CurrentUserHelper;


public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Bmob.initialize(this, "50fce2799c4f7c973de087d7b2cf6f37");
        doInUI(new Runnable() {
            @Override
            public void run() {
                if (BmobUser.isLogin()) {
                    CurrentUser currentUser = BmobUser.getCurrentUser(CurrentUser.class);
                    CurrentUserHelper.getInstance().updateCurrentUser(currentUser);
                    toActivity(MainActivity.class);
                    finish();
                } else {
                    toActivity(LoginActivity.class);
                    finish();
                }
            }
        }, 1000);

    }
}
