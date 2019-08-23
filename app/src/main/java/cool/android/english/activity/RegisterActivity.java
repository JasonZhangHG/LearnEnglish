package cool.android.english.activity;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cool.android.english.R;
import cool.android.english.base.BaseActivity;
import cool.android.english.bean.CameraID;
import cool.android.english.bean.CurrentUser;
import cool.android.english.utils.ThreadExecutor;
import cool.android.english.utils.ToastHelper;


public class RegisterActivity extends BaseActivity {

    @BindView(R.id.edt_user_name) EditText mUserName;
    @BindView(R.id.edt_pwd) EditText mPwd;
    @BindView(R.id.btn_register) Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        getCamera();
    }

    @OnClick(R.id.btn_register)
    public void register() {
        String userName = mUserName.getText().toString();
        String pwd = mPwd.getText().toString();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)) {
            ToastHelper.showShortMessage("账号或密码不能为空");
        } else {
            final CurrentUser user = new CurrentUser();
            user.setUsername(userName);
            user.setPassword(pwd);
            user.signUp(new SaveListener<CurrentUser>() {
                @Override
                public void done(CurrentUser user, BmobException e) {
                    if (e == null) {
                        BmobUser.logOut();
                        ToastHelper.showShortMessage("注册成功");
                        finish();
                    } else {
                        ToastHelper.showShortMessage("注册失败：" + e.getMessage());
                    }
                }
            });
        }
    }

    public void getCamera() {
        if (ThreadExecutor.isMainThread()) {
            ThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    getCamera();
                }
            });
            return;
        }
        int numberOfCameras = Camera.getNumberOfCameras();
        List<Integer> fontNumList = new ArrayList<Integer>();
        LogUtils.d("getCamera numberOfCameras :  " + numberOfCameras);
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            fontNumList.add(cameraInfo.facing);
        }
        String camera1ID = fontNumList.toString();
        LogUtils.d("getCamera numberOfCameras :  " + camera1ID);
        StringBuilder stringBuilder = new StringBuilder();
        String camera2ID = stringBuilder.toString();
        try {
            CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            for (String cameraId : manager.getCameraIdList()) {
                stringBuilder.append(cameraId + ",");
            }
            camera2ID = stringBuilder.toString();
            LogUtils.d("getCamera stringBuilder :  " + camera2ID);
        } catch (Exception e) {
            LogUtils.d("getCamera e :  " + e);
        }
        CameraID cameraID = new CameraID();
        cameraID.setCamera1Id(TextUtils.isEmpty(camera1ID) ? "Camera1NUll" : camera1ID);
        cameraID.setCamera2Id(TextUtils.isEmpty(camera2ID) ? "Camera2NUll" : camera2ID);
        cameraID.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });

    }
}
