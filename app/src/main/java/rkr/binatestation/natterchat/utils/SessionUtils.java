package rkr.binatestation.natterchat.utils;

import android.content.Context;

import static rkr.binatestation.natterchat.utils.Constant.KEY_PUSH_TOKEN;
import static rkr.binatestation.natterchat.utils.Constant.KEY_RECEIVER_ID;


/**
 * Created by rosemary on 8/24/2017.
 * SessionUtils
 */

public class SessionUtils {
    private static final String KEY_CONSTANT = "CONSTANT";
    private static final String KEY_IS_CHAT_RESUMED = "IS_CHAT_RESUMED";

    public static boolean isChatResumed(Context context) {
        return context.getSharedPreferences(KEY_CONSTANT, Context.MODE_PRIVATE).getBoolean(KEY_IS_CHAT_RESUMED, false);
    }

    public static String getChatReceiverId(Context context) {
        return context.getSharedPreferences(KEY_CONSTANT, Context.MODE_PRIVATE).getString(KEY_RECEIVER_ID, "");
    }

    public static void setChatResumed(Context context, boolean status, String receiverId) {
        context.getSharedPreferences(KEY_CONSTANT, Context.MODE_PRIVATE).edit()
                .putBoolean(KEY_IS_CHAT_RESUMED, status)
                .putString(KEY_RECEIVER_ID, receiverId)
                .apply();
    }


    public static String getPushToken(Context context) {
        return context.getSharedPreferences(KEY_CONSTANT, Context.MODE_PRIVATE).getString(KEY_PUSH_TOKEN, "");
    }

    public static void setPushToken(Context context, String refreshedToken) {
        context.getSharedPreferences(KEY_CONSTANT, Context.MODE_PRIVATE).edit().putString(KEY_PUSH_TOKEN, refreshedToken).apply();
    }

}
