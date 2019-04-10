package com.mcourse.parallelAnimation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019-4-10 8:56.
 * Describe: TODO
 */

public class ParallelFragment extends Fragment {
    private List<View> parallelViews = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        int layoutId = args.getInt("layoutId");
        ParallelLayoutInflater inflater1 = new ParallelLayoutInflater(inflater, getActivity(), this);
        return inflater1.inflate(layoutId, null);
    }

    public List<View> getParallelViews() {
        return parallelViews;
    }
}
