package com.jiajun.demo.moudle.me.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiajun.demo.R;
import com.jiajun.demo.moudle.me.entities.UserInfoBean;
import com.jiajun.demo.network.Network;

import java.util.List;


public class BottomAdapter extends BaseQuickAdapter<UserInfoBean.SetManagInfoBean, BaseViewHolder> {


    public BottomAdapter(List<UserInfoBean.SetManagInfoBean> data) {
        super(R.layout.item_me_menu, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, final UserInfoBean.SetManagInfoBean item) {
        helper.setText(R.id.item_tv, item.getName());
        ImageView imageView = helper.getView(R.id.item_iv);
        Glide.with(mContext).load(Network.SERVICE + item.getImgUrl())
//                .apply(new RequestOptions().placeholder(R.drawable.s_icon02).error(R.drawable.s_icon02))
                .into(imageView);
        helper.addOnClickListener(R.id.root);
    }
}
