package com.mcourse.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.mcourse.R;
import com.mcourse.adapter.PaintAdapter;
import com.mcourse.bean.advanceui.PaintBean;
import com.mcourse.widget.paint.PaintCapView;
import com.mcourse.widget.paint.PaintDrawArc;
import com.mcourse.widget.paint.PaintDrawRect;
import com.mcourse.widget.paint.PaintDrawText;
import com.mcourse.widget.paint.PaintJoinView;
import com.mcourse.widget.paint.PaintPathEffectView;
import com.mcourse.widget.paint.PaintShaderView;
import com.mcourse.widget.paint.PaintStyleView;
import com.mcourse.widget.paint.ParticleView;
import com.mlib.rv.RVHelper;
import com.mlib.rv.base.IBaseBean;
import com.mlib.rv.base.RVBaseAdapter;
import com.mlib.rv.base.RVBaseCreate;
import com.mlib.rv.listener.OnItemListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2019-4-1 15:35.
 * Describe: TODO
 */

public class PaintActivity extends Activity {
    private final String TAG = PaintActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<IBaseBean> dataList = new ArrayList<>();
    private List<String> itemList = new ArrayList<>();
    private PaintAdapter adapter;
    private RVHelper rvHelper;
    private FrameLayout rf_content;
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_paint);
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        rf_content = findViewById(R.id.rf_content);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initRV();
    }

    private void initRV() {

        RVBaseCreate rvCreate = new RVBaseCreate() {
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
                adapter = new PaintAdapter(dataList);
                adapter.setOnItemListener(new OnItemListener<PaintBean>() {
                    @Override
                    public void onItemClick(View view, PaintBean entity, int position) {
                        onAdapterItemClick(view, entity, position);
                    }

                    @Override
                    public boolean onItemLongClick(View view, PaintBean entity, int position) {
                        return false;
                    }
                });
                return adapter;
            }

            @Override
            public RecyclerView.LayoutManager createLayoutManager() {
                return new LinearLayoutManager(PaintActivity.this);
            }

            @Override
            public RecyclerView.ItemDecoration createItemDecoration() {
                return new DividerItemDecoration(PaintActivity.this, DividerItemDecoration.VERTICAL);
            }

            @Override
            public void onRefresh() {
                super.onRefresh();
            }
        };

        rvHelper = new RVHelper.Builder(rvCreate, rvCreate).build();
        initData();
    }

    private void initData() {
        itemList = Arrays.asList(getResources().getStringArray(R.array.paint_item));
        for (String s : itemList) {
            PaintBean bean = new PaintBean();
            bean.setItemStyle(PaintAdapter.STYLE_A);
            bean.setTitle(s);
            bean.setTips("Paint");
            dataList.add(bean);
        }
        rvHelper.notifyAdapterDataSetChanged(dataList);
    }

    private void onAdapterItemClick(View view, PaintBean entity, int position) {
        if (position >= itemList.size()) return;
        String title = entity.getTitle();
        if (TextUtils.isEmpty(title)) return;
        Log.e(TAG, "onItemClick: " + entity.toString() + "; position = " + position);
        View childView = null;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        if (title.equals(itemList.get(0))) {
            childView = new PaintDrawRect(PaintActivity.this);
        } else if (title.equals(itemList.get(1))) {
            childView = new PaintStyleView(PaintActivity.this);
        } else if (title.equals(itemList.get(2))) {
            childView = new PaintDrawText(PaintActivity.this);
        } else if (title.equals(itemList.get(3))) {
            childView = new PaintDrawArc(PaintActivity.this);
        } else if (title.equals(itemList.get(4))) {
            childView = new PaintCapView(PaintActivity.this);
        } else if (title.equals(itemList.get(5))) {
            childView = new PaintJoinView(PaintActivity.this);
        } else if (title.equals(itemList.get(6))) {
            childView = new PaintPathEffectView(PaintActivity.this);
        } else if (title.equals(itemList.get(7))) {
            childView = new PaintPathEffectView(PaintActivity.this);
        } else if (title.equals(itemList.get(8))) {
            childView = new PaintShaderView(PaintActivity.this);
        } else if (title.equals(itemList.get(9))) {
            childView = new ParticleView(PaintActivity.this);
//            childView = new SpiltView(PaintActivity.this);
        }

        if (null != childView) {
            rf_content.removeAllViews();
            childView.setLayoutParams(params);
            rf_content.addView(childView);
        }

    }

}
