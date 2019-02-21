package com.jiajun.demo.moudle.me;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.caijia.selectpicture.bean.MediaBean;
import com.caijia.selectpicture.ui.SelectMediaActivity;
import com.caijia.selectpicture.utils.MediaType;
import com.jiajun.demo.R;
import com.jiajun.demo.base.BaseActivity;
import com.jiajun.demo.model.BaseBean;
import com.jiajun.demo.moudle.account.LoginActivity;
import com.jiajun.demo.moudle.me.entities.HeadImgsBean;
import com.jiajun.demo.moudle.me.entities.LogoutBean;
import com.jiajun.demo.moudle.me.entities.PersonalBean;
import com.jiajun.demo.moudle.webview.WebViewActivity;
import com.jiajun.demo.network.BaseObserver;
import com.jiajun.demo.network.Network;
import com.jiajun.demo.util.RandomNum;
import com.jiajun.demo.util.SignatureUtil;
import com.jiajun.demo.util.ToastUtil;
import com.orhanobut.logger.Logger;
import com.qiyukf.unicorn.api.Unicorn;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.jiajun.demo.network.Network.WORK_URL;

/**
 * 个人中心
 * Created by dan on 2017/11/29/029.
 */

@RuntimePermissions
public class PersonalInfoActivity extends BaseActivity {
    @BindView(R.id.titleCeneter_textView)
    TextView titleCeneterTextView;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.id_number)
    TextView idNumber;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.group)
    TextView group;
    @BindView(R.id.qrcode)
    ImageView qrcode;
    @BindView(R.id.update)
    TextView update;
    @BindView(R.id.titleLeft_textView)
    TextView titleLeftTextView;
    @BindView(R.id.qrcode_layout)
    LinearLayout qrcodeLayout;
    @BindView(R.id.logout)
    Button logout;

    private int SELECT_MEDIA_RQ = 101;

    private PersonalBean personalBean;

    private Subscription mSubscription;

    private BaseObserver<PersonalBean> observer = new BaseObserver<PersonalBean>() {

        @Override
        public void onSuccess(PersonalBean bean) {
            dismissDialog();
            personalBean = bean;
            Glide.with(getContext())
                    .load(bean.getHeadImg())
                    .into(icon);
            name.setText(bean.getName());
            mobile.setText(bean.getMobile());
            email.setText(bean.getEmail());
            idNumber.setText(bean.getCard_no());
            group.setText(String.format("%s-%s", bean.getOrganizationName(), bean.getCompanyName()));
        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {
            dismissDialog();
            Logger.e("error:" + message);
            ToastUtil.showToast(getContext(),message);
        }

        @Override
        public void networkError(Throwable e) {
            dismissDialog();
            Logger.e(e.getMessage());
            ToastUtil.showToast(getContext(),"网络错误");
        }
    };

    private BaseObserver<HeadImgsBean> observer_img = new BaseObserver<HeadImgsBean>() {

        @Override
        public void onSuccess(HeadImgsBean bean) {
            dismissDialog();
            Glide.with(getContext()).load(bean.getHeadImgUrl())
//                    .apply(new RequestOptions().error(R.drawable.blank).placeholder(R.drawable.blank))
                    .into(icon);
        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {
            dismissDialog();
            Logger.e("error:" + message);
            ToastUtil.showToast(getContext(),message);

        }

        @Override
        public void networkError(Throwable e) {
            dismissDialog();
            Logger.e(e.getMessage());
            ToastUtil.showToast(getContext(),"网络错误");

        }
    };

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_personal);
    }

    @Override
    public void initPresenter() {
        titleLeftTextView.setVisibility(View.VISIBLE);
        titleCeneterTextView.setText("个人中心");
        unsubscribe();
        showProgressDialog();
        getInfo();
    }

    private void getInfo() {
        String str_random = RandomNum.getrandom();
        Map<String, String> map = new HashMap<>();
        map.put("method", "getPersonal");
        map.put("userId", preferences.getString("user_id", ""));
        map.put("versionName", getResources().getString(R.string.versionName));
        map.put("random", str_random);
        String str_signature = SignatureUtil.getSignature(map);
        map.put("signature", str_signature);

        mSubscription = Network.getPersonalApi().getPersonal(Network.WORK_URL, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    //=======================动态权限的申请===========================================================<
    @NeedsPermission({
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void openSelect() {
        Intent intent_camare = new SelectMediaActivity.IntentBuilder(this)
                .mediaType(MediaType.IMAGE)
                .canMultiSelect(false)
                .hasCamera(true)
                .build();
        startActivityForResult(intent_camare, SELECT_MEDIA_RQ);
    }

    /**
     * 为什么要获取这个权限给用户的说明
     *
     * @param request
     */
    @OnShowRationale({
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("有部分权限需要你的授权")
//                .setPositiveButton(R.string.imtrue, (dialog, button) -> request.proceed())
//                .setNegativeButton(R.string.cancel, (dialog, button) -> request.cancel())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show()
        ;
    }

    /**
     * 如果用户不授予权限调用的方法
     */
    @OnPermissionDenied({
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForCamera() {
        showPermissionDenied();
    }

    public void showPermissionDenied() {
        new AlertDialog.Builder(this)
                .setTitle("权限说明")
                .setCancelable(false)
                .setMessage("需要部分必要的权限，如果不授予可能会影响正常使用！")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("赋予权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PersonalInfoActivityPermissionsDispatcher.openSelectWithCheck(PersonalInfoActivity.this);
                    }
                })
                .create().show();
    }

    /**
     * 如果用户选择了让设备“不再询问”，而调用的方法
     */
    @OnNeverAskAgain({
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForCamera() {
        Toast.makeText(this, "不再询问授权权限！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PersonalInfoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnClick({R.id.titleLeft_textView, R.id.icon_layout, R.id.qrcode_layout, R.id.update, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleLeft_textView:
                finish();
                break;
            case R.id.icon_layout:
                PersonalInfoActivityPermissionsDispatcher.openSelectWithCheck(PersonalInfoActivity.this);
                break;
            case R.id.qrcode_layout:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", personalBean.getQcode());
                startActivity(intent);
                break;
            case R.id.update:
                Uri uri = Uri.parse(personalBean.getDown_url());
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
                break;
            case R.id.logout:
                Unicorn.setUserInfo(null);
                Intent logoutIntent = new Intent(getContext(), LoginActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                logoutIntent.putExtra("type", "logout");
                startActivity(logoutIntent);
                EventBus.getDefault().post(new LogoutBean());
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle args = data.getExtras();
            if (args != null) {
                String str_random = RandomNum.getrandom();
                Map<String, String> map = new HashMap<>();
                map.put("method", "uploadHeadImg");
                map.put("userId", preferences.getString("user_id", ""));
                map.put("random", str_random);
                String str_signature = SignatureUtil.getSignature(map);
                map.put("signature", str_signature);

                MediaBean mediaBean = args.getParcelable(SelectMediaActivity.RESULT_MEDIA);
                File file = new File(mediaBean.getPath());
                RequestBody requestBody = RequestBody.create(
                        okhttp3.MediaType.parse("application/octet-stream"),
                        file);

                MultipartBody.Part part = MultipartBody.Part.createFormData("uploadHeadImg",
                        file.getName(), requestBody);
                showProgressDialog();
                Network.GetUploadHeadImgApi()
                        .uploadHeadImg(WORK_URL, map, part)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer_img);
            }
        }
    }


    @OnClick(R.id.titleLeft_textView)
    public void onViewClicked() {
        finish();
    }
}
