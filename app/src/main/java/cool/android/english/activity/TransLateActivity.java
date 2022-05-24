package cool.android.english.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cool.android.english.R;
import cool.android.english.base.BaseActivity;
import cool.android.english.bean.TranslateResponse;
import cool.android.english.http.ApiEndpointClient;
import cool.android.english.utils.MD5Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransLateActivity extends BaseActivity {

    @BindView(R.id.edt_translate)
    EditText mTranslateEditText;
    @BindView(R.id.btn_translate)
    Button mTranslateButton;
    @BindView(R.id.tv_translate_result)
    TextView mTranslateResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_late);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_translate)
    public void translateClicked() {
        String inputText = mTranslateEditText.getText().toString();
        String from = "zh";
        String to = "en";
        String appid = "20190213000266387";
        String scrientID = "veKhArlI7EdZDD_WKTti";
        String salt = "1435660288";
        String unSign = appid + inputText + salt + scrientID;
        String sign = MD5Util.md5Encode(unSign);
        LogUtils.d("translateClicked  sign ： " + sign);
        ApiEndpointClient.getEndpointV2().translate(inputText, from, to, appid, salt, sign)
                .enqueue(new Callback<TranslateResponse>() {
                    @Override
                    public void onResponse(Call<TranslateResponse> call, Response<TranslateResponse> response) {
                        TranslateResponse translateResponse = response.body();
                        LogUtils.d("translateClicked  translate success translateResponse :" + translateResponse);
                        if (translateResponse != null) {
                            List<TranslateResponse.TransResultBean> transResult = translateResponse.getTransResult();
                            if (transResult != null && transResult.size() > 0) {
                                TranslateResponse.TransResultBean resultBean = transResult.get(0);
                                if (mTranslateResult != null) {
                                    mTranslateResult.setText(resultBean.getDst());
                                }
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<TranslateResponse> call, Throwable t) {
                        LogUtils.d("translateClicked  translate failed Throwable :" + t);
                    }
                });

    }
}