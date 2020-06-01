package com.example.tak_frontend.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;

import com.pusher.client.channel.SubscriptionEventListener;
import androidx.annotation.Nullable;

public class BackgroundService extends Service {

/*    PusherOptions options = new PusherOptions();
        options.setCluster("APP_CLUSTER");

    Pusher pusher = new Pusher("APP_KEY", options);
        pusher.connect();

        private Channel channel = pusher.subscribe("my-channel");

        channel.bind("my-event", new SubscriptionEventListener() {
        @Override
        public void onEvent(String channelName, String eventName, String data) {
            System.out.println(data);
        }
    });*/







    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {






        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
