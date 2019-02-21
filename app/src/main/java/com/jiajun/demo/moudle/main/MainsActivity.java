package com.jiajun.demo.moudle.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jiajun.demo.R;
import com.jiajun.demo.app.BaseApplication;
import com.jiajun.demo.base.BaseActivity;
import com.jiajun.demo.config.Const;
import com.jiajun.demo.model.entities.RefreshNewsFragmentEvent;
import com.jiajun.demo.moudle.customer.CustomerFragment;
import com.jiajun.demo.moudle.homepage.HomepageFragment;
import com.jiajun.demo.moudle.main.entities.RefreshMainEvent;
import com.jiajun.demo.moudle.me.MeFragment;
import com.jiajun.demo.moudle.me.entities.LogoutBean;
import com.jiajun.demo.moudle.news_category.CategoryActivity;
import com.jiajun.demo.moudle.vip.VIPFragment;
import com.jiajun.demo.util.inputmethodmanager_leak.IMMLeaks;
import com.jiajun.demo.widget.TabBar_Mains;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 * Created by dan on 2017/8/21.
 */

public class MainsActivity extends BaseActivity {

    public static final String MESSAGE_RECEIVED_ACTION = "com.ysx.cxbb.MESSAGE_RECEIVED_ACTION";

    @BindView(R.id.framelayout_mains)
    FrameLayout framelayoutMains;
    @BindView(R.id.recommend_mains)
    TabBar_Mains recommendMains;
    @BindView(R.id.cityfinder_mains)
    TabBar_Mains cityfinderMains;
    @BindView(R.id.findtravel_mains)
    TabBar_Mains findtravelMains;
    @BindView(R.id.me_mains)
    TabBar_Mains meMains;

    private static final String HOMEPAGE_FRAGMENT = "HOMEPAGE_FRAGMENT";
    private static final String VIP_FRAGMENT = "VIP_FRAGMENT";
    private static final String CUSTOMER_FRAGMENT = "CUSTOMER_FRAGMENT";
    public static final String ME_FRAGMENT = "ME_FRAGMENT";

    private FragmentManager sBaseFragmentManager;

    public MeFragment mMeFragment;
    public HomepageFragment mHomepageFragment;
    public VIPFragment mVIPFragment;
    public CustomerFragment mCustomerFragment;
    private String mCurrentIndex;

    public static List<String> logList = new CopyOnWriteArrayList<String>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void loadLayout() {
        BaseApplication.setMainActivity(this);
        setContentView(R.layout.activity_mains);
        Window window = getWindow();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使得布局延伸到状态栏和导航栏区域
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

//            //透明状态栏/导航栏
//            window.setStatusBarColor(Color.TRANSPARENT);
//            //这样的效果跟上述的主题设置效果类似
        }
    }

    @Override
    protected int getTranslucentColor() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return R.color.transparent;
        } else {
            return R.color.textGreen;
        }
    }

    @Override
    public void initialize(Bundle savedInstanceState) {
        sBaseFragmentManager = getBaseFragmentManager();

        String startPage = HOMEPAGE_FRAGMENT;
        if (savedInstanceState != null) {
            initByRestart(savedInstanceState);
        } else {
            switchToFragment(startPage);
            mCurrentIndex = startPage;
        }
        //订阅事件
        EventBus.getDefault().register(this);
    }

    boolean isRestart = false;

    private void initByRestart(Bundle savedInstanceState) {
        mCurrentIndex = savedInstanceState.getString("mCurrentIndex");
        isRestart = true;
        Logger.e("恢复状态：" + mCurrentIndex);
        mMeFragment = (MeFragment) sBaseFragmentManager.findFragmentByTag(ME_FRAGMENT);
        if (recommendMains.getVisibility() == View.VISIBLE) {
            mHomepageFragment = (HomepageFragment) sBaseFragmentManager.findFragmentByTag(HOMEPAGE_FRAGMENT);
        }
        mVIPFragment = (VIPFragment) sBaseFragmentManager.findFragmentByTag(VIP_FRAGMENT);
        mCustomerFragment = (CustomerFragment) sBaseFragmentManager.findFragmentByTag(CUSTOMER_FRAGMENT);
        switchToFragment(mCurrentIndex);
    }

    private void switchToFragment(String index) {
//        sFragmentTransaction = getFragmentTransaction();
        hideAllFragment();
        switch (index) {
            case HOMEPAGE_FRAGMENT:
                if (recommendMains.getVisibility() == View.VISIBLE) {
                    showHomeFragment();
                    Logger.e("newsopen:101");
                }
                break;
            case VIP_FRAGMENT:
                showWechatFragment();
                break;
            case CUSTOMER_FRAGMENT:
                showFindFragment();
                break;
            case ME_FRAGMENT:
                showMeFragment();
                break;
            default:

                break;
        }
        mCurrentIndex = index;
    }

    private void showMeFragment() {
        if (!meMains.isSelected())
            meMains.setSelected(true);
        if (mMeFragment == null) {
            mMeFragment = MeFragment.newInstance();
            addFragment(R.id.framelayout_mains, mMeFragment, ME_FRAGMENT);
        } else if (isRestart) {
            getFragmentTransaction().show(mMeFragment).commit();
            isRestart = false;
        } else {
            showFragment(mMeFragment);
        }
        EventBus.getDefault().post(new RefreshMainEvent(3));

    }

    private void showFindFragment() {
        if (!findtravelMains.isSelected()) {
            findtravelMains.setSelected(true);
        }
        if (mCustomerFragment == null) {
            mCustomerFragment = CustomerFragment.newInstance();
            addFragment(R.id.framelayout_mains, mCustomerFragment, CUSTOMER_FRAGMENT);
        } else if (isRestart = true) {
            isRestart = false;
            getFragmentTransaction().show(mCustomerFragment).commit();
        } else {
            showFragment(mCustomerFragment);
        }

    }

    private void showWechatFragment() {

        if (!cityfinderMains.isSelected()) {
            cityfinderMains.setSelected(true);
        }
        if (mVIPFragment == null) {
            mVIPFragment = VIPFragment.newInstance();
            addFragment(R.id.framelayout_mains, mVIPFragment, VIP_FRAGMENT);
        } else if (isRestart = true) {
            isRestart = false;
            getFragmentTransaction().show(mVIPFragment).commit();
        } else {
            showFragment(mVIPFragment);
        }
        EventBus.getDefault().post(new RefreshMainEvent(1));
    }

    private void showHomeFragment() {
        if (recommendMains.getVisibility() != View.VISIBLE) {
            return;
        }
        if (!recommendMains.isSelected()) {
            recommendMains.setSelected(true);
        }
        if (mHomepageFragment == null) {
            Logger.e("恢复状态：" + "null");
            mHomepageFragment = HomepageFragment.newInstance();
            addFragment(R.id.framelayout_mains, mHomepageFragment, HOMEPAGE_FRAGMENT);
        } else if (isRestart = true) {
            isRestart = false;
            getFragmentTransaction().show(mHomepageFragment).commit();
        } else {
            showFragment(mHomepageFragment);
        }
        EventBus.getDefault().post(new RefreshMainEvent(0));
    }

    private void hideAllFragment() {
        if (mHomepageFragment != null) {
            hideFragment(mHomepageFragment);
        }
        if (mVIPFragment != null) {
            hideFragment(mVIPFragment);
        }
        if (mCustomerFragment != null) {
            hideFragment(mCustomerFragment);
        }
        if (mMeFragment != null) {
            hideFragment(mMeFragment);
        }
        if (recommendMains.getVisibility() == View.VISIBLE) {
            recommendMains.setSelected(false);
        }
        findtravelMains.setSelected(false);
        cityfinderMains.setSelected(false);
        meMains.setSelected(false);
    }

    @Override
    public void initPresenter() {

    }

    @OnClick({R.id.recommend_mains, R.id.cityfinder_mains, R.id.findtravel_mains, R.id.me_mains})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recommend_mains:
                if (!mCurrentIndex.equals(HOMEPAGE_FRAGMENT))
                    switchToFragment(HOMEPAGE_FRAGMENT);
                break;
            case R.id.cityfinder_mains:
                if (!mCurrentIndex.equals(VIP_FRAGMENT))
                    switchToFragment(VIP_FRAGMENT);
                break;
            case R.id.findtravel_mains:
                if (!mCurrentIndex.equals(CUSTOMER_FRAGMENT))
                    switchToFragment(CUSTOMER_FRAGMENT);
                break;
            case R.id.me_mains:
                if (!mCurrentIndex.equals(ME_FRAGMENT))
                    switchToFragment(ME_FRAGMENT);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Const.NEWSFRAGMENT_CATEGORYACTIVITY_REQUESTCODE
                && resultCode == Const.NEWSFRAGMENT_CATEGORYACTIVITY_RESULTCODE) {
            mHomepageFragment.initView();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnRefreshNewsFragmentEvent(RefreshNewsFragmentEvent event) {
        startActivityForResult(new Intent(MainsActivity.this, CategoryActivity.class), event.getMark_code());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ONLogoutEvent(LogoutBean logoutBean) {
        finish();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        for (Fragment fragment :
                getBaseFragmentManager().getFragments()) {
            getFragmentTransaction().remove(fragment);
        }
        super.onDestroy();
        BaseApplication.setMainActivity(null);
        IMMLeaks.fixFocusedViewLeak(getApplication());//解决 Android 输入法造成的内存泄漏问题。
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLogInfo();
    }

    public void refreshLogInfo() {
        String AllLog = "";
        for (String log : logList) {
            AllLog = AllLog + log + "\n\n";
        }
        Logger.e(AllLog);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mCurrentIndex", mCurrentIndex);
        Logger.e("保存状态");
    }

    /**
     * 监听用户按返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    private boolean mIsExit;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 用于优雅的退出程序(当从其他地方退出应用时会先返回到此页面再执行退出)
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(Const.TAG_EXIT, false);
            if (isExit) {
                finish();
            }
        }
    }
}
