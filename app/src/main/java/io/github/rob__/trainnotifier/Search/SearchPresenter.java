package io.github.rob__.trainnotifier.Search;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.rob__.trainnotifier.API.Models.API;
import io.github.rob__.trainnotifier.API.TrainlineAPI;
import io.github.rob__.trainnotifier.CustomListeners;
import io.github.rob__.trainnotifier.Utils;
import retrofit2.Response;

public class SearchPresenter {

    private String TAG = "SearchPresenter";
    private SearchView view;
    private TrainlineAPI api;

    public SearchPresenter(SearchView view){
        this.view = view;
        this.api = new TrainlineAPI();
    }

    public void getRecentSearches(Context context){
        String[][] recentSearches = Utils.getRecentSearches(context);

        /* recentSearches will be a fixed size of 5, the element will be null if it doesn not
           contain a recent search so we need to remove recent searches to ensure the adapter
           doesn't create a view for a null search result */

        List<String[]> list = new ArrayList<>();
        for(int i = 0; i < recentSearches.length; i++){
            if(recentSearches[i][0] != null && recentSearches[i][1] != null){
                list.add(recentSearches[i]);
            }
        }

        view.showRecentSearches(list.toArray(new String[list.size()][2]));
    }

    public void findTrains(){
        view.findTrainsClicked();
    }

    public void getJourneys(String from, String to) {
        api.getJourneys(Utils.buildQuery(from, to), new CustomListeners.TrainlineCallback() {
            @Override
            public void onResponse(Response<API> response) {
                Log.d(TAG, "getJourneys API call successful");
                view.showJourneys(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "getJourneys API call failed");
                view.failedGettingJourneys();
                t.printStackTrace();
            }
        });
    }
}
