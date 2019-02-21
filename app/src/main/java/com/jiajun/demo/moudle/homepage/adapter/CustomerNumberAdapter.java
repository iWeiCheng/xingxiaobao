package com.jiajun.demo.moudle.homepage.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jiajun.demo.R;

import java.util.List;


public class CustomerNumberAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public CustomerNumberAdapter(List<String> data) {
        super(R.layout.item_customer_number, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, final String item) {
        helper.setText(R.id.text, item);
        helper.addOnClickListener(R.id.text);
        if (item.equals("I") || item.equals("O")) {
            helper.getView(R.id.text).setBackgroundResource(R.drawable.shape_gray_solid);
            helper.getView(R.id.text).setClickable(false);
        } else {
            helper.getView(R.id.text).setClickable(true);
        }
    }
}
