package com.jiajun.demo.moudle.homepage;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiajun.demo.R;
import com.jiajun.demo.model.entities.NewsItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class DefaultStyleItemAdapter extends BaseQuickAdapter<NewsItem.ResultBean.DataBean, BaseViewHolder> {
    boolean isNotLoad;
    public int mImgWidth;
    public int mImgHeight;

    public DefaultStyleItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    public DefaultStyleItemAdapter(int layoutResId, List<NewsItem.ResultBean.DataBean> data) {
        super(layoutResId, data);
    }

    public DefaultStyleItemAdapter(int layoutResId, boolean isNotLoadImg, int imgWidth, int imgHeight){
        super(layoutResId);
        isNotLoad = isNotLoadImg;
        mImgWidth = imgWidth;
        mImgHeight = imgHeight;
    }
    @Override
    protected void convert(BaseViewHolder helper, NewsItem.ResultBean.DataBean item) {
        helper.setText(R.id.title_news_item, item.getTitle())
                .setText(R.id.from_news_item, item.getAuthor_name())
                .setText(R.id.time_news_item, onFormatTime(item.getDate()));
        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .error(R.drawable.errorview)
                .placeholder(R.drawable.lodingview)
                .override(100, 100);
        if (!isNotLoad) {
            Glide.with(mContext)
                    .load(item.getThumbnail_pic_s())
                    .apply(myOptions)
                    .transition(new DrawableTransitionOptions().crossFade(1000))
                    .into((ImageView) helper.getView(R.id.img_news_item));
        }
    }

    private String onFormatTime(String date) {
        Date formatDate = null;
        if (TextUtils.isEmpty(date)) {
            return getNowTime("MM-dd");
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                formatDate = simpleDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        if (formatDate == null) {
            return getNowTime("MM-dd");
        }
        return simpleDateFormat.format(formatDate);
    }

    private String getNowTime(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return simpleDateFormat.format(date);
    }
}
