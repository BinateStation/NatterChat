package rkr.binatestation.natterchat.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by RKR on 21-10-2017.
 * UserModel
 */
@IgnoreExtraProperties
public class UserModel extends BaseModel implements Parcelable {


    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
    private String photo;
    private String email;
    private String phoneNumber;
    private String pushToken;
    private ArrayList<ChatContactModel> chatContacts;


    public UserModel(@NonNull FirebaseUser user, String phoneNumber) {
        super(user.getUid(), user.getDisplayName());
        this.email = user.getEmail();
        this.photo = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "";
        this.phoneNumber = phoneNumber;
        this.chatContacts = new ArrayList<>();
    }


    public UserModel() {
    }

    protected UserModel(Parcel in) {
        super(in);
        photo = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        pushToken = in.readString();
        chatContacts = in.createTypedArrayList(ChatContactModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(photo);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeString(pushToken);
        dest.writeTypedList(chatContacts);
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public ArrayList<ChatContactModel> getChatContacts() {
        return chatContacts;
    }

    public void setChatContacts(ArrayList<ChatContactModel> chatContacts) {
        this.chatContacts = chatContacts;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
