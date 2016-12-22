package io.github.rob__.trainnotifier;

import android.view.View;

import io.github.rob__.trainnotifier.API.Models.API;
import io.github.rob__.trainnotifier.API.Models.Journey;
import retrofit2.Response;

public class CustomListeners {
    public interface RecentSearchListener {
        void recentSearchClicked(View view, String[] recentSearch, int position);
    }

    public interface JourneyClickListener {
        void journeyClicked(View view, Journey journey, int position);
    }

    public interface TrainSavedListener {
        void trainSaved();
    }

    public interface TrainlineCallback{
        void onResponse(Response<API> response);
        void onFailure(Throwable t);
    }
}