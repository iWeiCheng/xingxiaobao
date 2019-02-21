package com.jiajun.demo.moudle.licence_recognize;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.cloudapi.sdk.model.ApiResponse;
import com.jiajun.demo.R;
import com.jiajun.demo.base.BaseActivity;
import com.jiajun.demo.moudle.licence_recognize.LicenceRecognize;
import com.jiajun.demo.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 车牌识别结果
 * Created by dan on 2018/5/25/025.
 */

public class RecognizeResponseActivity extends BaseActivity {

    @BindView(R.id.titleLeft_textView)
    TextView titleLeftTextView;
    @BindView(R.id.titleCeneter_textView)
    TextView titleCeneterTextView;
    @BindView(R.id.edt_number)
    EditText edtNumber;

    private String filePath;

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_recognize_response);
    }

    @Override
    protected boolean supportEventBus() {
        return true;
    }

    @Override
    protected void getExtra(@NonNull Bundle bundle) {
        super.getExtra(bundle);
        filePath = bundle.getString("filePath");
    }

    @Override
    public void initPresenter() {
        titleLeftTextView.setVisibility(View.VISIBLE);
        titleCeneterTextView.setText("识别结果");
        showProgressDialog();
        LicenceRecognize.recognize(filePath);
    }

    /**
     * 识别车牌结果
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getRecognizeResponse(ApiResponse response) {
        dismissDialog();
        if (response.getCode() == 200) {
            try {
                JSONArray array = new JSONObject(new String(response.getBody())).getJSONArray("plates");
                JSONObject object = array.getJSONObject(0);
                String plate = object.getString("txt");
                edtNumber.setText(plate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.showToast(getContext(), "识别失败");
        }
    }

    @OnClick({R.id.btn_submit, R.id.titleLeft_textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                EventBus.getDefault().post(new LicenceEvent(edtNumber.getText().toString()));
                finish();
                break;
            case R.id.titleLeft_textView:
                finish();
                break;
        }

    }
}
