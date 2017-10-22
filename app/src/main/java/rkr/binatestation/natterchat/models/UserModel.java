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
    private String pushToken;

    public UserModel(@NonNull FirebaseUser user) {
        super(user.getUid(), user.getDisplayName());
        this.email = user.getEmail();
        this.photo = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "";
    }


    public UserModel() {
    }

    protected UserModel(Parcel in) {
        super(in);
        photo = in.readString();
        email = in.readString();
        pushToken = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(photo);
        dest.writeString(email);
        dest.writeString(pushToken);
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

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
