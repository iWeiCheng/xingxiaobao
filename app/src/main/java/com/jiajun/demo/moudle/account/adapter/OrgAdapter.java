package com.jiajun.demo.moudle.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jiajun.demo.R;
import com.jiajun.demo.moudle.account.entities.OrgInfo;

import java.util.List;

/**
 * Created by danjj on 2016/12/6.
 */
public class OrgAdapter extends BaseAdapter {
    private List<OrgInfo.OrgInfoBean> lists;
    private Context context;

    public OrgAdapter(Context context, List<OrgInfo.OrgInfoBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_text,parent,false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(lists.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        TextView  textView;
    }
}
