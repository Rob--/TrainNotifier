package io.github.rob__.trainnotifier.Trains;

import android.content.Context;

import io.github.rob__.trainnotifier.API.TrainlineAPI;
import io.github.rob__.trainnotifier.Utils;

public class TrainsPresenter {

    private TrainsView view;
    private TrainlineAPI api;
    private Context context;

    public TrainsPresenter(TrainsView view, Context context){
        this.view = view;
        this.api = new TrainlineAPI();
        this.context = context;
    }

    public void loadSavedJourneys(){
        view.showJourneys(Utils.getAllJourneys(context));
    }
}
