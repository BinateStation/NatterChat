package rkr.binatestation.natterchat.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.fragments.UserFragment;
import rkr.binatestation.natterchat.fragments.UsersSwipeListFragment;
import rkr.binatestation.natterchat.models.ChatContactModel;
import rkr.binatestation.natterchat.models.UserModel;

public class DetailsActivity extends BaseActivity {

    private static final String KEY_DATA_MODEL = "DATA_MODEL";
    /**
     * instance of Model classes
     */
    private Object mDataModel;

    public static Intent newInstance(Context context, Object dataModel) {
        Intent intent = new Intent(context, DetailsActivity.class);
        if (dataModel instanceof Parcelable) {
            Parcelable model = (Parcelable) dataModel;
            intent.putExtra(KEY_DATA_MODEL, model);
        }
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setBackActionEnabled();

        mDataModel = getIntent().getParcelableExtra(KEY_DATA_MODEL);
        if (savedInstanceState == null) {
            loadFragments();
        }
    }

    /**
     * loads the fragments
     */
    private void loadFragments() {
        if (mDataModel instanceof UserModel) {
            UserModel userModel = (UserModel) mDataModel;
            loadFragment(UserFragment.newInstance(userModel), UserFragment.TAG);
        }
        if (mDataModel instanceof ChatContactModel) {
            ChatContactModel chatContactModel = (ChatContactModel) mDataModel;
            loadFragment(UsersSwipeListFragment.newInstance(chatContactModel), UsersSwipeListFragment.TAG);
        }
    }

    /**
     * load the fragments to the container
     *
     * @param fragment Fragment value
     * @param tag      String value
     */
    private void loadFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit();
    }
}
