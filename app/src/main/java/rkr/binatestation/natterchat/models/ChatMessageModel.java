package rkr.binatestation.natterchat.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import java.util.Calendar;

import static java.text.DateFormat.LONG;
import static java.text.DateFormat.SHORT;

/**
 * Created by RKR on 25-09-2017.
 * ChatMessageModel
 */

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
    private long dateTime;
    private int status;
    private String userId;

    public ChatMessageModel() {
    }

    public ChatMessageModel(String id, String name, long dateTime, int status, String userId) {
        super(id, name);
        this.dateTime = dateTime;
        this.status = status;
        this.userId = userId;
    }

    private ChatMessageModel(Parcel in) {
        super(in);
        dateTime = in.readLong();
        status = in.readInt();
        userId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(dateTime);
        dest.writeInt(status);
        dest.writeString(userId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String formatSameDayTime() {
        return DateUtils.formatSameDayTime(
                getDateTime(),
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
