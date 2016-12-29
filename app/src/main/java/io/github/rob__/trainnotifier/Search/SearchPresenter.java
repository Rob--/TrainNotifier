package io.github.rob__.trainnotifier.Search;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.rob__.trainnotifier.API.Models.Mobile.JourneyData;
import io.github.rob__.trainnotifier.API.TrainlineAPI;
import io.github.rob__.trainnotifier.CustomListeners;
import io.github.rob__.trainnotifier.R;
import io.github.rob__.trainnotifier.Utils.Utils;
import retrofit2.Response;

class SearchPresenter {

    private final String TAG = "SearchPresenter";
    private final SearchView view;
    private final TrainlineAPI api;
    private final Context context;

    public SearchPresenter(SearchView view, Context context){
        this.view = view;
        this.api = new TrainlineAPI();
        this.context = context;
    }

    public void getRecentSearches(Context context){
        String[][] recentSearches = Utils.getRecentSearches(context);

        /* recentSearches will be a fixed size of 5, the element will be null if it does not
           contain a recent search so we need to remove recent searches to ensure the adapter
           doesn't create a view for a null search result */

        List<String[]> list = new ArrayList<>();
        for (String[] recentSearch : recentSearches) {
            if (recentSearch[0] != null && recentSearch[1] != null) {
                list.add(recentSearch);
            }
        }

        view.showRecentSearches(list.toArray(new String[list.size()][2]));
    }

    public void findTrains(){
        view.findTrainsClicked();
    }

    public void swapSearchInput(){
        view.swapSearchInput();
    }

    public void getJourneys(String from, String to) {
        api.getJourneys(Utils.buildQuery(from, to), new CustomListeners.TrainlineCallback() {
            @Override
            public void onResponse(Response<JourneyData> response) {
                Log.d(TAG, context.getString(R.string.requestSuccess, context.getString(R.string.endpointJourneys)));
                view.showJourneys(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, context.getString(R.string.requestFail, context.getString(R.string.endpointJourneys)));
                view.failedGettingJourneys();
                t.printStackTrace();
            }
        });
    }
}
