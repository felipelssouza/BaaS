package br.com.mob11.baas.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import br.com.mob11.baas.util.NotificationUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationUtils.showNotification(this, remoteMessage.getFrom(), remoteMessage.getNotification().getBody());
    }
}