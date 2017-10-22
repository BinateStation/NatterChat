package rkr.binatestation.natterchat.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.adapters.ListAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends BaseFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(getAdapter());

    }

    public LinearLayoutManager getLinearLayoutManager() {
        return mLinearLayoutManager;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public ListAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ListAdapter(this);
        }
        return mAdapter;
    }


    public void scrollToEnd() {
        getLinearLayoutManager().scrollToPosition(getAdapter().getItemCount() - 1);
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
}
