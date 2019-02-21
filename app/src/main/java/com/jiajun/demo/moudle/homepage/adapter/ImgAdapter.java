package com.jiajun.demo.moudle.homepage.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiajun.demo.R;
import com.jiajun.demo.moudle.homepage.entities.HomePageBean;
import com.jiajun.demo.moudle.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 文章适配器
 * Created by danjj on 2017/2/17.
 */
public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.MyViewHolder> {

    private Context context;
    private List<HomePageBean.ImgListBean> list;

    public List<HomePageBean.ImgListBean> getList() {
        return list;
    }

    public ImgAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void addData(List<HomePageBean.ImgListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setData(List<HomePageBean.ImgListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_image, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final HomePageBean.ImgListBean item = list.get(position);
        if (item.getImgUrl().startsWith("http")) {
            Glide.with(context).load(item.getImgUrl())
//                    .apply(new RequestOptions().error(R.drawable.blank).placeholder(R.drawable.blank))
                    .into(holder.item_img);
        } else {
            holder.item_img.setImageResource(Integer.parseInt(item.getImgUrl()));
        }
        holder.item_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getUrl() != null && item.getUrl().length() > 0) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("url", item.getUrl());
                    context.startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_img)
        ImageView item_img;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
