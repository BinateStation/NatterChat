package rkr.binatestation.natterchat.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.adapters.ListAdapter;
import rkr.binatestation.natterchat.models.ChatMessageModel;


/**
 * Created by RKR on 26-09-2017.
 * ChatMessageViewHolder
 */

public class ChatMessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static final int LAYOUT_ID_RIGHT = R.layout.adapter_chat_message_right;
    public static final int LAYOUT_ID_LEFT = R.layout.adapter_chat_message_left;

    private final ListAdapter mListAdapter;
    private TextView messageTextView;
    private TextView dateTimeTextView;
    private ImageView userProfileImageView;
    private ImageView statusImageView;

    public ChatMessageViewHolder(View itemView, ListAdapter listAdapter) {
        super(itemView);
        this.mListAdapter = listAdapter;

        messageTextView = itemView.findViewById(R.id.message);
        dateTimeTextView = itemView.findViewById(R.id.date_time);
        userProfileImageView = itemView.findViewById(R.id.user_profile_image);
        statusImageView = itemView.findViewById(R.id.status);

        itemView.setOnClickListener(this);
    }

    public void bindView(Object object) {
        if (object instanceof ChatMessageModel) {
            ChatMessageModel chatMessageModel = (ChatMessageModel) object;
//            UserModel userModel = chatMessageModel.getUserModel();
//            messageTextView.setText(chatMessageModel.getMessage());
//            dateTimeTextView.setText(chatMessageModel.formatSameDayTime());
//            statusImageView.setVisibility(View.INVISIBLE);
//            long myId = SessionUtils.getUserId(statusImageView.getContext());
//            if (userModel != null) {
//                Utils.setGlideCircleImageProfile(userProfileImageView, userModel.getProfileImage());
//                if (myId == userModel.getId()) {
//                    statusImageView.setVisibility(View.VISIBLE);
//                    switch (Status.fromValue(chatMessageModel.getStatus())) {
//                        case SEND:
//                            statusImageView.setImageResource(R.drawable.ic_done_black_24dp);
//                            statusImageView.setColorFilter(ContextCompat.getColor(statusImageView.getContext(), android.R.color.black));
//                            break;
//                        case RECEIVED:
//                            statusImageView.setImageResource(R.drawable.ic_done_all_black_24dp);
//                            statusImageView.setColorFilter(ContextCompat.getColor(statusImageView.getContext(), android.R.color.black));
//                            break;
//                        case READ:
//                            statusImageView.setImageResource(R.drawable.ic_done_all_black_24dp);
//                            statusImageView.setColorFilter(ContextCompat.getColor(statusImageView.getContext(), R.color.colorAccent));
//                            break;
//                        default:
//                            statusImageView.setImageResource(R.drawable.ic_access_time_black_24dp);
//                            statusImageView.setColorFilter(ContextCompat.getColor(statusImageView.getContext(), android.R.color.black));
//                    }
//                }
//            }
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
