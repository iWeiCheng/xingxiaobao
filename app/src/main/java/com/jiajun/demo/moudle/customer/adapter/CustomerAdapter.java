package com.jiajun.demo.moudle.customer.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiajun.demo.R;
import com.jiajun.demo.moudle.customer.entities.CustomerBean;
import com.jiajun.demo.moudle.homepage.entities.HomePageBean;
import com.jiajun.demo.moudle.webview.WebViewActivity;

import java.util.List;


public class CustomerAdapter extends BaseQuickAdapter<CustomerBean, BaseViewHolder> {


    public CustomerAdapter(List<CustomerBean> data) {
        super(R.layout.item_customer, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, final CustomerBean item) {
        helper.setText(R.id.item_car_number, item.getCar_number());
        helper.setText(R.id.item_car_desc, item.getCar_name());
        helper.setText(R.id.item_owner_name, item.getName());
        helper.getView(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", item.getUrl());
                mContext.startActivity(intent);
            }
        });
    }
}
