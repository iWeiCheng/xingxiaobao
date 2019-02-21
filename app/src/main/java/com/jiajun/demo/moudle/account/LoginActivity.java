package com.jiajun.demo.moudle.account;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jiajun.demo.R;
import com.jiajun.demo.base.BaseActivity;
import com.jiajun.demo.model.BaseBean;
import com.jiajun.demo.moudle.account.entities.LoginBean;
import com.jiajun.demo.moudle.main.MainsActivity;
import com.jiajun.demo.moudle.webview.WebViewActivity;
import com.jiajun.demo.network.BaseObserver;
import com.jiajun.demo.network.Network;
import com.jiajun.demo.util.RandomNum;
import com.jiajun.demo.util.SignatureUtil;
import com.jiajun.demo.util.ToastUtil;
import com.jiajun.demo.views.ClearEditText;
import com.jiajun.demo.views.PassWordEditText;
import com.orhanobut.logger.Logger;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by hgj on 2016/7/21 0021.
 * 登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout layout;
    private Button loginBt;
    private TextView registerTv;
    private TextView forgetPwTv;
    private TextView remember_pwd;
    private String phone, password; // 电话 密码
    private ClearEditText nameEt;
    private PassWordEditText passWordEt;
    private boolean isRemember = false;
    private String type;
    private TextView app_name;
    private TextView tv_guide;


    private Subscription mSubscription;

    private BaseObserver<LoginBean> observer = new BaseObserver<LoginBean>() {

        @Override
        public void onSuccess(LoginBean bean) {
            dismissDialog();
            Set<String> tags = new HashSet<>();
            tags.add(bean.getCompanyId());
            Logger.e("alias:" + bean.getUserId() + "tags:" + tags.toString());
            JPushInterface.setTags(getContext(), 0, tags);
//            JPushInterface.setAlias(getContext(),0,bean.getUserId());
            JPushInterface.setAlias(getContext(), bean.getUserId(),
                    new TagAliasCallback() {
                        @Override
                        public void gotResult(int arg0, String arg1,
                                              Set<String> arg2) {
                            Logger.e("setAlias:如果code=0,说明别名设置成功:" + arg0);
                        }
                    });
            YSFUserInfo userInfo = new YSFUserInfo();
            userInfo.userId = bean.getUserId();
            userInfo.data = userInfoData(bean.getUserName() + "-" + bean.getCompanyName(), bean.getMobile()).toJSONString();
            Unicorn.setUserInfo(userInfo);
            SharedPreferences.Editor editor = preferences.edit();
            preferences.edit().putString("user_id", bean.getUserId()).apply();
            editor.putString("phone", nameEt.getText().toString()).apply();
            editor.putString("pwd", password).apply();
            editor.putString("distance", bean.getDistanceMetre()).apply();
            editor.putBoolean("isRemember", isRemember).apply();
            Intent intent = new Intent(getContext(), MainsActivity.class);
            getContext().startActivity(intent);
            finish();
        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {
            dismissDialog();
            ToastUtil.showToast(getContext(), message);
        }

        @Override
        public void networkError(Throwable e) {
            dismissDialog();
            Logger.e(e.getMessage());
            ToastUtil.showToast(getContext(), getString(R.string.empty_network_error));
        }
    };


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void loadLayout() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        // 加上这句设置为全屏 不加则只隐藏title
        setContentView(R.layout.account_login_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void initPresenter() {
        unsubscribe();
    }

    @Override
    protected void getExtra(@NonNull Bundle bundle) {
        super.getExtra(bundle);
        type = bundle.getString("type");
    }

    @Override
    protected int getTranslucentColor() {
        return R.color.transparent;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        isRemember = preferences.getBoolean("isRemember", false);
        layout = findViewById(R.id.layout);
        loginBt = findViewById(R.id.loginButton);
        registerTv = findViewById(R.id.register_textView);
        app_name = findViewById(R.id.tv_app_name);
        forgetPwTv = findViewById(R.id.forget_password_textView);
        remember_pwd = findViewById(R.id.remember_pwd);
        nameEt = findViewById(R.id.login_username);
        passWordEt = findViewById(R.id.login_password);
        tv_guide = findViewById(R.id.tv_guide);

        if (isRemember) {
            Drawable drawable = getResources().getDrawable(R.drawable.login_select);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            remember_pwd.setCompoundDrawables(drawable, null, null, null);
        }


    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        String phone = preferences.getString("phone", "");
        if (!phone.equals("")) {
            nameEt.setText(phone);
        }
        if (preferences.getBoolean("isRemember", false)) {
            String pwd = preferences.getString("pwd", "");
            if (!pwd.equals("")) {
                passWordEt.setText(pwd);
            }
        }
//        getVersion();
    }


    @Override
    protected void setListener(Bundle savedInstanceState) {
        super.setListener(savedInstanceState);
        remember_pwd.setOnClickListener(this);
        loginBt.setOnClickListener(this);
        registerTv.setOnClickListener(this);
        forgetPwTv.setOnClickListener(this);
        layout.setOnClickListener(this);
        tv_guide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == remember_pwd) {
            if (!isRemember) {
                Drawable drawable = getResources().getDrawable(R.drawable.login_select);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                remember_pwd.setCompoundDrawables(drawable, null, null, null);
                isRemember = true;
            } else {
                Drawable drawable = getResources().getDrawable(R.drawable.login_unselect);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                remember_pwd.setCompoundDrawables(drawable, null, null, null);
                isRemember = false;
            }
        }

        if (v == loginBt) {
            // 登录操作
            phone = nameEt.getText().toString();
            password = passWordEt.getText().toString();
            showProgressDialog();
            String str_random = RandomNum.getrandom();
            Map<String, String> map = new HashMap<>();
            map.put("method", "login");
            map.put("username", phone);
            map.put("password", password);
            map.put("versionName", getResources().getString(R.string.versionName));
            map.put("random", str_random);
            String str_signature = SignatureUtil.getSignature(map);
            map.put("signature", str_signature);

            mSubscription = Network.login().login(Network.WORK_URL, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);

        } else if (v == registerTv) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if (v == forgetPwTv) {
            Intent intent = new Intent(this, ForgetPasswordActivity.class);
            startActivity(intent);
        } else if (v == layout) {
            hideIM(nameEt, this);
        } else if (v == tv_guide) {
            Intent intent = new Intent(this,WebViewActivity.class);
            intent.putExtra("url","http://www.implus100.com/agent/interface/app/operationalGuidelines.jsp");
            startActivity(intent);
        }
    }

    private JSONArray userInfoData(String name, String mobile) {
        JSONArray array = new JSONArray();
        array.add(userInfoDataItem("real_name", name, false, -1, null, null)); // name
        array.add(userInfoDataItem("mobile_phone", mobile, false, -1, null, null)); // mobile
        return array;
    }

    private JSONObject userInfoDataItem(String key, Object value, boolean hidden, int index, String label, String href) {
        JSONObject item = new JSONObject();
        try {
            item.put("key", key);
            item.put("value", value);
            if (hidden) {
                item.put("hidden", true);
            }
            if (index >= 0) {
                item.put("index", index);
            }
            if (!TextUtils.isEmpty(label)) {
                item.put("label", label);
            }
            if (!TextUtils.isEmpty(href)) {
                item.put("href", href);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return item;
    }


}
