package cool.android.english.activity;

import android.os.Bundle;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;

import java.util.List;

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
        getPermission();
    }

    public void getPermission() {
        PermissionUtils.permission(PermissionConstants.CAMERA)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(final ShouldRequest shouldRequest) {
                        shouldRequest.again(true);
                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
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

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                    }
                }).request();
    }
}
