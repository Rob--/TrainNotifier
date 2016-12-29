package io.github.rob__.trainnotifier.Details;

import android.util.Log;

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

    public void getRealtimeInfo(final Journey journey){
        Log.d("JourneyPresenter", "Getting realtime info...");
        final List<RealtimeData> realtimeData = new ArrayList<>();
        for(Leg leg : journey.getLegs()){
            final String trainId = leg.getTrainId();
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
