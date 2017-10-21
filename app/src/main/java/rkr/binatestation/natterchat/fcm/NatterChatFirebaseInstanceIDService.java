package rkr.binatestation.natterchat.fcm;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import rkr.binatestation.natterchat.utils.SessionUtils;

import static rkr.binatestation.natterchat.utils.Constant.KEY_TABLE_USERS;


public class NatterChatFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "NatterChatFirebaseInsta";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    @SuppressLint("HardwareIds")
    private void sendRegistrationToServer(String refreshedToken) {
        Log.d(TAG, "sendRegistrationToServer() called with: refreshedToken = [" + refreshedToken + "]");
        SessionUtils.setPushToken(this, refreshedToken);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            myRef.child(KEY_TABLE_USERS).child(user.getUid()).child("pushToken").setValue(refreshedToken);
        }
    }

}
