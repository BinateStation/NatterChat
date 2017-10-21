package rkr.binatestation.natterchat.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import rkr.binatestation.natterchat.R;


/**
 * Created by RKR on 21-09-2017.
 * EmptyStateModel
 */

public class EmptyStateModel extends BaseModel implements Parcelable {

    public static final Creator<EmptyStateModel> CREATOR = new Creator<EmptyStateModel>() {
        @Override
        public EmptyStateModel createFromParcel(Parcel in) {
            return new EmptyStateModel(in);
        }

        @Override
        public EmptyStateModel[] newArray(int size) {
            return new EmptyStateModel[size];
        }
    };
    @DrawableRes
    private int icon;
    private String message;

    public EmptyStateModel(String id, String title, String message, @DrawableRes int icon) {
        super(id, title);
        this.icon = icon;
        this.message = message;
    }

    protected EmptyStateModel(Parcel in) {
        super(in);
        icon = in.readInt();
        message = in.readString();
    }


    public static EmptyStateModel getNoInternetEmptyModel() {
        return new EmptyStateModel("", "No Internet", "Sorry you are not connected to internet, Please check your connection and try again!", R.drawable.ic_signal_wifi_off_black_24dp);
    }

    public static EmptyStateModel getUnKnownEmptyModel() {
        return new EmptyStateModel("", "Alert", "Something went wrong, Please try again later !", R.drawable.ic_sentiment_dissatisfied_black_24dp);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EmptyStateModel) {
            EmptyStateModel emptyStateModel = (EmptyStateModel) obj;
            return getId().equals(emptyStateModel.getId());
        }
        return super.equals(obj);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(icon);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(@DrawableRes int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return super.getName();
    }

    public void setTitle(String title) {
        super.setName(title);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
