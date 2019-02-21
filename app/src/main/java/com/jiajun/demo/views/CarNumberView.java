package com.jiajun.demo.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiajun.demo.R;

/**
 * Created by dan on 2017/11/28/028.
 */

public class CarNumberView extends LinearLayout implements OnClickView {

    public CarNumberView(Context context) {
        super(context, null, 0);
    }

    public CarNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CarNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        LinearLayout view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_car_number, null);
        for (int i = 0; i < 7; i++) {
            final int index = i;
            TextView child = (TextView) view.getChildAt(i);
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickText((TextView) view, index);
                }
            });
        }
    }

    @Override
    public void onClickText(TextView textview, int index) {

    }


}
