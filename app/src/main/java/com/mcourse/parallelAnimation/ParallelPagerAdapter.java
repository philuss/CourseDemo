package com.mcourse.parallelAnimation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

public class ParallelPagerAdapter extends FragmentPagerAdapter {
    private final String TAG = ParallelPagerAdapter.class.getSimpleName();
    private List<ParallelFragment> fragments;

    public ParallelPagerAdapter(FragmentManager fm, List<ParallelFragment> fragments) {
        super(fm);
        this.fragments = fragments;
        Log.e(TAG, "ParallelPagerAdapter: " + (fragments == null ? 0 : fragments.size()));
    }

    @Override
    public Fragment getItem(int position) {
        Log.e(TAG, "getItem: " + fragments.get(position).toString());
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
