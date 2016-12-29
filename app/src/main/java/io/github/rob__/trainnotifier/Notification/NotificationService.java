package io.github.rob__.trainnotifier.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import io.github.rob__.trainnotifier.API.Models.Mobile.Journey;
import io.github.rob__.trainnotifier.API.Models.Mobile.JourneyData;
import io.github.rob__.trainnotifier.API.Models.Mobile.Leg;
import io.github.rob__.trainnotifier.API.Models.Mobile.Query;
import io.github.rob__.trainnotifier.API.TrainlineAPI;
import io.github.rob__.trainnotifier.CustomListeners;
import io.github.rob__.trainnotifier.Details.JourneyActivity;
import io.github.rob__.trainnotifier.R;
import io.github.rob__.trainnotifier.Utils.Utils;
import retrofit2.Response;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class NotificationService extends Service{

    private final String DISMISS_EVENT_NAME = "dismiss_event";
    private final String TAG = "NotificationService";

    private NotificationManager manager;
    private final long updateInterval = 1000 * 10;
    private final Handler handler = new Handler();

    private final TrainlineAPI trainlineAPI = new TrainlineAPI();
    private final HashMap<Integer, Journey> journeys = new HashMap<>();

    /* uses journey ids to track notification text, if a change is detected we add a vibration
       to the notification */
    private final HashMap<Integer, String> notificationText = new HashMap<>();

    private final NotificationDismissReceiver dismissReceiver = new NotificationDismissReceiver();
    public class NotificationDismissReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            Journey journey = Utils.journeyFromJson(intent.getStringExtra("journey"));
            dismissNotification(journey);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if(manager == null){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }

        /* if our service has been started, this function will be called regardless
           if the service is already running */

        Log.d(TAG, "Journey id: " + String.valueOf(Utils.journeyFromJson(intent.getStringExtra("journey")).getId()));

        registerReceiver(dismissReceiver, new IntentFilter(DISMISS_EVENT_NAME));

        /* attempt to get our extras, if we can't, return */
        final Journey savedJourney = Utils.journeyFromJson(intent.getStringExtra("journey"));
        if(savedJourney == null) return Service.START_NOT_STICKY;

        if(intent.getBooleanExtra("dismiss", false)){
            dismissNotification(savedJourney);
            Log.d("NotificationService", "Told to dismiss notification #" + String.valueOf(savedJourney.getId()));
            return Service.START_NOT_STICKY;
        }

        /* save journey in hash map */
        journeys.put(savedJourney.getId(), savedJourney);

        Log.d(TAG, "Updating notification for #" + String.valueOf(savedJourney.getId()));

        /* create the query for the journey we want to check on */
        Query query = Utils.buildQuery(Utils.stationFromCode(savedJourney.getOrigin()), Utils.stationFromCode(savedJourney.getDestination()));

        pollForUpdates(query, savedJourney);

        return Service.START_NOT_STICKY;
    }

    private void pollForUpdates(final Query query, final Journey savedJourney){
        /* attempt to get the journey, if it's null it means it was dismissed and we no
           longer need to poll for it */
        Journey journey = journeys.get(savedJourney.getId());
        if(journey == null) return;

        Log.d(TAG, "Making JourneyData request to update the notification");

        /* execute the query, callback will handle response */
        trainlineAPI.getJourneys(query, new CustomListeners.TrainlineCallback() {
            @Override
            public void onResponse(Response<JourneyData> response) {
                /* loop through the journeys to find the same
                   journey the user has saved so we can get updates for it */
                List<Journey> journeys = response.body().getJourneys();
                for(Journey j : journeys){
                    if(Utils.areJourneysIdentical(j, savedJourney)){
                        /* if we've found the exact journey the
                           user is polling for, build the notification */
                        buildNotification(j, savedJourney.getId());

                        /* postDelayed will call this pollForUpdates function again
                           after the interval time */
                        postDelayed(query, savedJourney);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //builder
                //        .setContentTitle(Utils.getRoute(savedJourney.getOrigin(), savedJourney.getDestination()))
                //        .setContentText("Unable to fetch train journey data!");

                //manager.notify(savedJourney.getId(), builder.build());

                postDelayed(query, savedJourney);
            }
        });
    }

    private void postDelayed(final Query query, final Journey savedJourney){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pollForUpdates(query, savedJourney);
            }
        }, updateInterval);
    }

    private void dismissNotification(Journey journey){
        journeys.remove(journey.getId());
        Log.d(TAG, "Dismiss called for #" + String.valueOf(journey.getId()));
        manager.cancel(journey.getId());


        Log.d(TAG, "Journeys left to poll for: " + String.valueOf(journeys.size()));

        /* if we've removed the last journey, halt the service */
        if(journeys.size() == 0){
            stopSelf();
        }
    }

    private void buildNotification(final Journey journey, int savedJourneyId){
        final Journey savedJourney = journeys.get(savedJourneyId);
        if(savedJourney == null) return;

        Log.d(TAG, "Building notification for #" + String.valueOf(savedJourney.getId()));

        /* our notification text, build up as we check response */
        String text = "";

        /*if(!journey.getJourneyStatus().equals("Possible")){
            text += "Journey may not be possible!\n";
        }*/

        /* check every leg/change of the journey */
        for(int i = 0; i < journey.getLegs().size(); i++){
            Leg l = journey.getLegs().get(i);

            /* check if train is cancelled */
            if(l.getIsCancelled()){
                text += "Train from " + Utils.stationFromCode(l.getOrigin().getStationCode()) + " cancelled!";
            }

            /* check if there's a generic "Delayed" with no given expected time */
            if(l.getOrigin().getRealTimeStatus().equals("Delayed")){
                //text += "Train from " + Utils.stationFromCode(l.getOrigin().getStationCode()) + " is delayed!";
            }

            /* check each train leg delay, will return "" if there is no delay */
            String delay = Utils.getTimeDifference(l.getOrigin().getRealTime(), l.getOrigin().getScheduledTime());
            if(!delay.equals("")){
                String prefix = "Train from " + l.getOrigin().getStationCode() + " is delayed by ";
                String suffix = " (exp. " + Utils.getFormattedTime(Utils.getBestDepartTime(l.getOrigin())) + ")";
                text += prefix + delay + suffix;
            }
        }

        /* if our text hasn't changed, the train is as scheduled */
        if(text.isEmpty()) text = "Train appears to be on time!";

        /* create the dismiss intent */
        Intent dismissIntent = new Intent(DISMISS_EVENT_NAME);
        dismissIntent.putExtra("journey", Utils.toJson(savedJourney));

        /* create the pending intent for the notification */
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(this, savedJourney.getId(), dismissIntent, FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_clear_black_48px)
                .setOngoing(true)
                .addAction(R.drawable.ic_clear_black_48px, "Dismiss", dismissPendingIntent)
                .setSubText(savedJourney.getOrigin() + " to " + savedJourney.getDestination())
                .setContentTitle("Train to " + Utils.stationFromCode(savedJourney.getDestination()))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        /* when the user clicks the notification, it'll open up an activity giving a full updated
           view of the journey, we pass on the journey information through an intent */
        Intent detailsIntent = new Intent(getApplicationContext(), JourneyActivity.class);
        detailsIntent.putExtra("journey", Utils.toJson(journey));

        PendingIntent detailsPending = PendingIntent.getActivity(getApplicationContext(), 0, detailsIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.setContentIntent(detailsPending);

        /* if the last stored version of our notification text is null it means we haven't saved it
           and therefore haven't shown a notification - so we save the text and vibrate with the
           the notification, we also do this if the text content has changed so the user can be
           alerted */
        String lastNotificationText = notificationText.get(savedJourneyId);
        if(lastNotificationText == null || !lastNotificationText.equals(text)){
            builder.setVibrate(new long[] { 0, 250, 500, 250 });
            notificationText.put(savedJourneyId, text);
        }

        /* use the manager to update the current notification */
        manager.notify(savedJourneyId, builder.build());
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "Service created.");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(dismissReceiver);
        Log.d(TAG, "Service destroyed.");
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

}
