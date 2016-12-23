package io.github.rob__.trainnotifier;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import io.github.rob__.trainnotifier.API.Models.Journey;

import static android.content.Context.ALARM_SERVICE;

public class NotificationReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent){
        /*  TODO: This broadcast receiver declares an intent-filter for a protected broadcast action string, which can only be sent by the system, not third-party applications. However, the receiver's onReceive method does not appear to call getAction to ensure that the received Intent's action string matches the expected value, potentially making it possible for another actor to send a spoofed intent with no action string or a different action string and cause undesired behavior. In this case, it is possible that the onReceive method passed the received Intent to another method that checked the action string. If so, this finding can safely be ignored. (at line 17)*/

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
        intent.putExtra("journey", Utils.journeyToJson(journey));
        intent.putExtra("dismiss", false);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, journey.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, AlarmManager.INTERVAL_DAY, pendingIntent);
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
        intent.putExtra("journey", Utils.journeyToJson(journey));
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
