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
 * ChatContactViewHolder
 */

public class ChatContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static final int LAYOUT_ID = R.layout.fragment_user;
    private final ListAdapter mListAdapter;
    private ImageView userImageView;
    private TextView userNameTextView;
    private TextView messageTextView;
    private TextView dateTimeTextView;
    private View indicatorUnread;

    public ChatContactViewHolder(View itemView, ListAdapter listAdapter) {
        super(itemView);
        userImageView = itemView.findViewById(R.id.image_view);
        userNameTextView = itemView.findViewById(R.id.name);
        messageTextView = itemView.findViewById(R.id.description);
//        dateTimeTextView = itemView.findViewById(R.id.date_time);
//        indicatorUnread = itemView.findViewById(R.id.indicator_unread);
        this.mListAdapter = listAdapter;

        itemView.setOnClickListener(this);
    }

    public void bindView(Object object) {
        if (object instanceof UserModel) {
            UserModel userModel = (UserModel) object;
            userNameTextView.setText(userModel.getName());
            Utils.setGlideCircleImageProfile(userImageView, userModel.getPhoto());
            messageTextView.setText(userModel.getPushToken());
        }
//        userImageView.setImageResource(R.drawable.ic_person_black_alpha_24dp);
//        userNameTextView.setText("");
//        messageTextView.setText("");
//        dateTimeTextView.setText("");
//        indicatorUnread.setVisibility(View.GONE);
//
//        if (object instanceof ChatGroupModel) {
//            ChatGroupModel chatGroupModel = (ChatGroupModel) object;
//            long myId = SessionUtils.getUserId(itemView.getContext());
//            UserModel userModel;
//            if (myId == chatGroupModel.getBuyerUserModel().getId()) {
//                userModel = chatGroupModel.getSellerModel();
//            } else {
//                userModel = chatGroupModel.getBuyerUserModel();
//            }
//            if (userModel != null) {
//                Utils.setGlideCircleImageProfile(userImageView, userModel.getProfileImage());
//                userNameTextView.setText(userModel.getName());
//            }
//            ChatMessageModel chatMessageModel = chatGroupModel.getChatMessageModel();
//            if (chatMessageModel != null) {
//                messageTextView.setText(chatMessageModel.getMessage());
//                dateTimeTextView.setText(chatMessageModel.formatSameDayTime());
//                if (myId != chatMessageModel.getUserModel().getId()) {
//                    if (chatMessageModel.getStatus() == Status.READ.getValue()) {
//                        indicatorUnread.setVisibility(View.GONE);
//                    } else {
//                        indicatorUnread.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//            BookCopyModel bookCopyModel = chatGroupModel.getBookCopyModel();
//            if (bookCopyModel != null) {
//                for (BookImageModel bookImageModel : bookCopyModel.getImages()) {
//                    if (bookImageModel != null) {
//                        if (!TextUtils.isEmpty(bookImageModel.getImagePath())) {
//                            Utils.setGlideImage(bookImageView, bookImageModel.getImagePath());
//                        }
//                        break;
//                    }
//                }
//            }
//        }
    }


    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION && mListAdapter != null && mListAdapter.getClickListener() != null) {
            mListAdapter.getClickListener().onClickItem(mListAdapter.getItem(position), position);
        }
    }
}
