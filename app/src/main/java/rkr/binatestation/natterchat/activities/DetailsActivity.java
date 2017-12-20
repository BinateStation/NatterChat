package rkr.binatestation.natterchat.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.fragments.UserFragment;
import rkr.binatestation.natterchat.models.BaseModel;
import rkr.binatestation.natterchat.models.UserModel;

public class DetailsActivity extends BaseActivity {

    private static final String KEY_DATA_MODEL = "DATA_MODEL";
    /**
     * instance of Model classes
     */
    private Object mDataModel;

    public static Intent newInstance(Context context, BaseModel dataModel) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(KEY_DATA_MODEL, dataModel);
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
