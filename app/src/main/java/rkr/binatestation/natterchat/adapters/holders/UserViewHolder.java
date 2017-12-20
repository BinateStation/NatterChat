package rkr.binatestation.natterchat.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.adapters.RecyclerViewAdapter;
import rkr.binatestation.natterchat.models.UserModel;
import rkr.binatestation.natterchat.utils.Utils;


/**
 * Created by RKR on 25-09-2017.
 * UserViewHolder
 */

public class UserViewHolder extends RecyclerViewAdapterBaseViewHolder {
    public static final int LAYOUT_ID = R.layout.fragment_user;
    private ImageView userImageView;
    private TextView userNameTextView;
    private TextView messageTextView;


    public UserViewHolder(View itemView, RecyclerViewAdapter recyclerViewAdapter) {
        super(itemView, recyclerViewAdapter);
        userImageView = itemView.findViewById(R.id.image_view);
        userNameTextView = itemView.findViewById(R.id.name);
        messageTextView = itemView.findViewById(R.id.description);

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
}
