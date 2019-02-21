package com.jiajun.demo.moudle.homepage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiajun.demo.R;
import com.jiajun.demo.base.BottomDialogFragment;
import com.jiajun.demo.moudle.homepage.adapter.CustomerNumberAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * 选择车牌号
 * Created by danjj on 2016/12/6.
 */
public class CustomerNumberFragment extends BottomDialogFragment {

    private List<String> list_1, list_2, list_3, list_4;
    private int position;
    private OnClickSubmitListener listener;
    private CustomerNumberAdapter adapter_number1, adapter_number2, adapter_number3, adapter_number4;

    public static CustomerNumberFragment getInstance() {
        CustomerNumberFragment f = new CustomerNumberFragment();
//        Bundle args = new Bundle();
//        args.putInt("position", position);
//        f.setArguments(args);
        return f;
    }

    @Override
    protected void getExtra(@NonNull Bundle bundle) {
        position = bundle.getInt("position");
    }

    public void setClickItemListener(OnClickSubmitListener onClickSubmitListener) {
        this.listener = onClickSubmitListener;

    }

    private RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    private TextView done, cancel;
    private RelativeLayout delete;

    @Override
    protected void loadLayout() {
        setContentView(R.layout.fragment_customer_number);
    }

    @Override
    protected void initialize(View view, Bundle savedInstanceState) {
        super.initialize(view, savedInstanceState);
        recyclerView1 = view.findViewById(R.id.recycler_view1);
        cancel = view.findViewById(R.id.tv_cancel);
        done = view.findViewById(R.id.tv_done);
        delete = view.findViewById(R.id.delete);
        recyclerView2 = view.findViewById(R.id.recycler_view2);
        recyclerView3 = view.findViewById(R.id.recycler_view3);
        recyclerView4 = view.findViewById(R.id.recycler_view4);
        GridLayoutManager manager1 = new GridLayoutManager(getContext(), 10);
        recyclerView1.setLayoutManager(manager1);
        GridLayoutManager manager2 = new GridLayoutManager(getContext(), 10);
        recyclerView2.setLayoutManager(manager2);
        GridLayoutManager manager3 = new GridLayoutManager(getContext(), 9);
        recyclerView3.setLayoutManager(manager3);
        GridLayoutManager manager4 = new GridLayoutManager(getContext(), 7);
        recyclerView4.setLayoutManager(manager4);
        String[] strs_1 = getResources().getStringArray(R.array.number_1);
        String[] strs_2 = getResources().getStringArray(R.array.number_2);
        String[] strs_3 = getResources().getStringArray(R.array.number_3);
        String[] strs_4 = getResources().getStringArray(R.array.number_4);
        list_1 = Arrays.asList(strs_1);
        list_2 = Arrays.asList(strs_2);
        list_3 = Arrays.asList(strs_3);
        list_4 = Arrays.asList(strs_4);
        adapter_number1 = new CustomerNumberAdapter(list_1);
        adapter_number2 = new CustomerNumberAdapter(list_2);
        adapter_number3 = new CustomerNumberAdapter(list_3);
        adapter_number4 = new CustomerNumberAdapter(list_4);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_divider));
        recyclerView1.addItemDecoration(divider);
        recyclerView2.addItemDecoration(divider);
        recyclerView3.addItemDecoration(divider);
        recyclerView4.addItemDecoration(divider);
        adapter_number1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int i) {
                if (listener != null) {
                    listener.onClickItem(position, adapter.getData().get(i).toString());
                }
            }
        });
        adapter_number2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int i) {
                if (listener != null) {
                    listener.onClickItem(position, adapter.getData().get(i).toString());
                }
            }
        });
        adapter_number3.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int i) {
                if (listener != null) {
                    listener.onClickItem(position, adapter.getData().get(i).toString());
                }
            }
        });
        adapter_number4.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int i) {
                if (listener != null) {
                    listener.onClickItem(position, adapter.getData().get(i).toString());
                }
            }
        });
        recyclerView1.setAdapter(adapter_number1);
        recyclerView2.setAdapter(adapter_number2);
        recyclerView3.setAdapter(adapter_number3);
        recyclerView4.setAdapter(adapter_number4);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (listener != null) {
                    listener.onClickCancel();
                }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (listener != null) {
                    listener.onClickCancel();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClickDelete();
                }
            }
        });
    }


    public interface OnClickSubmitListener {
        /**
         * 提供方法供调用者调用
         *
         * @param index
         * @param text
         */
        void onClickItem(int index, String text);

        void onClickCancel();

        void onClickDelete();
    }
}
