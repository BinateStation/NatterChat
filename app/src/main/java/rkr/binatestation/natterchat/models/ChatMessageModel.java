package rkr.binatestation.natterchat.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Calendar;

import static java.text.DateFormat.LONG;
import static java.text.DateFormat.SHORT;

/**
 * Created by RKR on 25-09-2017.
 * ChatMessageModel
 */

@IgnoreExtraProperties
public class ChatMessageModel extends BaseModel implements Parcelable {

    public static final Creator<ChatMessageModel> CREATOR = new Creator<ChatMessageModel>() {
        @Override
        public ChatMessageModel createFromParcel(Parcel in) {
            return new ChatMessageModel(in);
        }

        @Override
        public ChatMessageModel[] newArray(int size) {
            return new ChatMessageModel[size];
        }
    };
    private long chatTime;
    private int status;
    private UserModel userModel;

    public ChatMessageModel() {
    }


    public ChatMessageModel(String id, String name, long chatTime, int status, UserModel userModel) {
        super(id, name);
        this.chatTime = chatTime;
        this.status = status;
        this.userModel = userModel;
    }

    protected ChatMessageModel(Parcel in) {
        super(in);
        chatTime = in.readLong();
        status = in.readInt();
        userModel = in.readParcelable(UserModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(chatTime);
        dest.writeInt(status);
        dest.writeParcelable(userModel, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getChatTime() {
        return chatTime;
    }

    public void setChatTime(long chatTime) {
        this.chatTime = chatTime;
    }

    /**
     * The status of the message weather read , delivered or send
     *
     * @return the value of status {@link Status#PENDING} , {@link Status#SEND},
     * {@link Status#RECEIVED}, {@link Status#READ}
     */
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String formatSameDayTime() {
        return DateUtils.formatSameDayTime(
                getChatTime(),
                Calendar.getInstance().getTimeInMillis(),
                LONG,
                SHORT
        ).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChatMessageModel) {
            ChatMessageModel chatMessageModel = (ChatMessageModel) obj;
            return getId().equals(chatMessageModel.getId());
        }
        return super.equals(obj);
    }
}
