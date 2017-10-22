package rkr.binatestation.natterchat.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.adapters.ListAdapter;
import rkr.binatestation.natterchat.models.UserModel;
import rkr.binatestation.natterchat.utils.Utils;


/**
 * Created by RKR on 25-09-2017.
 * UserViewHolder
 */

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static final int LAYOUT_ID = R.layout.fragment_user;
    private final ListAdapter mListAdapter;
    private ImageView userImageView;
    private TextView userNameTextView;
    private TextView messageTextView;

    public UserViewHolder(View itemView, ListAdapter listAdapter) {
        super(itemView);
        userImageView = itemView.findViewById(R.id.image_view);
        userNameTextView = itemView.findViewById(R.id.name);
        messageTextView = itemView.findViewById(R.id.description);
        this.mListAdapter = listAdapter;

        itemView.setOnClickListener(this);
    }

    public void bindView(Object object) {
        if (object instanceof UserModel) {
            UserModel userModel = (UserModel) object;
            userNameTextView.setText(userModel.getName());
            Utils.setGlideCircleImageProfile(userImageView, userModel.getPhoto());
            messageTextView.setText(userModel.getEmail());
        }
    }


    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION && mListAdapter != null && mListAdapter.getClickListener() != null) {
            mListAdapter.getClickListener().onClickItem(mListAdapter.getItem(position), position);
        }
    }
}
