package com.jiajun.demo.moudle.vip;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiajun.demo.R;
import com.jiajun.demo.model.entities.WechatItem;

import java.util.List;

/**
 * Created by dan on 2017/8/24/024.
 */

public class WechatMultiAdapter extends BaseMultiItemQuickAdapter<WechatItem.ResultBean.ListBean,BaseViewHolder> {

    private int width,height;
    private boolean isNotLoad ;//是否为省流量模式
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public WechatMultiAdapter(List<WechatItem.ResultBean.ListBean> data) {
        super(data);
        addItemType(WechatItem.ResultBean.ListBean.STYLE_BIG, R.layout.item_wechat_style1);
        addItemType(WechatItem.ResultBean.ListBean.STYLE_SMALL, R.layout.item_wechat_style2);

    }

    @Override
    protected void convert(BaseViewHolder helper, WechatItem.ResultBean.ListBean item) {
        helper.setText(R.id.title_wechat_style1, TextUtils.isEmpty(item.getTitle()) ? mContext.getString(R.string.wechat_select) : item.getTitle());
        switch (helper.getItemViewType()){

            case WechatItem.ResultBean.ListBean.STYLE_BIG:
                if(!isNotLoad){
                    Glide.with(mContext.getApplicationContext())
                            .load(item.getFirstImg())
//                            .override(width,height)
//                            .placeholder(R.drawable.lodingview)
//                            .error(R.drawable.lodingview)
//                            .crossFade(1000)
                            .into((ImageView) helper.getView(R.id.img_wechat_style));
                }
                break;
            case WechatItem.ResultBean.ListBean.STYLE_SMALL:
                if(!isNotLoad){
                    Glide.with(mContext.getApplicationContext())
                            .load(item.getFirstImg())
//                            .override(width/2,width/2)
//                            .placeholder(R.drawable.lodingview)
//                            .error(R.drawable.lodingview)
//                            .crossFade(1000)
                            .into((ImageView) helper.getView(R.id.img_wechat_style));
                }
                break;

        }
    }
}
