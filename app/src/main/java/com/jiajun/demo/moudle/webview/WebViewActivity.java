package com.jiajun.demo.moudle.webview;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bqs.risk.df.android.BqsDF;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.jiajun.customercamera.EasyCamera;
import com.jiajun.demo.R;
import com.jiajun.demo.app.BaseApplication;
import com.jiajun.demo.base.BaseActivity;
import com.jiajun.demo.model.BaseBean;
import com.jiajun.demo.moudle.account.LoginActivity;
import com.jiajun.demo.moudle.licence_recognize.LicenceCameraActivity;
import com.jiajun.demo.moudle.licence_recognize.LicenceEvent;
import com.jiajun.demo.moudle.yd.entities.GetSignBean;
import com.jiajun.demo.network.BaseObserver;
import com.jiajun.demo.network.Network;
import com.jiajun.demo.util.BitmapUtil;
import com.jiajun.demo.util.CoordinateUtil;
import com.jiajun.demo.util.FileUtils;
import com.jiajun.demo.util.RandomNum;
import com.jiajun.demo.util.SignatureUtil;
import com.jiajun.demo.util.ToastUtil;
import com.msyd.msydsdk.XdSdkManager;
import com.orhanobut.logger.Logger;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Cookie;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * webView详情页
 * Created by danjj on 2016/12/8.
 */
@RuntimePermissions
public class WebViewActivity extends BaseActivity implements View.OnClickListener, ShareFragment.OnClickShareListener {

    @BindView(R.id.titleLeft_textView)
    TextView titleLeftTextView;
    @BindView(R.id.titleRight)
    TextView titleRight;
    @BindView(R.id.titleRight_service)
    ImageView service;
    @BindView(R.id.titleCeneter_textView)
    TextView titleCeneterTextView;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.no_network_layout)
    RelativeLayout noNetWorkLayout;
    @BindView(R.id.reload)
    TextView reload;
    @BindView(R.id.more)
    ImageView more;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.tv_close)
    TextView tvClose;

    private String url1;
    private String backurl;
    private String share_title;
    private String share_image;
    private String share_content;
    private String share_link;

    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    private Uri imageUri;
    private String imagePaths;
    private String compressPath;
    private BaseApplication application;
    private Handler handler = new MyHandler(this);

    private static class MyHandler extends Handler {
        WeakReference<WebViewActivity> weakReference;

        public MyHandler(WebViewActivity webViewActivity) {
            weakReference = new WeakReference<>(webViewActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (weakReference.get().share_link != null && weakReference.get().share_link.length() > 0) {
                    weakReference.get().titleRight.setVisibility(View.VISIBLE);
                } else {
                    weakReference.get().titleRight.setVisibility(View.GONE);
                }
            } else if (msg.what == 2) {
                try {
                    String latitude = weakReference.get().preferences.getString("latitude", "");
                    String longitude = weakReference.get().preferences.getString("longitude", "");
                    double lat = 0, lon = 0;
                    if (latitude.length() > 0) {
                        lat = Double.parseDouble(latitude);
                    }
                    if (longitude.length() > 0) {
                        lon = Double.parseDouble(longitude);
                    }
                    Location location = new Location(LocationManager.GPS_PROVIDER);
                    location.setLatitude(lat);
                    location.setLongitude(lon);
                    XdSdkManager.getInstance().init(weakReference.get(), weakReference.get().signBean.getMac(),
                            weakReference.get().signBean.getSign(), BqsDF.getTokenKey(), location,new XdSdkManager.BusinessEndCallback() {
                        @Override
                        public void onBusinessEnd(String s, String s1) {
                            Logger.e(s + "---" + s1);
                        }
                    });
                } catch (Exception e) {
                    Logger.e(e.getMessage());
                }

            }
        }
    }

    private ShareFragment share_fragment;
    private IWXAPI api;

    private float ratio = 0.76f; //取景框高宽比

    @Override
    protected boolean supportEventBus() {
        return true;
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_webview_ysx);
    }

    @Override
    public void initPresenter() {
        unsubscribe();
    }

    @Override
    protected void getExtra(@NonNull Bundle bundle) {
        super.getExtra(bundle);
        url1 = bundle.getString("url");
    }

    @Override
    protected void setListener(Bundle savedInstanceState) {
        super.setListener(savedInstanceState);
        titleLeftTextView.setOnClickListener(this);
        titleRight.setOnClickListener(this);
        service.setOnClickListener(this);
        reload.setOnClickListener(this);
        more.setOnClickListener(this);
        tvRefresh.setOnClickListener(this);
        tvClose.setOnClickListener(this);
        llMore.setOnClickListener(this);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        //小车童的链接跳转到小车童
        if (url1.contains("tb.sjdcar.com") || url1.contains("tc.sjdcar.com")) {
            Intent intent = new Intent(this, NewWebViewActivity.class);
            intent.putExtra("url", url1);
            startActivity(intent);
            finish();
        }
//        BaseApplication application = (BaseApplication) getApplication();
        api = BaseApplication.api;
        Intent intent = getIntent();
        titleLeftTextView.setVisibility(View.VISIBLE);
//        titleRight.setVisibility(View.VISIBLE);
        titleRight.setBackgroundResource(R.drawable.share);
        service.setVisibility(View.GONE);
        application = (BaseApplication) getApplication();
        webView.getSettings().setJavaScriptEnabled(true);
        synCookies(this, url1);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        webView.addJavascriptInterface(this, "app");
        webView.setWebChromeClient(new WebChromeClient());
        //启用数据库
        webView.getSettings().setDatabaseEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        //启用地理定位
        webView.getSettings().setGeolocationEnabled(true);
        //设置定位的数据库路径
        webView.getSettings().setGeolocationDatabasePath(dir);
        //最重要的方法，一定要设置，这就是出不来的主要原因
        webView.getSettings().setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

//        url1 = "https://www.baidu.com/";
        webView.loadUrl(url1);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (!error.getDescription().equals("net::ERR_FILE_NOT_FOUND")) {
                    noNetWorkLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("url", url);
                if (url.contains("/user/login")) {
                    view.clearCache(true);
                    ClearableCookieJar cj = (ClearableCookieJar) OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
                    cj.clear();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("tb.sjdcar.com") || url.contains("tc.sjdcar.com")) {
                    Intent intent = new Intent(getContext(), NewWebViewActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                    return true;
                }
                if (url.startsWith("weixin://wap/pay?")) {
                    Intent intent = null;
//                    try {
//                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
//                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                        intent.setComponent(null);
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    }
                    return true;
                }
                if (url.contains("platformapi/startapp") || url.contains("platformapi/startApp")) {
                    startAlipayActivity(url);
                    // android  6.0 两种方式获取intent都可以跳转支付宝成功,7.1测试不成功
                } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                        && (url.contains("platformapi") && (url.contains("startapp")) || url.contains("startApp"))) {
                    startAlipayActivity(url);
                } else if (url.startsWith("login_tel:")) {
                    callPhone(url);
                    return true;
                } else {
                    //H5微信支付要用，不然说"商家参数格式有误"
                    Map<String, String> extraHeaders = new HashMap<String, String>();
                    extraHeaders.put("Referer", "tb.sjdcar.com");
                    view.loadUrl(url, extraHeaders);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                dismissDialog();
                noNetWorkLayout.setVisibility(View.GONE);
                webView.post(new Runnable() {

                    @Override
                    public void run() {
                        webView.loadUrl("javascript:window.java_obj.getSource('<head>'+" +
                                "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                    }
                });

                titleCeneterTextView.setText(view.getTitle());
                super.onPageFinished(view, url);
            }

        });
        webView.setWebChromeClient(new

                                           WebChromeClient() {

                                               @Override
                                               public void onReceivedTitle(WebView view, String title) {
                                                   if (!title.startsWith("http")) {
                                                       titleCeneterTextView.setText(title);
                                                   }
                                               }

                                               public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                                                   callback.invoke(origin, true, false);
                                                   super.onGeolocationPermissionsShowPrompt(origin, callback);
                                               }

                                               @Override
                                               public boolean onShowFileChooser(WebView webView,
                                                                                ValueCallback<Uri[]> filePathCallback,
                                                                                FileChooserParams fileChooserParams) {
                                                   mUploadCallbackAboveL = filePathCallback;
                                                   WebViewActivityPermissionsDispatcher.openSelectWithCheck(WebViewActivity.this);
//                take();
                                                   return true;
                                               }

                                               public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                                                   mUploadMessage = uploadMsg;
                                                   WebViewActivityPermissionsDispatcher.openSelectWithCheck(WebViewActivity.this);
//                take();
                                               }

                                               public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                                                   mUploadMessage = uploadMsg;
                                                   WebViewActivityPermissionsDispatcher.openSelectWithCheck(WebViewActivity.this);
//                take();
                                               }

                                               public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                                                   mUploadMessage = uploadMsg;
                                                   WebViewActivityPermissionsDispatcher.openSelectWithCheck(WebViewActivity.this);
//                take();
                                               }

                                           });
    }

    //=======================动态权限的申请===========================================================<
    @NeedsPermission({Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void openSelect() {
        take();
    }

    /**
     * 为什么要获取这个权限给用户的说明
     *
     * @param request
     */
    @OnShowRationale({Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("有部分权限需要你的授权")
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
    @OnPermissionDenied({Manifest.permission.CAMERA,
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
                        WebViewActivityPermissionsDispatcher.openSelectWithCheck(WebViewActivity.this);
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
        WebViewActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void take() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_aaa111222333bbb" + ".jpg");
        File temp = new File(imageStorageDir + File.separator + "temp" + ".jpg");
        imageUri = Uri.fromFile(file);
        imagePaths = file.getAbsolutePath();
        compressPath = temp.getAbsolutePath();
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);
        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }

    public void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.setAcceptCookie(true);
        Iterator<Cookie> it = Network.cookieCache.iterator();
        while (it.hasNext()) {
            Cookie cookie1 = it.next();
            String name = cookie1.name();
            String valie = cookie1.value();
            String domain = cookie1.domain();
//            if (cookie1.name().contains("JSESSIONID")) {
            String cookieString = cookie1.name() + "=" + cookie1.value() + "; domain=" + cookie1.domain();
            cookieManager.setCookie(cookie1.domain(), cookieString);
//                cookieManager.setCookie(url, cookieString);
//            }
        }
        CookieSyncManager.getInstance().sync();
    }


    @Override
    public void onClickShare(final int position) {
        share_fragment.dismiss();
        if (share_link == null || share_link.length() == 0) {
            share_title = preferences.getString("share_title", "");
            share_link = preferences.getString("share_url", "");
            share_image = preferences.getString("share_image_url", "");
            share_content = preferences.getString("share_content", "");
        }
        share_image = preferences.getString("share_image_url", "");
        switch (position) {
            case 2://QQ好友
                final Bundle params2 = new Bundle();
                params2.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params2.putString(QQShare.SHARE_TO_QQ_TITLE, share_title);
                params2.putString(QQShare.SHARE_TO_QQ_SUMMARY, share_content);
                params2.putString(QQShare.SHARE_TO_QQ_TARGET_URL, share_link);
                params2.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, share_image);
                params2.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));
                BaseApplication.mTencent.shareToQQ(WebViewActivity.this, params2, qqShareListener);
                break;
            case 3://QQ空间

                final Bundle params = new Bundle();
                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, share_title);
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, share_content);
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, share_link);
                ArrayList<String> imgUrlList = new ArrayList<>();
                imgUrlList.add(share_image);
//                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, share_image);
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
                params.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));
                BaseApplication.mTencent.shareToQzone(WebViewActivity.this, params, qqShareListener);
                break;
            case 5://短信
                sendSMS();
                break;

        }
        if (position != 2 && position != 3 && position != 5) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        WXWebpageObject webpageObject = new WXWebpageObject();
                        webpageObject.webpageUrl = share_link;
                        WXMediaMessage msg = new WXMediaMessage(webpageObject);
                        msg.title = share_title;
                        msg.description = share_content;
                        Bitmap thumb = BitmapFactory.decodeStream(new URL(share_image).openStream());
                        //注意下面的这句压缩，120，150是长宽。
                        //一定要压缩，不然会分享失败
                        Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 120, 150, true);
                        //Bitmap回收
                        thumb.recycle();
                        msg.thumbData = BitmapUtil.bitmapToByte(thumbBmp);
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = System.currentTimeMillis() + getResources().getString(R.string.app_name);
                        req.message = msg;
                        if (position == 0) {
                            req.scene = SendMessageToWX.Req.WXSceneTimeline;
                        } else if (position == 1) {
                            req.scene = SendMessageToWX.Req.WXSceneSession;
                        } else if (position == 4) {
                            req.scene = SendMessageToWX.Req.WXSceneFavorite;
                        }
                        api.sendReq(req);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

//            Glide.with(this)
//                    .asBitmap()
//                    .load(share_image)
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                            super.onLoadFailed(errorDrawable);
//                            Bitmap resource = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
//                            switch (position) {
//                                case 0://微信朋友圈
//                                    WXWebpageObject webpageObject = new WXWebpageObject();
//                                    webpageObject.webpageUrl = share_link;
//                                    WXMediaMessage msg = new WXMediaMessage(webpageObject);
//                                    msg.title = share_title;
//                                    msg.description = share_content;
//                                    msg.thumbData = BitmapUtil.bitmapToByte(resource);
//                                    SendMessageToWX.Req req = new SendMessageToWX.Req();
//                                    req.transaction = System.currentTimeMillis() + getResources().getString(R.string.app_name);
//                                    req.message = msg;
//                                    req.scene = SendMessageToWX.Req.WXSceneTimeline;
//                                    api.sendReq(req);
//                                    break;
//                                case 1://微信好友
//                                    WXWebpageObject webpageObject2 = new WXWebpageObject();
//                                    webpageObject2.webpageUrl = share_link;
//                                    WXMediaMessage msg2 = new WXMediaMessage(webpageObject2);
//                                    msg2.title = share_title;
//                                    msg2.description = share_content;
//                                    msg2.thumbData = BitmapUtil.bitmapToByte(resource);
//                                    SendMessageToWX.Req req2 = new SendMessageToWX.Req();
//                                    req2.transaction = System.currentTimeMillis() + getResources().getString(R.string.app_name);
//                                    req2.message = msg2;
//                                    req2.scene = SendMessageToWX.Req.WXSceneSession;
//                                    api.sendReq(req2);
//                                    Log.d("wx:", api.sendReq(req2) + "");
//                                    break;
//
//                                case 4://微信收藏
//                                    WXWebpageObject webpageObject3 = new WXWebpageObject();
//                                    webpageObject3.webpageUrl = share_link;
//                                    WXMediaMessage msg3 = new WXMediaMessage(webpageObject3);
//                                    msg3.title = share_title;
//                                    msg3.description = share_content;
//                                    msg3.thumbData = BitmapUtil.bitmapToByte(resource);
//                                    SendMessageToWX.Req req3 = new SendMessageToWX.Req();
//                                    req3.transaction = System.currentTimeMillis() + getResources().getString(R.string.app_name);
//                                    req3.message = msg3;
//                                    req3.scene = SendMessageToWX.Req.WXSceneFavorite;
//                                    api.sendReq(req3);
//                                    break;
//                            }
//                        }
//
//                        @Override
//                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                            switch (position) {
//                                case 0://微信朋友圈
//                                    WXWebpageObject webpageObject = new WXWebpageObject();
//                                    webpageObject.webpageUrl = share_link;
//                                    WXMediaMessage msg = new WXMediaMessage(webpageObject);
//                                    msg.title = share_title;
//                                    msg.description = share_content;
//                                    msg.thumbData = BitmapUtil.bitmapToByte(resource);
//
//                                    SendMessageToWX.Req req = new SendMessageToWX.Req();
//                                    req.transaction = System.currentTimeMillis() + getResources().getString(R.string.app_name);
//                                    req.message = msg;
//                                    req.scene = SendMessageToWX.Req.WXSceneTimeline;
//                                    api.sendReq(req);
//                                    break;
//                                case 1://微信好友
//                                    WXWebpageObject webpageObject2 = new WXWebpageObject();
//                                    webpageObject2.webpageUrl = share_link;
//                                    WXMediaMessage msg2 = new WXMediaMessage(webpageObject2);
//                                    msg2.title = share_title;
//                                    msg2.description = share_content;
//                                    msg2.thumbData = BitmapUtil.bitmapToByte(resource);
//                                    Log.d("length", msg2.thumbData.length + "");
//                                    SendMessageToWX.Req req2 = new SendMessageToWX.Req();
//                                    req2.transaction = System.currentTimeMillis() + getResources().getString(R.string.app_name);
//                                    req2.message = msg2;
//                                    req2.scene = SendMessageToWX.Req.WXSceneSession;
//                                    api.sendReq(req2);
//                                    Log.d("wx:", api.sendReq(req2) + "");
//                                    break;
//
//                                case 4://微信收藏
//                                    WXWebpageObject webpageObject3 = new WXWebpageObject();
//                                    webpageObject3.webpageUrl = share_link;
//                                    WXMediaMessage msg3 = new WXMediaMessage(webpageObject3);
//                                    msg3.title = share_title;
//                                    msg3.description = share_content;
//                                    msg3.thumbData = BitmapUtil.bitmapToByte(resource);
//                                    SendMessageToWX.Req req3 = new SendMessageToWX.Req();
//                                    req3.transaction = System.currentTimeMillis() + getResources().getString(R.string.app_name);
//                                    req3.message = msg3;
//                                    req3.scene = SendMessageToWX.Req.WXSceneFavorite;
//                                    api.sendReq(req3);
//                                    break;
//                            }
//                        }
//                    });
        }
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            ToastUtil.showToast(getContext(), "分享取消");
        }

        @Override
        public void onComplete(Object response) {
            ToastUtil.showToast(getContext(), "分享成功");

        }

        @Override
        public void onError(UiError e) {
            Log.d("share", "onError:" + e.errorDetail);
            ToastUtil.showToast(getContext(), "分享失败");
        }
    };

    /**
     * 发短信
     */

    private void sendSMS() {
        String smsBody = share_title + ":" + share_content + "  这是网址《" + share_link + "》";
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        //短信内容
        sendIntent.putExtra("sms_body", smsBody);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivityForResult(sendIntent, 1002);
    }

    /**
     * 逻辑处理
     *
     * @author linzewu
     */
    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void getSource(String html) {
//            Log.d("html=", html);
            Document document = Jsoup.parse(html);
            getShareDate(document);
        }
    }

    public void getShareDate(Document doc) {
        Elements E = doc.select("meta");
        backurl = doc.select("meta").attr("backurl");//新增
        share_title = doc.select("meta").attr("share_title");
        share_image = doc.select("meta").attr("share_image");
        share_content = doc.select("meta").attr("share_content");
        share_link = doc.select("meta").attr("share_link");
        handler.sendEmptyMessage(1);
    }

    public Bitmap getAppIcon() {
        getPackageManager().getApplicationLogo(getApplicationInfo());
        Drawable d = getPackageManager().getApplicationIcon(getApplicationInfo()); //xxx根据自己的情况获取drawable
        BitmapDrawable bd = (BitmapDrawable) d;
        return drawableToBitmap(bd);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                Log.e("result", result + "");
                if (result == null) {
                    afterOpenCamera();
                    imageUri = Uri.fromFile(new File(compressPath));
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;
                    Log.e("imageUri", imageUri + "");
                } else {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }
            }
        }
        if (requestCode == 10103) {
            Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
        }
    }

    /**
     * 解决拍照后在相册中找不到的问题
     */
    private void addImageGallery(File file) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    /**
     * 拍照结束后
     */
    private void afterOpenCamera() {
        File f = new File(imagePaths);
        addImageGallery(f);
        File newFile = FileUtils.compressFile(f.getPath(), compressPath);
    }

    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                afterOpenCamera();
                imageUri = Uri.fromFile(new File(compressPath));
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

        return;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (backurl != null && !backurl.equals("") && backurl.length() == 1) {
                int steps = Integer.parseInt(backurl);
                for (int i = 0; i < steps; i++) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        clearCookie();
                    }
                }
                return false;
            } else {
//                Intent in = new Intent(this, MainsActivity.class);
//                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(in);
//                setResult(RESULT_OK);
                clearCookie();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titleRight://分享
                //appkey:584a4a6d8f4a9d7121000f9c
                if (share_fragment == null) {
                    share_fragment = ShareFragment.getInstance();
                    share_fragment.setListener(this);
                }
                share_fragment.show(getSupportFragmentManager(), "");
                break;
            case R.id.titleLeft_textView:
                if (backurl != null && !backurl.equals("") && backurl.length() == 1) {
                    int steps = Integer.parseInt(backurl);
                    for (int i = 0; i < steps; i++) {
                        if (webView.canGoBack()) {
                            webView.goBack();
                        } else {
                            clearCookie();
                        }
                    }
                } else {
//                    Intent in = new Intent(this, MainsActivity.class);
//                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(in);
//                    setResult(RESULT_OK);
                    clearCookie();
                }
                break;
            case R.id.reload:
                showProgressDialog();
                webView.reload();
                break;
            case R.id.titleRight_service:
                String title = getResources().getString(R.string.app_name);
                ConsultSource source = new ConsultSource("http://www.implus100.com", "", "custom");
                Unicorn.openServiceActivity(this, // 上下文
                        "返回", // 聊天窗口的标题
                        source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                );
                break;

            case R.id.more:
                if (llMore.getVisibility() == View.VISIBLE) {
                    llMore.setVisibility(View.GONE);
                } else {
                    llMore.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_refresh:
                webView.reload();
                llMore.setVisibility(View.GONE);
                break;
            case R.id.tv_close:
                clearCookie();
                break;
            case R.id.ll_more:
                llMore.setVisibility(View.GONE);
                break;
        }
    }

    private void callPhone(String phone) {
        Intent call_intent = new Intent(Intent.ACTION_CALL, Uri.parse(phone));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ToastUtil.showToast(this, "您禁止了拨打电话权限，请在设置-权限管理中允许应用拨打电话");
            return;
        }
        startActivity(call_intent);
    }

    @JavascriptInterface
    public void toFirstPage() {
        clearCookie();
    }

    private void clearCookie() {
        //清除WebView中cookie
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        finish();
    }

    // 调起支付宝并跳转到指定页面
    private void startAlipayActivity(String url) {
        Intent intent;
        try {
            intent = Intent.parseUri(url,
                    Intent.URI_INTENT_SCHEME);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setComponent(null);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void startIdentify() {
        String SAMPLE_CROPPED_IMAGE_NAME = "cropImage_" + System.currentTimeMillis() + ".png";
        Uri destination = Uri.fromFile(new File(getContext().getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME));
        EasyCamera.create(destination)
                .withViewRatio(ratio)
                .withMarginCameraEdge(50, 50)
                .start(this, LicenceCameraActivity.class);
    }


    /**
     * 设置车牌
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setLicence(final LicenceEvent event) {
        if (event.getNumber().length() > 0) {
            webView.post(new Runnable() {

                @Override
                public void run() {
                    webView.loadUrl("javascript:setCarNumber('" + event.getNumber() + "')");
                }
            });
        }
    }

    @JavascriptInterface
    public void sendTokenkeytoHtml() {
        final String latitude = preferences.getString("latitude", "");
        final String longitude = preferences.getString("longitude", "");
//        String str = "javascript:setTokenParams('" + BqsDF.getTokenKey() + "','" + longitude + "','" + latitude + "')";
        webView.post(new Runnable() {

            @Override
            public void run() {
                webView.loadUrl("javascript:setTokenParams('" + BqsDF.getTokenKey() + "','" + longitude + "','" + latitude + "')");
            }
        });

    }

    /**
     * 调用白骑士
     *
     * @param outTradeno
     */
    @JavascriptInterface
    public void startYDXDCreitSDK(String outTradeno) {
        getSdkSign(outTradeno,"1");
    }
    /**
     * 调用白骑士
     *
     * @param outTradeno
     */
    @JavascriptInterface
    public void startYDXDCreitSDKWithTypeOne(String outTradeno) {
        getSdkSign(outTradeno,"2");
    }

    private GetSignBean signBean;
    private Subscription mSubscription;
    private BaseObserver<GetSignBean> observer = new BaseObserver<GetSignBean>() {

        @Override
        public void onSuccess(GetSignBean getSignBean) {
            String sign = getSignBean.getSign();
            String mac = getSignBean.getMac();
            String tokenkey = BqsDF.getTokenKey();
            signBean = getSignBean;
            Logger.e("sign:" + sign + "   mac:" + mac);
            handler.sendEmptyMessage(2);
        }

        @Override
        public void onError(int code, String message, BaseBean baseBean) {
            ToastUtil.showToast(getContext(), message);
        }

        @Override
        public void networkError(Throwable e) {
            ToastUtil.showToast(getContext(), "网络错误");
        }
    };

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }


    public void getSdkSign(String outTradeno,String type) {
        Map<String, String> map = new HashMap<>();
        map.put("clietOs", "2");
        String str_random = RandomNum.getrandom();
        map.put("outTradeno", outTradeno);
        map.put("sdkType", type);

//        map.put("random", str_random);
        String str_signature = SignatureUtil.getSignature(map);
//        map.put("signature", str_signature);

        mSubscription = Network.getYdGetSignApi().getSign(Network.YDSDK, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
