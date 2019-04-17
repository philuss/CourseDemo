package com.philus.materialdesign.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.philus.materialdesign.R;

/**
 * Created by philus on 2019/4/17 11:41 .
 */

public class ToolbarActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar, mToolbar1, mToolbar2, mToolbar3, mToolbar4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        findViewById(R.id.btn_toolbar).setOnClickListener(this);
        findViewById(R.id.btn_toolbar1).setOnClickListener(this);
        findViewById(R.id.btn_toolbar2).setOnClickListener(this);
        findViewById(R.id.btn_toolbar3).setOnClickListener(this);
        findViewById(R.id.btn_toolbar4).setOnClickListener(this);
        initView();
        initToolbar1();
        initToolbar2();
        initToolbar3();
        initToolbar4();

//        setSupportActionBar(mToolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar!=null){
//            //设置Toolbar home键可点击
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            //设置Toolbar home键图标
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_book_list);
//        }

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar = toolbar;
//        //设置NavigationIcon
//        toolbar.setNavigationIcon(R.drawable.ic_book_list);
//        // 设置navigation button 点击事件
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        // 设置 toolbar 背景色
//        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        // 设置 Title
//        toolbar.setTitle(R.string.toolbar_title);
//        //  设置Toolbar title文字颜色
//        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        // 设置Toolbar subTitle
//        toolbar.setSubtitle(R.string.sub_title);
//
//        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
//        // 设置logo
//        toolbar.setLogo(R.mipmap.ic_launcher);
//        // 设置 NavigationIcon 点击事件
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        //设置 Toolbar menu
//        toolbar.inflateMenu(R.menu.setting_menu);
//        // 设置溢出菜单的图标
//        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha));
//        // 设置menu item 点击事件
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.item_setting:
//                        //点击设置
//                        break;
//                }
//                return false;
//            }
//        });

    }

    private void initToolbar1() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_1);
        mToolbar1 = toolbar;
        //设置NavigationIcon
        toolbar.setNavigationIcon(R.drawable.ic_book_list);
        // 设置navigation button 点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 设置 toolbar 背景色
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        // 设置 Title
        toolbar.setTitle(R.string.toolbar_title);
        //  设置Toolbar title文字颜色
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        // 设置Toolbar subTitle
        toolbar.setSubtitle(R.string.sub_title);

        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        // 设置logo
        toolbar.setLogo(R.mipmap.ic_launcher);
        // 设置 NavigationIcon 点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置 Toolbar menu
        toolbar.inflateMenu(R.menu.setting_menu);
        // 设置溢出菜单的图标
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha));
        // 设置menu item 点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_setting:
                        //点击设置
                        break;
                }
                return false;
            }
        });

    }

    private void initToolbar2() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_2);
        mToolbar2 = toolbar;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //添加溢出菜单
        toolbar.inflateMenu(R.menu.setting_menu);
        // 添加菜单点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_setting:
                        //点击设置菜单
                        break;
                }
                return false;
            }
        });

    }

    private void initToolbar3() {
        mToolbar3 = (Toolbar) findViewById(R.id.tool_bar_3);
        mToolbar3.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar3.inflateMenu(R.menu.tool_bar_menu2);
    }

    private void initToolbar4() {
        mToolbar4 = (Toolbar) findViewById(R.id.tool_bar_4);
        mToolbar4.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar4.inflateMenu(R.menu.menu_search);

        mToolbar4.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_search) {
                    // do search
                }
                return false;
            }
        });
        // 获取ToolBar 上的编辑框
        EditText searchEdit = (EditText) mToolbar4.findViewById(R.id.edit_search);
        // 获取内容
        String content = searchEdit.getText().toString();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_toolbar) {
            mToolbar.setVisibility(View.VISIBLE);
            mToolbar1.setVisibility(View.GONE);
            mToolbar2.setVisibility(View.GONE);
            mToolbar3.setVisibility(View.GONE);
            mToolbar4.setVisibility(View.GONE);
        } else if (v.getId() == R.id.btn_toolbar1) {
            mToolbar.setVisibility(View.GONE);
            mToolbar1.setVisibility(View.VISIBLE);
            mToolbar2.setVisibility(View.GONE);
            mToolbar3.setVisibility(View.GONE);
            mToolbar4.setVisibility(View.GONE);
        } else if (v.getId() == R.id.btn_toolbar2) {
            mToolbar1.setVisibility(View.GONE);
            mToolbar.setVisibility(View.GONE);
            mToolbar2.setVisibility(View.VISIBLE);
            mToolbar3.setVisibility(View.GONE);
            mToolbar4.setVisibility(View.GONE);
        } else if (v.getId() == R.id.btn_toolbar3) {
            mToolbar.setVisibility(View.GONE);
            mToolbar1.setVisibility(View.GONE);
            mToolbar2.setVisibility(View.GONE);
            mToolbar3.setVisibility(View.VISIBLE);
            mToolbar4.setVisibility(View.GONE);
        } else if (v.getId() == R.id.btn_toolbar4) {
            mToolbar.setVisibility(View.GONE);
            mToolbar1.setVisibility(View.GONE);
            mToolbar2.setVisibility(View.GONE);
            mToolbar3.setVisibility(View.GONE);
            mToolbar4.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tool_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
            case R.id.add:
                Toast.makeText(this,"add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this,"setting",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
