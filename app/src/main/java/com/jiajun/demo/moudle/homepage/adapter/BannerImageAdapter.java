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


public class BannerImageAdapter extends BaseQuickAdapter<HomePageBean.ImgListBean, BaseViewHolder> {


    public BannerImageAdapter(List<HomePageBean.ImgListBean> data) {
        super(R.layout.item_image, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, final HomePageBean.ImgListBean item) {
        final HomePageBean.ImgListBean newItem = getData().get(helper.getAdapterPosition() % getData().size());
        ImageView imageView = helper.getView(R.id.item_img);
        if (item.getImgUrl().startsWith("http")) {
            Glide.with(mContext).load(newItem.getImgUrl())
//                    .apply(new RequestOptions().error(R.drawable.banner01).placeholder(R.drawable.banner01))
                    .into(imageView);
        } else {
            imageView.setImageResource(Integer.parseInt(newItem.getImgUrl()));
        }
        helper.getView(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newItem.getUrl().length() == 0) {
                    return;
                }
                if (newItem.getUrl().contains("tb.sjdcar.com")||newItem.getUrl().contains("tc.sjdcar.com")) {
                    Intent intent = new Intent(mContext, NewWebViewActivity.class);
                    intent.putExtra("url", newItem.getUrl());
                    mContext.startActivity(intent);
                    return;
                }
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", newItem.getUrl());
                mContext.startActivity(intent);
            }
        });
    }
}
