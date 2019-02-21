package com.jiajun.demo.moudle.licence_recognize;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.caijia.selectpicture.ui.SelectMediaActivity;
import com.caijia.selectpicture.utils.MediaType;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;
import com.jiajun.customercamera.EasyCamera;
import com.jiajun.customercamera.util.DisplayUtil;
import com.jiajun.customercamera.util.FileUtil;
import com.jiajun.customercamera.view.MaskView;
import com.jiajun.demo.R;
import com.jiajun.demo.base.BaseActivity;
import com.jiajun.demo.moudle.me.PersonalInfoActivity;
import com.jiajun.demo.util.BitmapUtil;
import com.jiajun.demo.util.FileUtils;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


/**
 * Author: dan
 * Des: LicenceCameraActivity
 */
@RuntimePermissions
public class LicenceCameraActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = LicenceCameraActivity.class.getSimpleName();
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String FRAGMENT_DIALOG = "dialog";

    //控件View
    private CameraView mCameraView;
    private MaskView viewMask;
    private ImageButton ibtCapture;
    private TextView ivReturn;

    private int RECT_WIDTH; //拍摄区域宽度
    private int RECT_HEIGHT; //拍摄区域高度

    private float ratio; //高宽比
    private float cameraRatio; // 相机高宽比

    private Point rectPictureSize;
    private int mCameraWidth;
    private int mCameraHeight;
    private Uri imageUri;
    private String imagePath;
    private int leftRight;
    private int topBottom;


    @Override
    protected int getTranslucentColor() {
        return R.color.black;
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_licence_camera);
    }

    @Override
    public void initPresenter() {
        setupViews(getIntent());
        initView();
        mCameraView.addCallback(mCallback);
    }

    private void setupViews(@NonNull Intent mIntent) {
        leftRight = mIntent.getIntExtra(EasyCamera.EXTRA_MARGIN_BY_WIDTH, 0);
        topBottom = mIntent.getIntExtra(EasyCamera.EXTRA_MARGIN_BY_HEIGHT, 0);
        ratio = mIntent.getFloatExtra(EasyCamera.EXTRA_VIEW_RATIO, 1f);
        imageUri = mIntent.getParcelableExtra(EasyCamera.EXTRA_OUTPUT_URI);
        imagePath = FileUtil.getRealFilePath(this, imageUri);
    }

    private void initView() {
        mCameraView = findViewById(R.id.camera_view);
        viewMask = findViewById(R.id.view_mask);
        ibtCapture = findViewById(R.id.ibt_capture);
        ivReturn = findViewById(R.id.iv_return);

        ibtCapture.setOnClickListener(this);
        ivReturn.setOnClickListener(this);

        AspectRatio currentRatio = mCameraView.getAspectRatio();
        cameraRatio = currentRatio.toFloat();
        mCameraWidth = (int) DisplayUtil.getScreenWidth(this);
        mCameraHeight = (int) (mCameraWidth * cameraRatio);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = mCameraWidth;
        layoutParams.height = mCameraHeight;
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        viewMask.setLayoutParams(layoutParams);

        if (ratio > cameraRatio) {
            //如果传过来的ratio比屏幕的高宽比大，那么需要以屏幕高为标准
            RECT_HEIGHT = mCameraHeight - topBottom; //以宽为准，到CameraView上下保留一定的间距
            RECT_WIDTH = (int) (RECT_HEIGHT / ratio);
        } else {
            RECT_WIDTH = mCameraWidth - leftRight; //以宽为准，到CameraView两边保留一定的间距
            RECT_HEIGHT = (int) (RECT_WIDTH * ratio);
        }
        if (viewMask != null) {
            Rect screenCenterRect = DisplayUtil.createCenterScreenRect(mCameraWidth, mCameraHeight, RECT_WIDTH, RECT_HEIGHT);
            viewMask.setCenterRect(screenCenterRect);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        LicenceCameraActivityPermissionsDispatcher.startCameraViewWithCheck(LicenceCameraActivity.this);
    }

    @Override
    protected void onPause() {
        mCameraView.stop();
        super.onPause();
    }

    private CameraView.Callback mCallback = new CameraView.Callback() {

        @Override
        public void onCameraOpened(CameraView cameraView) {
            super.onCameraOpened(cameraView);
            Log.d(TAG, "onCameraOpened");
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            super.onCameraClosed(cameraView);
            Log.d(TAG, "onCameraClosed");
        }

        @Override
        public void onPictureTaken(CameraView cameraView, final byte[] data) {
            Log.d(TAG, "onPictureTaken " + data.length);
            Bitmap bitmap = null;
            int degree; //图片被旋转的角度
            if (data != null) {
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成类图
            }
            //保存图片到sdcard
            if (bitmap != null) {
                degree = FileUtil.getRotateDegree(data);
                if (degree != 0) {
                    //如果图片被系统旋转了，就旋转过来
                    bitmap = FileUtil.rotateBitmap(degree, bitmap);
                }
                if (rectPictureSize == null) {
                    rectPictureSize = DisplayUtil.createCenterPictureRect(ratio, cameraRatio, bitmap.getWidth(), bitmap.getHeight());
                }
                int x = bitmap.getWidth() / 2 - rectPictureSize.x / 2;
                int y = bitmap.getHeight() / 2 - rectPictureSize.y / 2;
                Bitmap rectBitmap = Bitmap.createBitmap(bitmap, x, y, rectPictureSize.x, rectPictureSize.y);
                int imageWidth = rectBitmap.getWidth();
                int imageHeight = rectBitmap.getHeight();


                String fileName = FileUtils.SDPATH + System.currentTimeMillis() + ".jpg";
                FileUtils.saveBitmap(rectBitmap, fileName);
                String newFile = FileUtils.SDPATH + System.currentTimeMillis() + ".jpg";
                FileUtils.compressFile(fileName, newFile);
                Intent intent = new Intent(getContext(), RecognizeResponseActivity.class);
                intent.putExtra("filePath", newFile);
                startActivity(intent);
                finish();
//                setResultUri(imageUri, imageWidth, imageHeight);

                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                if (!rectBitmap.isRecycled()) {
                    rectBitmap.recycle();
                }
                finish();
            }
        }

    };

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ibt_capture) {
            mCameraView.takePicture();
        } else if (i == R.id.iv_return) {
            LicenceCameraActivity.this.finish();
        }
    }


    /**
     * @param uri         图片Uri
     * @param imageWidth  图片宽
     * @param imageHeight 图片高
     */
    protected void setResultUri(Uri uri, int imageWidth, int imageHeight) {
        setResult(RESULT_OK, new Intent()
                .putExtra(EasyCamera.EXTRA_OUTPUT_URI, uri)
                .putExtra(EasyCamera.EXTRA_OUTPUT_IMAGE_WIDTH, imageWidth)
                .putExtra(EasyCamera.EXTRA_OUTPUT_IMAGE_HEIGHT, imageHeight)
        );
    }


    /******************************** 动态权限申请 ********************************/
    @NeedsPermission({
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startCameraView() {
        mCameraView.start();
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
                        finish();
                    }
                })
                .setPositiveButton("赋予权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LicenceCameraActivityPermissionsDispatcher.startCameraViewWithCheck(LicenceCameraActivity.this);
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
        LicenceCameraActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    /******************************** 以上是权限部分 ********************************/


}
