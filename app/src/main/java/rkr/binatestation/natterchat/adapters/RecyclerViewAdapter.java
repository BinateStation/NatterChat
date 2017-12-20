package rkr.binatestation.natterchat.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import rkr.binatestation.natterchat.adapters.holders.ChatContactViewHolder;
import rkr.binatestation.natterchat.adapters.holders.ChatMessageViewHolder;
import rkr.binatestation.natterchat.adapters.holders.EmptyStateViewHolder;
import rkr.binatestation.natterchat.adapters.holders.UserViewHolder;
import rkr.binatestation.natterchat.listeners.OnListItemClickListener;
import rkr.binatestation.natterchat.listeners.ViewBinder;
import rkr.binatestation.natterchat.models.ChatContactModel;
import rkr.binatestation.natterchat.models.ChatMessageModel;
import rkr.binatestation.natterchat.models.EmptyStateModel;
import rkr.binatestation.natterchat.models.UserModel;


/**
 * Created by RKR on 7/13/2017.
 * RecyclerViewAdapter
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<?> data = new ArrayList<>();
    /**
     * the listener object to have list item click
     */
    private OnListItemClickListener mClickListener;

    public RecyclerViewAdapter() {
        data = EmptyStateModel.getEmptyDataModels();
    }

    public OnListItemClickListener getClickListener() {
        return mClickListener;
    }

    public void setClickListener(OnListItemClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public ArrayList<?> getData() {
        return data;
    }

    /**
     * sets the data to the adapter class
     *
     * @param data ArrayList of any Model class
     */
    public void setData(ArrayList<?> data) {
        if (data.size() > 0) {
            this.data = data;
        } else {
            this.data = EmptyStateModel.getEmptyDataModels();
        }
        notifyDataSetChanged();
    }

    /**
     * removes item from specified position
     *
     * @param position position of the item to remove
     */
    public void removeItem(int position) {
        if (position < data.size() && position >= 0) {
            this.data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        Object object = getItem(position);
        if (object instanceof ChatContactModel) {
            return ChatContactViewHolder.LAYOUT_ID;
        }
        if (object instanceof ChatMessageModel) {
            return ChatMessageViewHolder.LAYOUT_ID_RIGHT;
        }
        if (object instanceof UserModel) {
            return UserViewHolder.LAYOUT_ID;
        }
        if (object instanceof EmptyStateModel) {
            return EmptyStateViewHolder.LAYOUT_ID;
        }
        return EmptyStateViewHolder.LAYOUT_ID;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder() called with: parent = [" + parent + "], viewType = [" + viewType + "]");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        switch (viewType) {
            case ChatContactViewHolder.LAYOUT_ID:
                return new ChatContactViewHolder(itemView, this);
            case ChatMessageViewHolder.LAYOUT_ID_LEFT:
            case ChatMessageViewHolder.LAYOUT_ID_RIGHT:
                return new ChatMessageViewHolder(itemView, this);
            case UserViewHolder.LAYOUT_ID:
                return new UserViewHolder(itemView, this);
            default:
                return new EmptyStateViewHolder(itemView, this);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() called with: holder = [" + holder + "], position = [" + position + "]");
        if (holder instanceof ViewBinder) {
            ViewBinder viewBinder = (ViewBinder) holder;
            viewBinder.bindView(getItem(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}

