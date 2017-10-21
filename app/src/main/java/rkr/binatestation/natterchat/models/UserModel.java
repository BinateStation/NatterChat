package rkr.binatestation.natterchat.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by RKR on 21-10-2017.
 * UserModel
 */
@IgnoreExtraProperties
public class UserModel implements Parcelable {
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
    private String userId;
    private String name;
    private String photo;

    public UserModel() {
    }

    public UserModel(String userId, String name, String photo) {
        this.userId = userId;
        this.name = name;
        this.photo = photo;
    }

    public UserModel(@NonNull FirebaseUser user) {
        this.userId = user.getUid();
        this.name = user.getDisplayName();
        this.photo = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "";
    }

    private UserModel(Parcel in) {
        userId = in.readString();
        name = in.readString();
        photo = in.readString();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userId);
        parcel.writeString(name);
        parcel.writeString(photo);
    }
}
