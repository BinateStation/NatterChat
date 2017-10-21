package rkr.binatestation.natterchat.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.ExecutionException;

import rkr.binatestation.natterchat.R;
import rkr.binatestation.natterchat.activities.SplashScreenActivity;

public class NatterChatFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "KitabiFirebaseMessaging";
    // The id of the channel.
    private String id = "my_channel_01";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), "");
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param title       title of notification
     * @param messageBody FCM message body received.
     * @param userImage   the user Image
     */
    private void sendNotification(String title, String messageBody, String userImage) {
        Intent chatIntent = getChatIntent();
        chatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultPendingIntent = TaskStackBuilder
                .create(getApplicationContext())
                .addNextIntentWithParentStack(chatIntent)
                .getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(messageBody);
        bigText.setBigContentTitle(title);
        Bitmap largeLogo;
        try {
            largeLogo = Glide.
                    with(NatterChatFirebaseMessagingService.this).
                    asBitmap().
                    load(userImage).
                    apply(RequestOptions.circleCropTransform()).
                    submit(100, 100).get();
        } catch (InterruptedException | ExecutionException e) {
            largeLogo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.drawable.ic_stat_natter_chat)
                .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setLargeIcon(largeLogo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setStyle(bigText)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(getNotificationChannel());
        }
        notificationManager.notify(1, notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel getNotificationChannel() {
        // The user-visible name of the channel.
        CharSequence name = getString(R.string.app_name);
        // The user-visible description of the channel.
        String description = getString(R.string.app_name);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        // Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        // Sets the notification light color for notifications posted to this
        // channel, if the device supports this feature.
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        return mChannel;
    }

    private Intent getChatIntent() {
        return new Intent(this, SplashScreenActivity.class);
    }

}
