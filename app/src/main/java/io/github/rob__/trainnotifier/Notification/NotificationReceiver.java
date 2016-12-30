package io.github.rob__.trainnotifier.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.github.rob__.trainnotifier.API.Models.Mobile.Journey;
import io.github.rob__.trainnotifier.Utils.Utils;

import static android.content.Context.ALARM_SERVICE;

public class NotificationReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent){
        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.putExtra("journey", intent.getStringExtra("journey"));
        serviceIntent.putExtra("dismiss", intent.getBooleanExtra("dismiss", false));

        startWakefulService(context, serviceIntent);
    }

    /**
     * This method is called whenever a journey is saved, it will tell the alarm manager
     * to broadcast an event that this class will receive once a day to create the notification
     * for the train.
     *
     * @param journey - journey that's being saved
     * @param context - context to get AlarmManager
     */
    public static void setupPolling(Journey journey, Context context){
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("journey", Utils.toJson(journey));
        intent.putExtra("dismiss", false);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, journey.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        try {
            Date departTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(journey.getLegs().get(0).getOrigin().getScheduledTime());
            Calendar c = Calendar.getInstance();
            c.setTime(departTime);
            /* remove 30 minutes from the time */
            c.add(Calendar.MINUTE, -15);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } catch(ParseException e){
            // maybe remove journey and show alert dialog
        }
    }

    /**
     * This method will stop this journey from being polled in the future by removing it from
     * the alarm manager service and will stop the notification if currently present.
     * @param journey - journey being removed
     * @param context - context to get AlarmManager
     */
    public static void removePolling(Journey journey, Context context){
        /* create our intents */
        Intent intent = new Intent(context.getApplicationContext(), NotificationReceiver.class);
        intent.putExtra("journey", Utils.toJson(journey));
        intent.putExtra("dismiss", true);

        /* this will push our intent to the 'onReceive' method above and tell the service
           to dismiss the notification if there is one on going */
        context.sendBroadcast(intent);

        Log.d("NotificationReceiver", "Attempt to remove notification for #" + String.valueOf(journey.getId()));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, journey.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /* use the created intents to cancel the set alarms */
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
