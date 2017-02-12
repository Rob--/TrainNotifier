package io.github.rob__.trainnotifier.Details;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.rob__.trainnotifier.API.Models.Mobile.Journey;
import io.github.rob__.trainnotifier.API.Models.Mobile.Leg;
import io.github.rob__.trainnotifier.API.Models.Realtime.RealtimeData;
import io.github.rob__.trainnotifier.API.RealtimeAPI;
import io.github.rob__.trainnotifier.CustomListeners;
import retrofit2.Response;

class JourneyPresenter {

    private final JourneyView view;
    private final RealtimeAPI api;

    public JourneyPresenter(JourneyView view){
        this.api = new RealtimeAPI();
        this.view = view;
    }

    public void setupAnimation(View v){
        view.animationSetup(v);
    }

    public void animateElement(View v){
        view.doAnimation(v);
    }

    public void getRealtimeInfo(final Journey journey){
        Log.d("JourneyPresenter", "Getting realtime info...");
        final List<RealtimeData> realtimeData = new ArrayList<>();
        for(Leg leg : journey.getLegs()){
            final String trainId = leg.getTrainId();

            /* probably not best practice, but due to callbacks I check whether all the real
               time data has been collected by comparing the number of legs to the number of real
               time data received - so I need to add null to use this logic
               we also need to check if the transport mode is walk, if it is trainid will be null */
            if(leg.getTransportMode().equals("Walk") || trainId.isEmpty()){
                realtimeData.add(null);
                continue;
            }

            api.getRealtimeData(trainId, new CustomListeners.RealtimeCallback() {
                @Override
                public void onResponse(Response<RealtimeData> response) {
                    realtimeData.add(response.body());

                    if(realtimeData.size() == journey.getLegs().size()){
                        view.onRealtimeReceived(realtimeData);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

}
