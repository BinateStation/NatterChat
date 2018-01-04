package rkr.binatestation.natterchat.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by RKR on 21-10-2017.
 * ChatContactModel
 */

@IgnoreExtraProperties
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
    private UserModel receiver;
    private ArrayList<ChatMessageModel> chatMessages = new ArrayList<>();

    public ChatContactModel() {
    }

    public ChatContactModel(String id, String name, UserModel receiver, ArrayList<ChatMessageModel> chatMessages) {
        super(id, name);
        this.receiver = receiver;
        this.chatMessages = chatMessages;
    }

    private ChatContactModel(Parcel in) {
        super(in);
        receiver = in.readParcelable(UserModel.class.getClassLoader());
        chatMessages = in.createTypedArrayList(ChatMessageModel.CREATOR);
    }

    public static ArrayList<Object> getData(ArrayList<ChatMessageModel> chatMessageModels) {
        ArrayList<Object> data = new ArrayList<>();
        data.addAll(chatMessageModels);
        return data;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(receiver, flags);
        dest.writeTypedList(chatMessages);
    }

    @Override
    public int describeContents() {
        return 0;
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
