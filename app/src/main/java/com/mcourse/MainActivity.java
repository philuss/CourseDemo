package com.mcourse;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.mcourse.activity.BezierActivity;
import com.mcourse.activity.CanvasActivity;
import com.mcourse.activity.PaintActivity;
import com.mcourse.activity.ParallelActivity;
import com.mcourse.adapter.CategoryAdapter;
import com.mcourse.bean.advanceui.CategoryBean;
import com.mcourse.utils.PowerUtil;
import com.mlib.rv.RVHelper;
import com.mlib.rv.base.IBaseBean;
import com.mlib.rv.base.RVBaseAdapter;
import com.mlib.rv.base.RVBaseCreate;
import com.mlib.rv.listener.OnItemListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private List<String> itemList;
    private CategoryAdapter adapter;
    private List<IBaseBean> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private RVHelper rvHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        PowerUtil.acquireWakeLock(this);
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initData() {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        itemList = Arrays.asList(getResources().getStringArray(R.array.demo_item));
        for (int i = 0; i < itemList.size(); i++) {
            CategoryBean bean = new CategoryBean();
            bean.setItemStyle(CategoryAdapter.STYLE);
            bean.setTitle(itemList.get(i));
            dataList.add(bean);
        }
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        RVBaseCreate rvBaseCreate = new RVBaseCreate() {
            @Override
            public SwipeRefreshLayout createSwipeRefresh() {
                return swipeRefreshLayout;
            }

            @Override
            public RecyclerView createRecyclerView() {
                return recyclerView;
            }

            @Override
            public RVBaseAdapter createRecycleViewAdapter() {
                adapter = new CategoryAdapter(dataList);
                adapter.setOnItemListener(new OnItemListener<CategoryBean>() {
                    @Override
                    public void onItemClick(View view, CategoryBean entity, int position) {
                        String title = entity.getTitle();
                        if (TextUtils.isEmpty(title)) return;
                        Intent intent = new Intent();
                        if (title.equals(itemList.get(0))) {
                            intent.setClass(MainActivity.this, PaintActivity.class);
                        } else if (title.equals(itemList.get(1))) {
                            intent.setClass(MainActivity.this, CanvasActivity.class);
                        } else if (title.equals(itemList.get(2))) {
                            intent.setClass(MainActivity.this, BezierActivity.class);
                        } else if (title.equals(itemList.get(3))) {
                            intent.setClass(MainActivity.this, ParallelActivity.class);
                        }
                        if (intent.getComponent() != null) {
                            startActivity(intent);
                        }
                    }

                    @Override
                    public boolean onItemLongClick(View view, CategoryBean entity, int position) {
                        return false;
                    }
                });
                return adapter;
            }

            @Override
            public RecyclerView.LayoutManager createLayoutManager() {
                return new LinearLayoutManager(MainActivity.this);
            }

            @Override
            public RecyclerView.ItemDecoration createItemDecoration() {
                return new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL);
            }
        };
        rvHelper = new RVHelper.Builder(rvBaseCreate, rvBaseCreate).build();
        rvHelper.notifyAdapterDataSetChanged(dataList);
    }

    @Override
    protected void onDestroy() {
//        PowerUtil.releaseWakeLock();
        super.onDestroy();
    }
}
