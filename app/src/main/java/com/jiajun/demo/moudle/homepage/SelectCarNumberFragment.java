package com.jiajun.demo.moudle.homepage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jiajun.demo.R;
import com.jiajun.demo.base.BottomDialogFragment;
import com.jiajun.demo.moudle.account.adapter.CompanyAdapter;
import com.jiajun.demo.moudle.account.adapter.OrgAdapter;
import com.jiajun.demo.moudle.account.entities.OrganizationInfoBean;
import com.jiajun.demo.moudle.homepage.adapter.TextAdapter;
import com.jiajun.demo.moudle.homepage.adapter.TextNumberAdapter;
import com.jiajun.demo.moudle.homepage.entities.UpdateDialogEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择车牌号
 * Created by danjj on 2016/12/6.
 */
public class SelectCarNumberFragment extends BottomDialogFragment {

    private ArrayList<String> list_1, list_2, list_3;
    private int position;
    private OnClickSubmitListener listener;
    private TextAdapter adapter;
    private TextNumberAdapter adapter_number;

    public static SelectCarNumberFragment getInstance(int position) {
        SelectCarNumberFragment f = new SelectCarNumberFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        return f;
    }

    @Override
    protected void getExtra(@NonNull Bundle bundle) {
        position = bundle.getInt("position");
    }

    public void setClickItemListener(OnClickSubmitListener onClickSubmitListener) {
        this.listener = onClickSubmitListener;

    }

    private RecyclerView recyclerView;
    private TextView done, delete, cancel;

    @Override
    protected void loadLayout() {
        setContentView(R.layout.fragment_car_number);
    }

    @Override
    protected void initialize(View view, Bundle savedInstanceState) {
        super.initialize(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        done = view.findViewById(R.id.tv_done);
        cancel = view.findViewById(R.id.tv_cancel);
        delete = view.findViewById(R.id.tv_delete);
        recyclerView = view.findViewById(R.id.recycler_view);
        if (position == 0) {
            GridLayoutManager manager = new GridLayoutManager(getContext(), 9);
            recyclerView.setLayoutManager(manager);
        } else {
            GridLayoutManager manager = new GridLayoutManager(getContext(), 10);
            recyclerView.setLayoutManager(manager);
        }
        String[] strs_1 = getResources().getStringArray(R.array.province);
        String[] strs_2 = getResources().getStringArray(R.array.car_2);
        String[] strs_3 = getResources().getStringArray(R.array.car_3);
        list_1 = new ArrayList<>();
        list_2 = new ArrayList<>();
        list_3 = new ArrayList<>();
        for (int i = 0; i < strs_1.length; i++) {
            list_1.add(strs_1[i]);
        }
        for (int i = 0; i < strs_2.length; i++) {
            list_2.add(strs_2[i]);
        }
        for (int i = 0; i < strs_3.length; i++) {
            list_3.add(strs_3[i]);
        }
        adapter = new TextAdapter(list_1);
        adapter_number = new TextNumberAdapter(list_3);
        if (position == 0) {
            delete.setVisibility(View.GONE);
            adapter.setNewData(list_1);
            recyclerView.setAdapter(adapter);
        } else if (position == 1) {
            adapter.setNewData(list_2);
            recyclerView.setAdapter(adapter);
        } else {
            adapter_number.setNewData(list_3);
            recyclerView.setAdapter(adapter_number);
        }
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int i) {
                if (listener != null) {
                    listener.onClickItem(position, adapter.getData().get(i).toString());
                }
            }
        });
        adapter_number.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int i) {
                if (listener != null) {
                    listener.onClickItem(position, adapter_number.getData().get(i));
                }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (listener != null) {
                    listener.onClickCancel();
                }
//                if (position == 0) {
//                    return;
//                } else {
//                    position--;
//                    if (position == 0) {
//                        adapter.setNewData(list_1);
//                    } else if (position == 1) {
//                        adapter.setNewData(list_2);
//                    } else {
//                        adapter.setNewData(list_3);
//                    }
//                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClickDelete();
                }
//                if (position == 0) {
//                    return;
//                } else {
//                    position--;
//                    if (position == 0) {
//                        adapter.setNewData(list_1);
//                    } else if (position == 1) {
//                        adapter.setNewData(list_2);
//                    } else {
//                        adapter.setNewData(list_3);
//                    }
//                }

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
