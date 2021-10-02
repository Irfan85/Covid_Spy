package com.covid_spy.covidspy.App;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import com.covid_spy.covidspy.Util.Utils;

public class Covid_Spy extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(Utils.COVID_SPY_CHANNEL_ID, Utils.COVID_SPY_CHANNEL_NAME, NotificationManager.IMPORTANCE_MAX);
        channel.setDescription(Utils.COVID_SPY_CHANNEL_DESC);

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }
}
