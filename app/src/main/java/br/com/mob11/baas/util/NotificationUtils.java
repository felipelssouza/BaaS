package br.com.mob11.baas.util;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import br.com.mob11.baas.R;
import br.com.mob11.baas.activity.LoginActivity;

public class NotificationUtils {

    public static void showNotification(Context context, String titulo, String conteudo) {

        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Notification!") // title for notification
                .setContentText(conteudo) // message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(context, LoginActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        mBuilder.setContentIntent(pi);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
