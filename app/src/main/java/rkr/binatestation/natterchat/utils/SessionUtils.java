package rkr.binatestation.natterchat.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import static rkr.binatestation.natterchat.utils.Constant.KEY_PHONE_NUMBER;
import static rkr.binatestation.natterchat.utils.Constant.KEY_PUSH_TOKEN;
import static rkr.binatestation.natterchat.utils.Constant.KEY_RECEIVER_ID;


/**
 * Created by rosemary on 8/24/2017.
 * SessionUtils
 */

public class SessionUtils {
    private static final String KEY_CONSTANT = "CONSTANT";
    private static final String KEY_SESSION = "SESSION";
    private static final String KEY_IS_CHAT_RESUMED = "IS_CHAT_RESUMED";

    public static boolean isChatResumed(@NonNull Context context) {
        return context.getSharedPreferences(KEY_CONSTANT, Context.MODE_PRIVATE).getBoolean(KEY_IS_CHAT_RESUMED, false);
    }

    public static String getChatReceiverId(@NonNull Context context) {
        return context.getSharedPreferences(KEY_CONSTANT, Context.MODE_PRIVATE).getString(KEY_RECEIVER_ID, "");
    }

    public static void setChatResumed(@NonNull Context context, boolean status, String receiverId) {
        context.getSharedPreferences(KEY_CONSTANT, Context.MODE_PRIVATE).edit()
                .putBoolean(KEY_IS_CHAT_RESUMED, status)
                .putString(KEY_RECEIVER_ID, receiverId)
                .apply();
    }


    public static String getPushToken(@NonNull Context context) {
        return context.getSharedPreferences(KEY_CONSTANT, Context.MODE_PRIVATE).getString(KEY_PUSH_TOKEN, "");
    }

    public static void setPushToken(@NonNull Context context, String refreshedToken) {
        context.getSharedPreferences(KEY_CONSTANT, Context.MODE_PRIVATE).edit().putString(KEY_PUSH_TOKEN, refreshedToken).apply();
    }

    public static String getPhoneNumber(@NonNull Context context) {
        return context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).getString(KEY_PHONE_NUMBER, "");
    }

    public static void setPhoneNumber(@NonNull Context context, String phoneNumber) {
        context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).edit().putString(KEY_PHONE_NUMBER, phoneNumber).apply();
    }

    public static void logout(@NonNull Context context) {
        context.getSharedPreferences(KEY_SESSION, Context.MODE_PRIVATE).edit().clear().apply();
    }
}
