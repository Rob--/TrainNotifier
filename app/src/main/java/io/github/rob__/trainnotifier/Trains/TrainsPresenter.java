package io.github.rob__.trainnotifier.Trains;

import android.content.Context;

import io.github.rob__.trainnotifier.Utils;

class TrainsPresenter {

    private final TrainsView view;
    private final Context context;

    public TrainsPresenter(TrainsView view, Context context){
        this.view = view;
        this.context = context;
    }

    public void loadSavedJourneys(){
        view.showJourneys(Utils.getAllJourneys(context));
    }
}
