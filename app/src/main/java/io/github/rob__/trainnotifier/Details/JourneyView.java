package io.github.rob__.trainnotifier.Details;

import android.view.View;

import java.util.List;

import io.github.rob__.trainnotifier.API.Models.Realtime.RealtimeData;

interface JourneyView {

    void onRealtimeReceived(List<RealtimeData> realtimeData);
    void doAnimation(View view);

}
