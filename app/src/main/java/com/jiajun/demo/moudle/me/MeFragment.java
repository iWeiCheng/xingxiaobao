package com.jiajun.demo.moudle.me;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiajun.demo.R;
import com.jiajun.demo.base.BaseFragment;
import com.jiajun.demo.model.BaseBean;
import com.jiajun.demo.moudle.main.entities.RefreshMainEvent;
import com.jiajun.demo.moudle.me.adapter.BottomAdapter;
import com.jiajun.demo.moudle.me.entities.UserInfoBean;
import com.jiajun.demo.moudle.webview.NewWebViewActivity;
import com.jiajun.demo.moudle.webview.WebViewActivity;
import com.jiajun.demo.network.BaseObserver;
import com.jiajun.demo.network.Network;
import com.jiajun.demo.util.DeviceUtil;
import com.jiajun.demo.util.RandomNum;
import com.jiajun.demo.util.SignatureUtil;
import com.jiajun.demo.util.ToastUtil;
import com.jiajun.demo.views.CircleImageView;
import com.jiajun.demo.views.TffTextView;
import com.orhanobut.logger.Logger;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MeFragment extends BaseFragment {

    @BindView(R.id.tv_message)
    ImageView tvMessage;
    @BindView(R.id.tv_message_count)
    TextView tvMessageCount;
    @BindView(R.id.iv_img)
    CircleImageView ivImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_acc_income)
    TffTextView tvAccIncome;
    @BindView(R.id.tv_balance)
    TffTextView tvBalance;
    @BindView(R.id.tv_self_acc)
    TextView tvSelfAcc;
    @BindView(R.id.tv_car_acc)
    TextView tvCarAcc;
    @BindView(R.id.tv_other_acc)
    TextView tvOtherAcc;
    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.iv_fans)
    ImageView ivFans;
    @BindView(R.id.tv_fans_title)
    TextView tvFansTitle;
    @BindView(R.id.tv_fans_desc)
    TextView tvFansDesc;
    @BindView(R.id.iv_invite)
    ImageView ivInvite;
    @BindView(R.id.tv_invite_title)
    TextView tvInviteTitle;
    @BindView(R.id.tv_invite_desc)
    TextView tvInviteDesc;
    @BindView(R.id.tv_car_order)
    TextView tvCarOrder;
    @BindView(R.id.tv_self_order)
    TextView tvSelfOrder;
    @BindView(R.id.tv_other_order)
    TextView tvOtherOrder;
    @BindView(R.id.iv_car_order)
    ImageView ivCarOrder;
    @BindView(R.id.iv_self_order)
    ImageView ivSelfOrder;
    @BindView(R.id.iv_other_order)
    ImageView ivOtherOrder;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_car_acc)
    ImageView ivCarAcc;
    @BindView(R.id.iv_self_acc)
    ImageView ivSelfAcc;
    @BindView(R.id.iv_other_acc)
    ImageView ivOtherAcc;
    @BindView(R.id.tv_acc_income_name)
    TextView tvAccIncomeName;
    @BindView(R.id.tv_purse_balance_name)
    TextView tvPurseBalanceName;
    @BindView(R.id.iv_purse_balance)
    ImageView ivPurseBalance;
    @BindView(R.id.iv_acc_income)
    ImageView ivAccIncome;
    @BindView(R.id.ll_income)
    LinearLayout llIncome;
    @BindView(R.id.ll_acc)
    LinearLayout llAcc;
    @BindView(R.id.tv_barter)
    TextView tvBarter;
    @BindView(R.id.tv_barter_desc)
    TextView tvBarterDesc;
    @BindView(R.id.iv_barter)
    ImageView ivBarter;
    @BindView(R.id.tv_apply)
    TextView tvApply;
    @BindView(R.id.iv_apply)
    ImageView ivApply;

    private UserInfoBean userInfoBean;
    private BottomAdapter bottomAdater;
    private Subscription mSubscription;

    private BaseObserver<UserInfoBean> observer = new BaseObserver<UserInfoBean>() {

        @Override
        public void onSuccess(UserInfoBean bean) {
            isFirst = false;
            dismissDialog();
            userInfoBean = bean;
            setUserData(bean);
        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {
            isFirst = false;
            Logger.e("error:" + message);
            dismissDialog();

        }

        @Override
        public void networkError(Throwable e) {
            isFirst = false;
            Logger.e(e.getMessage());
            dismissDialog();
            ToastUtil.showToast(getContext(), "网络错误");
        }
    };


    private void setUserData(UserInfoBean bean) {
        Glide.with(this).load(Network.SERVICE + bean.getHeadImg())
//                .apply(new RequestOptions().error(R.drawable.blank).placeholder(R.drawable.blank))
                .into(ivImg);
        Glide.with(this).load(Network.SERVICE + bean.getMyFans().getImgUrl())
                .into(ivFans);
        Glide.with(this).load(Network.SERVICE + bean.getInvitFriend().getImgUrl())
                .into(ivInvite);
        Glide.with(this).load(Network.SERVICE + bean.getCarInfo().getImgUrl())
//                .apply(new RequestOptions().error(R.drawable.blank).placeholder(R.drawable.blank))
                .into(ivCarOrder);
        Glide.with(this).load(Network.SERVICE + bean.getAriskInfo().getImgUrl())
                .into(ivSelfOrder);
        Glide.with(this).load(Network.SERVICE + bean.getOtherInfo().getImgUrl()).into(ivOtherOrder);


        Glide.with(this).load(Network.SERVICE + bean.getAccIncome().getImgUrl()).into(ivAccIncome);
        Glide.with(this).load(Network.SERVICE + bean.getBalance().getImgUrl()).into(ivPurseBalance);
        Glide.with(this).load(Network.SERVICE + bean.getCarRevenue().getImgUrl()).into(ivCarAcc);
        Glide.with(this).load(Network.SERVICE + bean.getARiskRevenue().getImgUrl()).into(ivSelfAcc);
        Glide.with(this).load(Network.SERVICE + bean.getOtherRevenue().getImgUrl()).into(ivOtherAcc);
        Glide.with(this).load(Network.SERVICE + bean.getBarter().getImgUrl()).into(ivBarter);
        Glide.with(this).load(Network.SERVICE + bean.getCompensation().getImgUrl()).into(ivApply);

        tvApply.setText(bean.getCompensation().getName());

        tvBarter.setText(bean.getBarter().getName());
        tvBarterDesc.setText(bean.getBarter().getDesc());


        tvAccIncomeName.setText(bean.getAccIncome().getName());
        tvPurseBalanceName.setText(bean.getBalance().getName());

        tvCarOrder.setText(bean.getCarInfo().getName());
        tvSelfOrder.setText(bean.getAriskInfo().getName());
        tvOtherOrder.setText(bean.getOtherInfo().getName());

        tvName.setText(bean.getName());
        tvDesc.setText(bean.getLevel());
        tvAccIncome.setText(bean.getAccIncome().getAccIncomeNum());
        tvBalance.setText(bean.getBalance().getBalanceNum());

        tvSelfAcc.setText(bean.getARiskRevenue().getName());
        tvCarAcc.setText(bean.getCarRevenue().getName());
        tvOtherAcc.setText(bean.getOtherRevenue().getName());

        tvFansTitle.setText(bean.getMyFans().getName());
        tvFansDesc.setText(bean.getMyFans().getDesc());
        tvInviteTitle.setText(bean.getInvitFriend().getName());
        tvInviteDesc.setText(bean.getInvitFriend().getDesc());

        bottomAdater = new BottomAdapter(bean.getSetManagInfo());
        recyclerView.setAdapter(bottomAdater);
        bottomAdater.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(bottomAdater.getData().get(position).getName().equals("违章订单")){
                    Intent intent = new Intent(getContext(), NewWebViewActivity.class);
                    intent.putExtra("url", bottomAdater.getData().get(position).getUrl());
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url", bottomAdater.getData().get(position).getUrl());
                startActivity(intent);
            }
        });

        if(bean.getCommission_open().equals("0")){
            llIncome.setVisibility(View.GONE);
            llAcc.setVisibility(View.GONE);
        }else{
            llIncome.setVisibility(View.VISIBLE);
            llAcc.setVisibility(View.VISIBLE);
        }
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_me;
    }

    private void getUserInfo(boolean showProgressDialog) {
        if (showProgressDialog) {
            showProgressDialog();
        }
        Map<String, String> map = new HashMap<>();
        map.put("method", "getUserinfo");
        map.put("userId", preferences.getString("user_id", ""));
        map.put("versionName", getResources().getString(R.string.versionName));
        String str_random = RandomNum.getrandom();
        map.put("random", str_random);
        String str_signature = SignatureUtil.getSignature(map);
        map.put("signature", str_signature);


        mSubscription = Network.getUserInfoApi().getUserinfo(Network.WORK_URL, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPage(RefreshMainEvent event) {
        if (event.getIndex() == 3) {
            if (!isFirst) {
                getUserInfo(false);
            }
        }
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBar.getLayoutParams().height = DeviceUtil.getStatusBarHeight(getContext());
        }
        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        unsubscribe();
        getUserInfo(true);
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }


    @OnClick({R.id.iv_img, R.id.tv_name, R.id.tv_desc, R.id.tv_message, R.id.ll_self_order, R.id.ll_car_order,
            R.id.ll_other_order, R.id.tv_apply, R.id.ll_set_store, R.id.ll_article, R.id.ll_my_fans, R.id.ll_invite,
            R.id.tv_problem, R.id.tv_service, R.id.tv_setting, R.id.ll_car_acc, R.id.ll_self_acc, R.id.ll_other_acc,
            R.id.ll_acc_income ,R.id.ll_balance,R.id.ll_barter})
    public void onViewClicked(View view) {
        if (userInfoBean == null) {
            return;
        }
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        String url = "";
        switch (view.getId()) {
            case R.id.iv_img:
            case R.id.tv_name:
            case R.id.tv_desc:
                Intent personalIntent = new Intent(getContext(), PersonalInfoActivity.class);
                startActivity(personalIntent);
                break;
            case R.id.tv_message:
                break;
            case R.id.ll_self_order:
                url = userInfoBean.getAriskInfo().getUrl();
                break;
            case R.id.ll_car_order:
                url = userInfoBean.getCarInfo().getUrl();
                break;
            case R.id.ll_other_order:
                url = userInfoBean.getOtherInfo().getUrl();
                break;
            case R.id.tv_apply:
                url = userInfoBean.getCompensation().getUrl();
                break;
            case R.id.ll_set_store:
//                url = userInfoBean.get();
                break;
            case R.id.ll_article:
//                url = userInfoBean.getPublishUrl();
                break;
            case R.id.ll_my_fans:
                url = userInfoBean.getMyFans().getUrl();
                break;
            case R.id.ll_invite:
                url = userInfoBean.getInvitFriend().getUrl();
                break;
            case R.id.tv_problem:
//                url = userInfoBean.getProblemInfo();
                break;
            case R.id.tv_service:
                String title = getResources().getString(R.string.app_name);
                ConsultSource source = new ConsultSource("http://www.implus100.com", "", "custom");
                Unicorn.openServiceActivity(getContext(), // 上下文
                        "返回", // 聊天窗口的标题
                        source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                );
                break;
            case R.id.tv_setting:
//                url = userInfoBean.getSetManagInfo();
                break;
            case R.id.ll_car_acc:
                url = userInfoBean.getCarRevenue().getUrl();
                break;
            case R.id.ll_self_acc:
                url = userInfoBean.getARiskRevenue().getUrl();
                break;
            case R.id.ll_other_acc:
                url = userInfoBean.getOtherRevenue().getUrl();
                break;
            case R.id.ll_acc_income:
                url = userInfoBean.getAccIncome().getUrl();
                break;
            case R.id.ll_balance:
                url = userInfoBean.getBalance().getUrl();
                break;
            case R.id.ll_barter:
                url = userInfoBean.getBarter().getUrl();
                break;

        }
        if (url == null || url.length() == 0) {
            return;
        }
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private boolean isFirst = true;

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
            getUserInfo(false);
        }
    }
}
