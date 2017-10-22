package rkr.binatestation.natterchat.listeners;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by RKR on 22-10-2017.
 * RegistrationListener
 */

public interface RegistrationListener {
    void onSuccessRegistration(FirebaseUser user);

    void onRegistrationFailed();
}
