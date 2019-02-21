package com.jiajun.demo.moudle.homepage;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.caijia.widget.looperrecyclerview.LooperPageRecyclerView;
import com.caijia.widget.looperrecyclerview.RecyclerViewCircleIndicator;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jiajun.customercamera.EasyCamera;
import com.jiajun.demo.R;
import com.jiajun.demo.base.BaseFragment;
import com.jiajun.demo.model.BaseBean;
import com.jiajun.demo.moudle.account.LoginActivity;
import com.jiajun.demo.moudle.homepage.adapter.HotAdapter;
import com.jiajun.demo.moudle.homepage.adapter.ImgAdapter;
import com.jiajun.demo.moudle.homepage.adapter.PolicyMenuAdapter;
import com.jiajun.demo.moudle.homepage.entities.HomePageBean;
import com.jiajun.demo.moudle.licence_recognize.LicenceCameraActivity;
import com.jiajun.demo.moudle.licence_recognize.LicenceEvent;
import com.jiajun.demo.moudle.licence_recognize.RecognizeResponseActivity;
import com.jiajun.demo.moudle.main.entities.RefreshMainEvent;
import com.jiajun.demo.moudle.start.SplashActivity;
import com.jiajun.demo.moudle.webview.NewWebViewActivity;
import com.jiajun.demo.moudle.webview.WebViewActivity;
import com.jiajun.demo.network.BaseObserver;
import com.jiajun.demo.network.Network;
import com.jiajun.demo.util.CoordinateUtil;
import com.jiajun.demo.util.LatLng;
import com.jiajun.demo.util.RandomNum;
import com.jiajun.demo.util.RecyclerViewDivider;
import com.jiajun.demo.util.SignatureUtil;
import com.jiajun.demo.util.ToastUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomepageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RuntimePermissions
public class HomepageFragment extends BaseFragment {


    @BindView(R.id.view_pager_banner)
    LooperPageRecyclerView looperPageRecyclerView;
    @BindView(R.id.indicator)
    RecyclerViewCircleIndicator indicator;
    @BindView(R.id.tv_car_insurance)
    TextView tvCarInsurance;
    @BindView(R.id.recycler_view_policy_menu)
    RecyclerView recyclerViewMenu;
    @BindView(R.id.recycler_hot)
    RecyclerView recyclerHot;
    @BindView(R.id.ll_more_hot)
    LinearLayout llMoreHot;
    @BindView(R.id.iv_product1)
    ImageView ivProduct1;
    @BindView(R.id.tv_product1)
    TextView tvProduct1;
    @BindView(R.id.tv_product1_desc)
    TextView tvProduct1Desc;
    @BindView(R.id.rl_product_1)
    RelativeLayout rlProduct1;
    @BindView(R.id.iv_product2)
    ImageView ivProduct2;
    @BindView(R.id.tv_product2)
    TextView tvProduct2;
    @BindView(R.id.tv_product2_desc)
    TextView tvProduct2Desc;
    @BindView(R.id.rl_product_2)
    RelativeLayout rlProduct2;
    @BindView(R.id.iv_product3)
    ImageView ivProduct3;
    @BindView(R.id.tv_product3)
    TextView tvProduct3;
    @BindView(R.id.tv_product3_desc)
    TextView tvProduct3Desc;
    @BindView(R.id.rl_product_3)
    RelativeLayout rlProduct3;
    @BindView(R.id.iv_product4)
    ImageView ivProduct4;
    @BindView(R.id.tv_product4)
    TextView tvProduct4;
    @BindView(R.id.tv_product4_desc)
    TextView tvProduct4Desc;
    @BindView(R.id.rl_product_4)
    RelativeLayout rlProduct4;
    @BindView(R.id.iv_product5)
    ImageView ivProduct5;
    @BindView(R.id.tv_product5)
    TextView tvProduct5;
    @BindView(R.id.tv_product5_desc)
    TextView tvProduct5Desc;
    @BindView(R.id.rl_product_5)
    RelativeLayout rlProduct5;
    @BindView(R.id.recycler_guess)
    RecyclerView recyclerGuess;
    @BindView(R.id.ll_car_number)
    LinearLayout ll_car_number;
    @BindView(R.id.tv_record_name)
    TextView tvRecordName;
    @BindView(R.id.tv_icpid)
    TextView tvICPID;
    @BindView(R.id.tv_business)
    TextView tvBusiness;
    @BindView(R.id.tv_tech)
    TextView tvTech;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.edt_car_number)
    EditText edtCarNumber;
    @BindView(R.id.number)
    View number;
    @BindView(R.id.tv_news1)
    TextView tvNews1;
    @BindView(R.id.tv_news2)
    TextView tvNews2;
    @BindView(R.id.tv_time1)
    TextView tvTime1;
    @BindView(R.id.tv_time2)
    TextView tvTime2;
    @BindView(R.id.iv_news)
    ImageView ivNews;
    @BindView(R.id.new_news)
    View newNews;

    private static final int REQUEST_LICENCE = 1010;
    private float ratio = 0.76f; //取景框高宽比
    private boolean province_state = false;


    private ImgAdapter bannerImageAdapter;
    private List<HomePageBean.ImgListBean> bannerImgs;
    private HomePageBean homePageBean;

    private Subscription mSubscription;
    private String userId;


    private PolicyMenuAdapter policyMenuAdapter;
    private HotAdapter hotAdapter;


    private SelectCarNumberFragment selectCarNumberFragment;
    private CustomerNumberFragment customerNumberFragment;
    private CustomerProvinceFragment customerProvinceFragment;

    public static HomepageFragment newInstance() {
        HomepageFragment fragment = new HomepageFragment();
        return fragment;
    }

    private BaseObserver<Object> observer = new BaseObserver<Object>() {

        @Override
        public void onSuccess(Object o) {
            //上传完成更新位置
        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {

        }

        @Override
        public void networkError(Throwable e) {

        }
    };
    private BaseObserver<HomePageBean> observer_homepage = new BaseObserver<HomePageBean>() {

        @Override
        public void onSuccess(HomePageBean bean) {
            isFirst = false;
            dismissDialog();
            homePageBean = bean;
            policyMenuAdapter = new PolicyMenuAdapter(bean.getPolicyMenu());
            recyclerViewMenu.setAdapter(policyMenuAdapter);
            policyMenuAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = null;
                    if (policyMenuAdapter.getItem(position).getUrl() == null) {
                        return;
                    }
                    if (policyMenuAdapter.getItem(position).getUrl().contains("tb.sjdcar.com")
                            || policyMenuAdapter.getItem(position).getUrl().contains("tc.sjdcar.com")) {
                        intent = new Intent(getContext(), NewWebViewActivity.class);
                    } else {
                        intent = new Intent(getContext(), WebViewActivity.class);
                    }
                    intent.putExtra("url", policyMenuAdapter.getItem(position).getUrl());
                    startActivity(intent);
                }
            });

            hotAdapter = new HotAdapter(bean.getHostMenu());
            recyclerHot.setAdapter(hotAdapter);
            setNewData(bean.getNewMenu());

            if (bean.getImgList().size() > 0) {
                bannerImgs.clear();
                bannerImgs = bean.getImgList();
            }
            bannerImageAdapter.setData(bannerImgs);
            bannerImageAdapter.notifyDataSetChanged();

            tvRecordName.setText(bean.getRecordName());
            tvICPID.setText(bean.getICPId());
            if (bean.getBusiness_company().length() == 0) {
                tvBusiness.setVisibility(View.GONE);
            } else {
                tvBusiness.setText(bean.getBusiness_company());
                tvBusiness.setVisibility(View.VISIBLE);
            }
            tvTech.setText(bean.getTechnical());
            if (!province_state) {
                tvProvince.setText(bean.getProvince());
            }
            /** 消息管理 **/
            if (bean.getNoticeNews().getState().equals("1")) {
                newNews.setVisibility(View.VISIBLE);
            } else {
                newNews.setVisibility(View.GONE);
            }
            if (bean.getNoticeNews().getNoticeList().size()==0) {
                newNews.setVisibility(View.GONE);
            } else {
                newNews.setVisibility(View.VISIBLE);
            }
            Glide.with(getContext()).load(Network.SERVICE + bean.getNoticeNews().getImgUrl()).into(ivNews);
            for (int i = 0; i < bean.getNoticeNews().getNoticeList().size(); i++) {
                if (i == 0) {
                    tvNews1.setText(bean.getNoticeNews().getNoticeList().get(i).getTitle());
                    tvTime1.setText(bean.getNoticeNews().getNoticeList().get(i).getTime());
                } else if (i == 1) {
                    tvNews2.setText(bean.getNoticeNews().getNoticeList().get(i).getTitle());
                    tvTime2.setText(bean.getNoticeNews().getNoticeList().get(i).getTime());
                }
            }
            if (bean.getNoticeNews().getNoticeList().size() == 0) {
                tvNews1.setVisibility(View.GONE);
                tvTime1.setVisibility(View.GONE);
                tvNews2.setVisibility(View.GONE);
                tvTime2.setVisibility(View.GONE);
            } else if (bean.getNoticeNews().getNoticeList().size() == 1) {
                tvNews1.setVisibility(View.VISIBLE);
                tvTime1.setVisibility(View.VISIBLE);
                tvNews2.setVisibility(View.GONE);
                tvTime2.setVisibility(View.GONE);
            }

            preferences.edit().putString("share_title", bean.getShare_title()).apply();
            preferences.edit().putString("share_url", bean.getShare_url()).apply();
            preferences.edit().putString("share_image_url", bean.getShare_image_url()).apply();
            preferences.edit().putString("share_content", bean.getShare_content()).apply();

        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {
            dismissDialog();
            Logger.e("error:" + message);
        }

        @Override
        public void networkError(Throwable e) {
            dismissDialog();
            Logger.e(e.getMessage());
            ToastUtil.showToast(getContext(), "网络错误");
        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPage(RefreshMainEvent event) {
        if (event.getIndex() == 0) {
            if (!isFirst) {
                getHomePageInfo(false);
            }
        }
    }


    //=======================动态权限的申请===========================================================<
    @NeedsPermission({
            Manifest.permission.CALL_PHONE})
    public void call() {
        callPhone("4009008821");
    }

    /**
     * 为什么要获取这个权限给用户的说明
     *
     * @param request
     */
    @OnShowRationale({
            Manifest.permission.CALL_PHONE})
    void showRationaleForCall(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setMessage("应用请求您的拨打电话权限")
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
            Manifest.permission.CALL_PHONE})
    void showDeniedForCall() {
        showPermissionDenied();
    }

    public void showPermissionDenied() {
        new AlertDialog.Builder(getContext())
                .setTitle("权限说明")
                .setCancelable(false)
                .setMessage("应用请求您的打电话权限，拒绝将不能自动拨打电话")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("赋予权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomepageFragmentPermissionsDispatcher.callWithCheck(HomepageFragment.this);
                    }
                })
                .create().show();
    }

    /**
     * 如果用户选择了让设备“不再询问”，而调用的方法
     */
    @OnNeverAskAgain({
            Manifest.permission.CALL_PHONE})
    void showNeverAskForCall() {
        Toast.makeText(getContext(), "不再询问授权权限！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HomepageFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void setNewData(List<HomePageBean.NewMenuBean> newMenu) {
        for (int i = 0; i < newMenu.size(); i++) {
            final HomePageBean.NewMenuBean bean = newMenu.get(i);
            switch (i) {
                case 0:
                    tvProduct1.setText(bean.getTitle());
                    tvProduct1Desc.setText(bean.getDesc());
                    Glide.with(getContext())
                            .load(Network.SERVICE + bean.getImgUrl())
//                            .apply(new RequestOptions().placeholder(R.drawable.lei01).error(R.drawable.lei01))
                            .into(ivProduct1);
                    rlProduct1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), WebViewActivity.class);
                            intent.putExtra("url", bean.getUrl());
                            startActivity(intent);
                        }
                    });
                    break;
                case 1:
                    tvProduct2.setText(bean.getTitle());
                    tvProduct2Desc.setText(bean.getDesc());
                    Glide.with(getContext()).load(Network.SERVICE + bean.getImgUrl())
//                            .apply(new RequestOptions().placeholder(R.drawable.lei02).error(R.drawable.lei02))
                            .into(ivProduct2);
                    rlProduct2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), WebViewActivity.class);
                            intent.putExtra("url", bean.getUrl());
                            startActivity(intent);
                        }
                    });
                    break;
                case 2:
                    tvProduct3.setText(bean.getTitle());
                    tvProduct3Desc.setText(bean.getDesc());
                    Glide.with(getContext()).load(Network.SERVICE + bean.getImgUrl())
//                            .apply(new RequestOptions().placeholder(R.drawable.lei03).error(R.drawable.lei03))

                            .into(ivProduct3);
                    rlProduct3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), WebViewActivity.class);
                            intent.putExtra("url", bean.getUrl());
                            startActivity(intent);
                        }
                    });
                    break;
                case 3:
                    tvProduct4.setText(bean.getTitle());
                    tvProduct4Desc.setText(bean.getDesc());
                    Glide.with(getContext()).load(Network.SERVICE + bean.getImgUrl())
//                            .apply(new RequestOptions().placeholder(R.drawable.lei04).error(R.drawable.lei04))
                            .into(ivProduct4);
                    rlProduct4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), WebViewActivity.class);
                            intent.putExtra("url", bean.getUrl());
                            startActivity(intent);
                        }
                    });
                    break;
                case 4:
                    tvProduct5.setText(bean.getTitle());
                    tvProduct5Desc.setText(bean.getDesc());
                    Glide.with(getContext()).load(Network.SERVICE + bean.getImgUrl())
//                            .apply(new RequestOptions().placeholder(R.drawable.lei05).error(R.drawable.lei05))
                            .into(ivProduct5);
                    rlProduct5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), WebViewActivity.class);
                            intent.putExtra("url", bean.getUrl());
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_homepage;
    }

    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
//            Logger.e("AMap:"+aMapLocation.getLatitude()+"--"+aMapLocation.getLongitude());
            preferences.edit().putString("latitude", aMapLocation.getLatitude() + "").apply();
            preferences.edit().putString("longitude", aMapLocation.getLongitude() + "").apply();
            preferences.edit().putString("location", new Gson().toJson(aMapLocation)).apply();
        }
    };


    private void initAmap() {
        mLocationClient = new AMapLocationClient(getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //声明AMapLocationClientOption对象
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
//        mLocationOption.setOnceLocationLatest(false);
//        mLocationOption.setOnceLocation(false);
        mLocationOption.setInterval(5000);


        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
//        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }

    @Override
    public void initView() {
        initAmap();
        //初始化定位

        EventBus.getDefault().register(this);
        initCarNumber();
        userId = preferences.getString("user_id", "");
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        recyclerViewMenu.setLayoutManager(manager);
        LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
        recyclerHot.setLayoutManager(manager1);
        recyclerHot.addItemDecoration(new RecyclerViewDivider(
                getContext(), LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(getContext(), R.color.gray_line)));

        LinearLayoutManager manager2 = new LinearLayoutManager(getContext());
        recyclerGuess.setLayoutManager(manager2);

        recyclerGuess.addItemDecoration(new RecyclerViewDivider(
                getContext(), LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(getContext(), R.color.gray_line)));

        looperPageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        bannerImgs = new ArrayList<>();
//        bannerImgs.add(new HomePageBean.ImgListBean("", R.drawable.banner01 + "", ""));
        bannerImageAdapter = new ImgAdapter(getContext());
        bannerImageAdapter.setData(bannerImgs);
        looperPageRecyclerView.setAdapter(bannerImageAdapter);
        indicator.setupWithRecyclerView(looperPageRecyclerView);

        unsubscribe();
//        getGuideImages();
        getHomePageInfo(true);
        HomepageFragmentPermissionsDispatcher.locateWithCheck(HomepageFragment.this);

    }

    private void initCarNumber() {
        for (int i = 0; i < 7; i++) {
            final int index = i;
            final TextView textView = (TextView) ll_car_number.getChildAt(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    for (int j = 0; j < 7; j++) {
                        ll_car_number.getChildAt(j).setSelected(false);
                    }
                    textView.setSelected(true);
                    selectCarNumberFragment = SelectCarNumberFragment.getInstance(index);
                    selectCarNumberFragment.setClickItemListener(new SelectCarNumberFragment.OnClickSubmitListener() {
                        @Override
                        public void onClickItem(int index, String text) {
                            TextView view = (TextView) ll_car_number.getChildAt(index);
                            view.setText(text);
                            nextSelect(index);
                        }

                        @Override
                        public void onClickCancel() {
                            selectCarNumberFragment.dismiss();
                        }

                        @Override
                        public void onClickDelete() {

                        }

                    });
                    selectCarNumberFragment.show(getFragmentManager(), "select");
                }
            });
        }
    }

    private void nextSelect(int index) {
        for (int i = 0; i < 7; i++) {
            ll_car_number.getChildAt(i).setSelected(false);
        }
        if (index < 6) {
            ll_car_number.getChildAt(index + 1).setSelected(true);
        }
    }

    private void preSelect(int index) {
        for (int i = 0; i < 7; i++) {
            ll_car_number.getChildAt(i).setSelected(false);
        }
        if (index >= 1) {
            ll_car_number.getChildAt(index - 1).setSelected(true);
        }
    }

    private void getHomePageInfo(boolean showProgressDialog) {
        if (showProgressDialog) {
            showProgressDialog();
        }
        Map<String, String> map = new HashMap<>();
        map.put("method", "getHomePage");
        map.put("userId", preferences.getString("user_id", ""));
        map.put("versionName", getResources().getString(R.string.versionName));
        String str_random = RandomNum.getrandom();
        map.put("random", str_random);
        String str_signature = SignatureUtil.getSignature(map);
        map.put("signature", str_signature);

        mSubscription = Network.getHomePage().getHomePage(Network.WORK_URL, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer_homepage);
    }

    /**
     * 上传位置信息
     */
    private void uploadPosition(double longitude, double latitude, String desc) {
        Map<String, String> map = new HashMap<>();
        map.put("method", "positionInfo");
        map.put("versionName", getResources().getString(R.string.versionName));
        String str_random = RandomNum.getrandom();
        map.put("random", str_random);
        map.put("userId", preferences.getString("user_id", ""));
        map.put("longitude", longitude + "");
        map.put("latitude", latitude + "");
        map.put("desc", desc);
        String str_signature = SignatureUtil.getSignature(map);
        map.put("signature", str_signature);

        mSubscription = Network.uploadPositionApi().uploadPosition(Network.WORK_URL, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }


    private void callPhone(String phone) {
        Intent call_intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ToastUtil.showToast(getContext(), "您禁止了拨打电话权限，请在设置-权限管理中允许应用拨打电话");
            return;
        }
        startActivity(call_intent);
    }

    @OnClick({R.id.tv_car_insurance, R.id.ll_more_hot, R.id.tv_phone, R.id.tv_province, R.id.number, R.id.iv_scan, R.id.ll_news})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        String url = "";
        switch (view.getId()) {
            case R.id.tv_phone:
                HomepageFragmentPermissionsDispatcher.callWithCheck(HomepageFragment.this);
                return;
            case R.id.tv_province:
                if (homePageBean == null) {
                    return;
                }
//                selectCarNumberFragment = SelectCarNumberFragment.getInstance(0);
//                selectCarNumberFragment.setClickItemListener(new SelectCarNumberFragment.OnClickSubmitListener() {
//                    @Override
//                    public void onClickItem(int index, String text) {
//                        tvProvince.setText(text);
//                        selectCarNumberFragment.dismiss();
//                        number.performClick();
//                    }
//
//                    @Override
//                    public void onClickCancel() {
//                        selectCarNumberFragment.dismiss();
//                    }
//
//                    @Override
//                    public void onClickDelete() {
//
//                    }
//                });
//                selectCarNumberFragment.show(getFragmentManager(), "carNumber");
                customerProvinceFragment = CustomerProvinceFragment.getInstance();
                customerProvinceFragment.setClickItemListener(new CustomerProvinceFragment.OnClickSubmitListener() {
                    @Override
                    public void onClickItem(int index, String text) {
                        province_state = true;
                        tvProvince.setText(text);
                        customerProvinceFragment.dismiss();
                        number.performClick();
                    }

                    @Override
                    public void onClickCancel() {
                        customerProvinceFragment.dismiss();
                    }

                    @Override
                    public void onClickDelete() {

                    }
                });
                customerProvinceFragment.show(getFragmentManager(), "carNumber");
                break;
            case R.id.tv_car_insurance://车险报价

                if (homePageBean == null) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(tvProvince.getText().toString()).append(edtCarNumber.getText().toString());
                for (int i = 0; i < sb.length(); i++) {
                    if (sb.charAt(i) >= 'a' && sb.charAt(i) <= 'z') {
                        sb.replace(i, i + 1, (sb.charAt(i) + "").toUpperCase());
                    }
                }
                if (sb.length() > 0) {
                    url = homePageBean.getInsure_car_url() + "&car_number=" + sb.toString();
                } else {
                    url = homePageBean.getInsure_car_url();
                }
                break;
            case R.id.ll_more_hot://更多热销
                url = homePageBean.getHost_all_url();
                break;
            case R.id.ll_news://消息管理
                url = homePageBean.getNoticeNews().getUrl();
                break;
            case R.id.number:
                if (homePageBean == null) {
                    return;
                }
                customerNumberFragment = CustomerNumberFragment.getInstance();
                customerNumberFragment.setClickItemListener(new CustomerNumberFragment.OnClickSubmitListener() {
                    @Override
                    public void onClickItem(int index, String text) {
                        if (edtCarNumber.getText().toString().length() >= 7) {
                            customerNumberFragment.dismiss();
                            return;
                        }
                        edtCarNumber.setText(String.format("%s%s", edtCarNumber.getText().toString(), text));
                        if (edtCarNumber.getText().toString().length() >= 7) {
                            customerNumberFragment.dismiss();
                            return;
                        }
                    }

                    @Override
                    public void onClickCancel() {
                        customerNumberFragment.dismiss();
                    }

                    @Override
                    public void onClickDelete() {
                        String text = edtCarNumber.getText().toString();
                        if (text.length() > 0) {
                            edtCarNumber.setText(text.substring(0, text.length() - 1));
                        }
                    }
                });
                customerNumberFragment.show(getFragmentManager(), "carNumber2");
                return;
            case R.id.iv_scan:
                String SAMPLE_CROPPED_IMAGE_NAME = "cropImage_" + System.currentTimeMillis() + ".png";
                Uri destination = Uri.fromFile(new File(getContext().getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME));
                EasyCamera.create(destination)
                        .withViewRatio(ratio)
                        .withMarginCameraEdge(50, 50)
                        .start(getBaseActivity(), LicenceCameraActivity.class);
                return;
        }

        if (url == null || url.length() == 0) {
            return;
        }
        intent.putExtra("url", url);
        startActivity(intent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == EasyCamera.REQUEST_CAPTURE) {
                Uri resultUri = EasyCamera.getOutput(data);
                try {
                    File file = new File(new URI(resultUri.toString()));
                    Intent intent = new Intent(getContext(), RecognizeResponseActivity.class);
                    intent.putExtra("filePath", file.getAbsolutePath());
                    startActivityForResult(intent, REQUEST_LICENCE);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_LICENCE) {
                String number = data.getStringExtra("number");
                edtCarNumber.setText(number);
            }
        }
    }

    /**
     * 设置车牌
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setLicence(LicenceEvent event) {
        if (event.getNumber().length() > 0) {
            province_state = true;
            tvProvince.setText(event.getNumber().substring(0, 1));
            edtCarNumber.setText(event.getNumber().substring(1, event.getNumber().length()));
        }
    }

    private boolean isFirst = true;

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
            getHomePageInfo(false);
        }
    }


    /**
     * 定位权限
     */
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void locate() {
//        mLocationClient.start();
        //获取一个地址管理者，获取的方法比较特殊，不是直接new出来的
        LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

//使用GPS获取上一次的地址，这样获取到的信息需要多次，才能够显示出来，所以后面有动态的判断
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            mLocation = location;
            LatLng latLng = CoordinateUtil.fromGpsToAmap(location);
            preferences.edit().putString("latitude", latLng.getLatitude() + "").apply();
            preferences.edit().putString("longitude", latLng.getLongtitude() + "").apply();
        }
        //判断是否用户打开了GPS开关，这个和获取权限没关系
        GPSisopen(locationManager);
//显示信息，可以根据自己的传入对应的location！！！
        addresses = getAddress(location);
//        if (addresses != null && addresses.size() > 0) {
//            uploadPosition(location.getLongitude(), location.getAltitude(), addresses.get(0).toString());
//        }
//获取时时更新，第一个是Provider,第二个参数是更新时间1000ms，第三个参数是更新半径，第四个是监听器
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, Integer.parseInt(preferences.getString("distance", "1000")), new LocationListener() {

            @Override
            /*当地理位置发生改变的时候调用*/
            public void onLocationChanged(final Location location) {
                mLocation = location;
                LatLng latLng = CoordinateUtil.fromGpsToAmap(location);
                preferences.edit().putString("latitude", latLng.getLatitude() + "").apply();
                preferences.edit().putString("longitude", latLng.getLongtitude() + "").apply();
//                upLoadInfor(location);//实时的显示信息
                //采取直接用匿名类的方法，构造了一个线程，但是在子线程中不能直接修改主线程的内容，否则会报错，但是！！！，当我用Android8.0模拟器测试的时候没有崩，当用Android7.0测试的时候直接崩溃，所以还是老老实实通过handler来解决这个问题！
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Run", "A new Thread");
                        try {
                            addresses = getAddress(location);
                            if (addresses != null) {
                                Log.e("run: ", addresses.toString());
                                Message message = new Message();
                                message.what = 1;//信息内容
                                handler.sendMessage(message);//发送信息
                            }
                        } catch (Exception e) {
                            Log.e("Exception", "ERRPOR");
                        }
                    }

                }).start();
            }

            /* 当状态发生改变的时候调用*/
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("GPS_SERVICES", "状态信息发生改变");

            }

            /*当定位者启用的时候调用*/
            @Override
            public void onProviderEnabled(String s) {
                Log.d("TAG", "onProviderEnabled: ");

            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("TAG", "onProviderDisabled: ");
            }
        });
    }

    /**
     * 为什么要获取这个权限给用户的说明
     *
     * @param request
     */
    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showRationaleForLocation(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setMessage("定位权限需要你的授权")
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
                .show();
    }

    /**
     * 如果用户不授予权限调用的方法
     */
    @OnPermissionDenied({
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void showDeniedForLocation() {
        showLocationPermissionDenied();
    }

    public void showLocationPermissionDenied() {
        new AlertDialog.Builder(getContext())
                .setTitle("权限说明")
                .setCancelable(false)
                .setMessage("本应用需要获取您的位置，如果不授予可能会影响正常使用！")
                .setNegativeButton("不授予", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("授予权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomepageFragmentPermissionsDispatcher.locateWithCheck(HomepageFragment.this);
                    }
                })
                .create().show();
    }

    //判断是否用户打开GPS开关，并作指导性操作！
    private void GPSisopen(LocationManager locationManager) {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getContext(), "请打开GPS", Toast.LENGTH_SHORT);
            final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setTitle("请打开GPS连接");
            dialog.setMessage("为了获取定位服务，请先打开GPS");
            dialog.setPositiveButton("设置", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //界面跳转
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 0);
                }
            });
            dialog.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            //调用显示方法！
            dialog.show();
        }
    }

    //同时获取到的只是location如果想根据location获取具体地址，可以通过Android提供的API获取具体的地点！

    //传进来一个location返回一个Address列表，这个是耗时的操作所以需要在子线程中进行！！！
    //传进来一个location返回一个Address列表，这个是耗时的操作所以需要在子线程中进行！！！
    private List<Address> getAddress(Location location) {
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(getContext(), Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<Address> addresses;
    private Location mLocation;
    private Handler handler = new MyHandler(this);

    private static class MyHandler extends Handler {
        WeakReference<HomepageFragment> weakReference;

        public MyHandler(HomepageFragment homepageFragment) {
            weakReference = new WeakReference<>(homepageFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (weakReference.get().addresses != null && weakReference.get().addresses.size() > 0) {
                        weakReference.get().uploadPosition(weakReference.get().mLocation.getLongitude(), weakReference.get().mLocation.getAltitude(), weakReference.get().addresses.get(0).toString());
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
