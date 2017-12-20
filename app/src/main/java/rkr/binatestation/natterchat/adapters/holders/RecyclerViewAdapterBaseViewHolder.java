package rkr.binatestation.natterchat.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import rkr.binatestation.natterchat.adapters.RecyclerViewAdapter;
import rkr.binatestation.natterchat.listeners.ViewBinder;


/**
 * Created by RKR on 08-12-2017.
 * RecyclerViewAdapterBaseViewHolder
 */

public abstract class RecyclerViewAdapterBaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ViewBinder {
    private final RecyclerViewAdapter adapter;

    RecyclerViewAdapterBaseViewHolder(View itemView, RecyclerViewAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION && adapter != null && adapter.getClickListener() != null) {
            adapter.getClickListener().onClickItem(adapter.getItem(position), position, view);
        }
    }

    @Override
    public abstract void bindView(Object object);
}
