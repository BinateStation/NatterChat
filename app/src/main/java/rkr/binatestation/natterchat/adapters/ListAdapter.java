package rkr.binatestation.natterchat.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import rkr.binatestation.natterchat.adapters.holders.ChatContactViewHolder;
import rkr.binatestation.natterchat.adapters.holders.ChatMessageViewHolder;
import rkr.binatestation.natterchat.adapters.holders.EmptyStateViewHolder;
import rkr.binatestation.natterchat.adapters.holders.UserViewHolder;
import rkr.binatestation.natterchat.listeners.OnListItemClickListener;
import rkr.binatestation.natterchat.listeners.OnListItemRemoveListener;
import rkr.binatestation.natterchat.models.ChatContactModel;
import rkr.binatestation.natterchat.models.ChatMessageModel;
import rkr.binatestation.natterchat.models.EmptyStateModel;
import rkr.binatestation.natterchat.models.UserModel;

/**
 * Created by rosemary on 9/18/2017.
 * ListAdapter
 * <p>
 * RecyclerVIew List adapter which changes the view type using Data Object models.
 * </p>
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * Data sets to have data models
     */
    private ArrayList<Object> data = new ArrayList<>();

    /**
     * the listener object to have list item click
     */
    private OnListItemClickListener mClickListener;

    private OnListItemRemoveListener mItemRemoveListener;

    /**
     * Context from where this adapter created
     */
    private Context mContext;

    /**
     * item width
     */
    private int mItemWidth;

    public ListAdapter(Fragment fragment) {
        if (fragment instanceof OnListItemClickListener) {
            mClickListener = (OnListItemClickListener) fragment;
        }
        if (fragment instanceof OnListItemRemoveListener) {
            mItemRemoveListener = (OnListItemRemoveListener) fragment;
        }
        mContext = fragment.getContext();
    }

    public ListAdapter(Context context) {
        mContext = context;
        if (context instanceof OnListItemClickListener) {
            mClickListener = (OnListItemClickListener) context;
        }
    }

    public void setItemWidth(int mItemWidth) {
        this.mItemWidth = mItemWidth;
    }

    public OnListItemClickListener getClickListener() {
        return mClickListener;
    }

    public void setClickListener(OnListItemClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public OnListItemRemoveListener getItemRemoveListener() {
        return mItemRemoveListener;
    }

    public void setItemRemoveListener(OnListItemRemoveListener mItemRemoveListener) {
        this.mItemRemoveListener = mItemRemoveListener;
    }

    public void add(Object object) {
        if (this.data.remove(EmptyStateModel.getUnKnownEmptyModel())) {
            notifyDataSetChanged();
        }
        this.data.add(object);
        notifyItemInserted(data.size() - 1);
    }

    public void replace(Object object, long requestId) {
        if (this.data.remove(EmptyStateModel.getUnKnownEmptyModel())) {
            notifyDataSetChanged();
        }
//        if (object instanceof ChatMessageModel) {
//            ChatMessageModel chatMessageModel = (ChatMessageModel) object;
//            ChatMessageModel messageModel = ChatMessageModel.getDummyObject(chatMessageModel.getChatGroupId());
//            messageModel.setId(requestId);
//            int index = data.indexOf(messageModel);
//            if (index >= 0 && index < data.size()) {
//                Object obj = data.get(index);
//                if (obj instanceof ChatMessageModel) {
//                    ChatMessageModel model = (ChatMessageModel) obj;
//                    model.setId(chatMessageModel.getId());
//                    model.setStatus(Status.SEND.getValue());
//                    notifyItemChanged(index);
//                }
//            }
//        }
    }

    public void add(Object object, int position) {
        if (this.data.remove(EmptyStateModel.getUnKnownEmptyModel())) {
            notifyDataSetChanged();
        }
        if (position < data.size()) {
            this.data.add(position, object);
            notifyItemInserted(position);
        }
    }

    public void remove(int position) {
        if (position < data.size()) {
            this.data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addEmptyState(EmptyStateModel emptyStateModel) {
        this.data.clear();
        this.data.add(emptyStateModel);
        notifyDataSetChanged();
    }

    /**
     * get the view type layout id
     *
     * @param position the item position
     * @return the layout id according to the data object instance
     */
    @Override
    public int getItemViewType(int position) {
        Object object = getItem(position);
        if (object instanceof EmptyStateModel) {
            // Layout ID for Empty state view
            return EmptyStateViewHolder.LAYOUT_ID;
        } else if (object instanceof ChatContactModel) {
            // Layout id for chat group holder
            return ChatContactViewHolder.LAYOUT_ID;
        } else if (object instanceof UserModel) {
            // Layout id for chat group holder
            return UserViewHolder.LAYOUT_ID;
        } else if (object instanceof ChatMessageModel) {
            // Layout id for chat message holder
            ChatMessageModel chatMessageModel = (ChatMessageModel) object;
            String userId = chatMessageModel.getUserModel().getId();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (!TextUtils.isEmpty(userId) && user != null && userId.equals(user.getUid())) {
                return ChatMessageViewHolder.LAYOUT_ID_RIGHT;
            }
            return ChatMessageViewHolder.LAYOUT_ID_LEFT;
        }
        return super.getItemViewType(position);
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public ArrayList<Object> getData() {
        return data;
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == UserViewHolder.LAYOUT_ID) {
            return new UserViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false),
                    this
            );
        } else if (viewType == ChatContactViewHolder.LAYOUT_ID) {
            return new ChatContactViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false),
                    this
            );
        } else if (viewType == ChatMessageViewHolder.LAYOUT_ID_RIGHT || viewType == ChatMessageViewHolder.LAYOUT_ID_LEFT) {
            return new ChatMessageViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false),
                    this
            );
        }
        return new EmptyStateViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(EmptyStateViewHolder.LAYOUT_ID, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyStateViewHolder) {
            EmptyStateViewHolder emptyStateViewHolder = (EmptyStateViewHolder) holder;
            emptyStateViewHolder.bindView(getItem(position));
        }
        if (holder instanceof ChatContactViewHolder) {
            ChatContactViewHolder chatContactViewHolder = (ChatContactViewHolder) holder;
            chatContactViewHolder.bindView(getItem(position));
        }
        if (holder instanceof ChatMessageViewHolder) {
            ChatMessageViewHolder chatMessageViewHolder = (ChatMessageViewHolder) holder;
            chatMessageViewHolder.bindView(getItem(position));
        }
        if (holder instanceof UserViewHolder) {
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.bindView(getItem(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
