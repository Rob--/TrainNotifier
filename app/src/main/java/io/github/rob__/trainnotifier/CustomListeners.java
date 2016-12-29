package io.github.rob__.trainnotifier;

import io.github.rob__.trainnotifier.API.Models.Mobile.Journey;
import io.github.rob__.trainnotifier.API.Models.Mobile.JourneyData;
import io.github.rob__.trainnotifier.API.Models.Realtime.RealtimeData;
import retrofit2.Response;

public class CustomListeners {
    public interface RecentSearchListener {
        void recentSearchClicked(String[] recentSearch);
    }

    public interface JourneyClickListener {
        void journeyClicked(Journey journey, int position);
    }

    public interface TrainSavedListener {
        void trainSaved();
    }

    public interface TrainlineCallback{
        void onResponse(Response<JourneyData> response);
        void onFailure(Throwable t);
    }

    public interface RealtimeCallback{
        void onResponse(Response<RealtimeData> response);
        void onFailure(Throwable t);
    }
}
