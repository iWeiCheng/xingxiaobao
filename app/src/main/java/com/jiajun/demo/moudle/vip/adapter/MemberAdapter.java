package com.jiajun.demo.moudle.vip.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiajun.demo.R;
import com.jiajun.demo.moudle.vip.entities.PrivilegeBean;
import com.jiajun.demo.moudle.webview.WebViewActivity;
import com.jiajun.demo.network.Network;

import java.util.ArrayList;
import java.util.List;


public class MemberAdapter extends BaseQuickAdapter<PrivilegeBean.GradeListBean.PrivilegeListBean, BaseViewHolder> {


    private RequestOptions myOptions;

    public MemberAdapter(List<PrivilegeBean.GradeListBean.PrivilegeListBean> data) {
        super(R.layout.item_policy_menu, data);
        myOptions = new RequestOptions()
                .fitCenter()
                .error(R.drawable.ft_icon02)
                .placeholder(R.drawable.ft_icon02);
    }

    public MemberAdapter() {
        super(R.layout.item_policy_menu, new ArrayList<PrivilegeBean.GradeListBean.PrivilegeListBean>());
        myOptions = new RequestOptions()
                .fitCenter()
                .error(R.drawable.ft_icon02)
                .placeholder(R.drawable.ft_icon02);
    }


    @Override
    protected void convert(BaseViewHolder helper, final PrivilegeBean.GradeListBean.PrivilegeListBean item) {
        helper.getView(R.id.root).setBackgroundColor(mContext.getResources().getColor(R.color.white));
        helper.setText(R.id.item_text, item.getPri_name());
        ImageView imageView = helper.getView(R.id.item_img);
        Glide.with(mContext)
                .asBitmap()
                .load(Network.SERVICE + item.getIco())
//                .apply(new RequestOptions()
//                        .fitCenter()
//                )
//                        .error(R.drawable.ft_icon02)
//                        .placeholder(R.drawable.ft_icon02))
                .into(imageView);
        View root = helper.getView(R.id.root);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", item.getOpen_grade_url());
                mContext.startActivity(intent);
            }
        });
        helper.setVisible(R.id.item_shadow, item.getBuy_status().equals("0"));//0:未购买 1：已购买
    }
}
