package rkr.binatestation.natterchat.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rkr.binatestation.natterchat.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SwipeListFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public SwipeListFragment() {
        // Required empty public constructor
    }

    public static SwipeListFragment newInstance() {
        Bundle args = new Bundle();
        SwipeListFragment fragment = new SwipeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void showProgress() {
        if (mSwipeRefreshLayout != null) {
            showProgress(mSwipeRefreshLayout);
        }
    }

    public void hideProgress() {
        if (mSwipeRefreshLayout != null) {
            hideProgress(mSwipeRefreshLayout);
        }
    }

    @Override
    public void onRefresh() {
        hideProgress();
    }
}
