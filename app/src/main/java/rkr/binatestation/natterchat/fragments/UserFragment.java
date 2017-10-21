package rkr.binatestation.natterchat.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.models.UserModel;
import rkr.binatestation.natterchat.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    private ImageView mProfileImageView;
    private TextView mNameTextView;
    private TextView mDescriptionTextView;
    private View mRootView;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * gets the instance of UserFragment
     *
     * @return UserFragment
     */
    public static UserFragment newInstance() {

        Bundle args = new Bundle();

        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_user, container, false);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProfileImageView = view.findViewById(R.id.image_view);
        mNameTextView = view.findViewById(R.id.name);
        mDescriptionTextView = view.findViewById(R.id.description);

    }

    /**
     * sets the UserModel
     *
     * @param userModel UserModel value
     */
    public void setUserModel(UserModel userModel) {
        setName(userModel.getName());
        setProfileImage(userModel.getPhoto());
    }

    /**
     * sets the UserModel
     *
     * @param userModel UserModel value
     */
    public void setUserModel(@NonNull FirebaseUser userModel) {
        setName(userModel.getDisplayName());
        setDescription(userModel.getEmail());
        Uri profileImage = userModel.getPhotoUrl();
        if (profileImage != null) {
            setProfileImage(profileImage.toString());
        }
    }

    /**
     * sets the color of name
     *
     * @param color int value
     */
    public void setNameColor(@ColorRes int color) {
        if (mNameTextView != null) {
            mNameTextView.setTextColor(ContextCompat.getColor(getContext(), color));
        }

    }

    /**
     * sets the color of description
     *
     * @param color int value
     */
    public void setDescriptionColor(@ColorRes int color) {
        if (mDescriptionTextView != null) {
            mDescriptionTextView.setTextColor(ContextCompat.getColor(getContext(), color));
        }
    }

    /**
     * sets the color of Background
     *
     * @param color int value
     */
    public void setBackgroundColor(@ColorRes int color) {
        if (mRootView != null) {
            mRootView.setBackgroundColor(ContextCompat.getColor(getContext(), color));
        }
    }

    /**
     * sets the name
     *
     * @param label String value
     */
    public void setName(String label) {
        if (mNameTextView != null) {
            if (!TextUtils.isEmpty(label)) {
                mNameTextView.setText(label);
            }
        }

    }

    /**
     * sets the profile image
     *
     * @param imageUrl String value
     */
    public void setProfileImage(String imageUrl) {
        if (mProfileImageView != null) {
            if (!TextUtils.isEmpty(imageUrl)) {
                Utils.setGlideCircleImageProfile(mProfileImageView, imageUrl);
            }
        }

    }

    /**
     * sets the description
     *
     * @param description String value
     */
    public void setDescription(String description) {
        if (mDescriptionTextView != null) {
            if (!TextUtils.isEmpty(description)) {
                mDescriptionTextView.setText(description);
            }
        }

    }
}
