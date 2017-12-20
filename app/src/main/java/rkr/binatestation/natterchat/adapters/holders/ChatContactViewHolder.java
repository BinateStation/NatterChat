package rkr.binatestation.natterchat.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.adapters.RecyclerViewAdapter;
import rkr.binatestation.natterchat.models.ChatContactModel;
import rkr.binatestation.natterchat.models.ChatMessageModel;
import rkr.binatestation.natterchat.models.Status;
import rkr.binatestation.natterchat.models.UserModel;
import rkr.binatestation.natterchat.utils.Utils;


/**
 * Created by RKR on 25-09-2017.
 * ChatContactViewHolder
 */

public class ChatContactViewHolder extends RecyclerViewAdapterBaseViewHolder {
    public static final int LAYOUT_ID = R.layout.adapter_chat_contact;
    private ImageView userImageView;
    private TextView userNameTextView;
    private TextView messageTextView;
    private TextView dateTimeTextView;
    private View indicatorUnread;

    public ChatContactViewHolder(View itemView, RecyclerViewAdapter recyclerViewAdapter) {
        super(itemView, recyclerViewAdapter);
        userImageView = itemView.findViewById(R.id.image_view);
        userNameTextView = itemView.findViewById(R.id.name);
        messageTextView = itemView.findViewById(R.id.description);
        dateTimeTextView = itemView.findViewById(R.id.date_time);
        indicatorUnread = itemView.findViewById(R.id.indicator);

        itemView.setOnClickListener(this);
    }

    public void bindView(Object object) {
        userImageView.setImageResource(R.drawable.ic_person_black_alpha_24dp);
        userNameTextView.setText("");
        messageTextView.setText("");
        dateTimeTextView.setText("");
        indicatorUnread.setVisibility(View.GONE);
        if (object instanceof ChatContactModel) {
            ChatContactModel chatContactModel = (ChatContactModel) object;

            UserModel receiver = chatContactModel.getReceiver();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null && receiver.getId().equals(user.getUid())) {
                receiver = chatContactModel.getSender();
            }
            int size = chatContactModel.getChatMessages().size();
            if (size > 0) {
                ChatMessageModel messageModel = chatContactModel.getChatMessages().get(size - 1);
                messageTextView.setText(messageModel.getName());
                dateTimeTextView.setText(messageModel.formatSameDayTime());
                if (Status.READ.getValue() != messageModel.getStatus()) {
                    indicatorUnread.setVisibility(View.VISIBLE);
                }
            }
            if (receiver != null) {
                userNameTextView.setText(receiver.getName());
                Utils.setGlideCircleImageProfile(userImageView, receiver.getPhoto());
            }
        }
    }
}
