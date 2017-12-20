package rkr.binatestation.natterchat.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.adapters.RecyclerViewAdapter;
import rkr.binatestation.natterchat.listeners.OnListItemClickListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends BaseFragment implements OnListItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

    public RecyclerViewAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new RecyclerViewAdapter();
            mAdapter.setClickListener(this);
        }
        return mAdapter;
    }


    public void scrollToEnd() {
        getLinearLayoutManager().scrollToPosition(getAdapter().getItemCount() - 1);
    }

    @Override
    public void onClickItem(Object object, int position, View actionView) {

    }
}
