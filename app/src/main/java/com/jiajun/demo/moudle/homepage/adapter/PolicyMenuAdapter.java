package com.jiajun.demo.moudle.homepage.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiajun.demo.R;
import com.jiajun.demo.moudle.homepage.entities.HomePageBean;
import com.jiajun.demo.network.Network;

import java.util.List;


public class PolicyMenuAdapter extends BaseQuickAdapter<HomePageBean.PolicyMenuBean, BaseViewHolder> {


    public PolicyMenuAdapter(List<HomePageBean.PolicyMenuBean> data) {
        super(R.layout.item_policy_menu, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, final HomePageBean.PolicyMenuBean item) {
        helper.setText(R.id.item_text, item.getName());
        ImageView imageView = helper.getView(R.id.item_img);
        Glide.with(mContext).load(Network.SERVICE + item.getImgUrl())
//                .apply(new RequestOptions().placeholder(R.drawable.s_icon02).error(R.drawable.s_icon02))
                .into(imageView);
        helper.addOnClickListener(R.id.root);
        helper.setVisible(R.id.item_red_point, item.getUnread() != null && item.getUnread().equals("1"));
    }
}
