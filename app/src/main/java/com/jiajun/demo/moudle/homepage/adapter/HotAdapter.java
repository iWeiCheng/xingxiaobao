package com.jiajun.demo.moudle.homepage.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiajun.demo.R;
import com.jiajun.demo.moudle.homepage.entities.HomePageBean;
import com.jiajun.demo.moudle.webview.NewWebViewActivity;
import com.jiajun.demo.moudle.webview.WebViewActivity;
import com.jiajun.demo.network.Network;

import java.util.List;


public class HotAdapter extends BaseQuickAdapter<HomePageBean.HostMenuBean, BaseViewHolder> {


    public HotAdapter(List<HomePageBean.HostMenuBean> data) {
        super(R.layout.item_hot, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, final HomePageBean.HostMenuBean item) {
        helper.setText(R.id.item_title, item.getTitle());
        helper.setText(R.id.item_desc, item.getDesc());
        ImageView imageView = helper.getView(R.id.item_img_big);
        ImageView imageView_logo = helper.getView(R.id.item_img_logo);
        Glide.with(mContext).load(Network.SERVICE + item.getImgUrl()).into(imageView);
        Glide.with(mContext).load(Network.SERVICE + item.getLogo()).into(imageView_logo);
        helper.getView(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getUrl().length() > 0) {
                    if (item.getUrl().contains("tb.sjdcar.com") || item.getUrl().contains("tc.sjdcar.com")) {
                        Intent intent = new Intent(mContext, NewWebViewActivity.class);
                        intent.putExtra("url", item.getUrl());
                        mContext.startActivity(intent);
                        return;
                    }
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("url", item.getUrl());
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
