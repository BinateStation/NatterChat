package rkr.binatestation.natterchat.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by RKR on 21-10-2017.
 * ChatContactModel
 */

public class ChatContactModel extends BaseModel implements Parcelable {
    public static final Creator<ChatContactModel> CREATOR = new Creator<ChatContactModel>() {
        @Override
        public ChatContactModel createFromParcel(Parcel in) {
            return new ChatContactModel(in);
        }

        @Override
        public ChatContactModel[] newArray(int size) {
            return new ChatContactModel[size];
        }
    };
    private UserModel sender;
    private UserModel receiver;
    private ArrayList<ChatMessageModel> chatMessages = new ArrayList<>();

    public ChatContactModel() {
    }

    public ChatContactModel(String id, String name, UserModel sender, UserModel receiver, ArrayList<ChatMessageModel> chatMessages) {
        super(id, name);
        this.sender = sender;
        this.receiver = receiver;
        this.chatMessages = chatMessages;
    }

    protected ChatContactModel(Parcel in) {
        super(in);
        sender = in.readParcelable(UserModel.class.getClassLoader());
        receiver = in.readParcelable(UserModel.class.getClassLoader());
        chatMessages = in.createTypedArrayList(ChatMessageModel.CREATOR);
    }

    public static ArrayList<Object> getData(ArrayList<ChatMessageModel> chatMessageModels) {
        ArrayList<Object> data = new ArrayList<>();
        for (ChatMessageModel messageModel : chatMessageModels) {
            data.add(messageModel);
        }
        return data;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(sender, flags);
        dest.writeParcelable(receiver, flags);
        dest.writeTypedList(chatMessages);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public UserModel getSender() {
        return sender;
    }

    public void setSender(UserModel sender) {
        this.sender = sender;
    }

    public UserModel getReceiver() {
        return receiver;
    }

    public void setReceiver(UserModel receiver) {
        this.receiver = receiver;
    }

    public ArrayList<ChatMessageModel> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessageModel> chatMessages) {
        this.chatMessages = chatMessages;
    }
}
