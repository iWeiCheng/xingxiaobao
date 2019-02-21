package com.jiajun.demo.moudle.homepage.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiajun.demo.R;
import com.jiajun.demo.moudle.homepage.entities.HomePageBean;
import com.jiajun.demo.network.Network;

import java.util.List;


public class GuessAdapter extends BaseQuickAdapter<HomePageBean.HostMenuBean, BaseViewHolder> {



    public GuessAdapter(List<HomePageBean.HostMenuBean> data) {
        super(R.layout.item_guess, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, final HomePageBean.HostMenuBean item) {
//        helper.setText(R.id.item_title, item.getTitle());
//        helper.setText(R.id.item_desc, item.getDescription());
//        ImageView imageView = helper.getView(R.id.item_img_big);
//        ImageView imageView_logo = helper.getView(R.id.item_img_logo);
//        Glide.with(mContext).load(Network.SERVICE+item.getImgUrl()).into(imageView);
//        Glide.with(mContext).load(Network.SERVICE+item.getLogo()).into(imageView_logo);
//        helper.addOnClickListener(R.id.root);
    }
}
