package io.github.rob__.trainnotifier.Details;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.rob__.trainnotifier.API.Models.Mobile.Journey;
import io.github.rob__.trainnotifier.API.Models.Realtime.RealtimeData;
import io.github.rob__.trainnotifier.R;
import io.github.rob__.trainnotifier.Utils.Utils;

public class JourneyActivity extends AppCompatActivity implements JourneyView{

    private JourneyPresenter presenter;
    private Journey journey;
    private Handler handler;

    @BindView(R.id.rvLegs) public RecyclerView rvLegs;
    @BindView(R.id.tvDetailsRoute) public TextView tvRoute;
    @BindView(R.id.tvDetailsInfo) public TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);

        ButterKnife.bind(this);

        journey = Utils.journeyFromJson(getIntent().getStringExtra("journey"));

        presenter = new JourneyPresenter(this);
        presenter.getRealtimeInfo(journey);

        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                presenter.getRealtimeInfo(journey);
                handler.postDelayed(this, 5000);
            }
        });

        rvLegs.setHasFixedSize(true);
        rvLegs.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rvLegs.setAdapter(new JourneyDetailsAdapter(journey, null, getApplicationContext()));

        String departStation = Utils.stationFromCode(journey.getOrigin());
        String arriveStation = Utils.stationFromCode(journey.getDestination());
        tvRoute.setText(getString(R.string.routeFull, departStation, arriveStation));

        String duration = Utils.getTimeDifference(journey.getArrivalDateTime(), journey.getDepartureDateTime());

        int changes = journey.getLegs().size() - 1;

        String changesStr;
        if(changes == 0){
            changesStr = getString(R.string.direct);
        } else {
            changesStr = getResources().getQuantityString(R.plurals.changes, changes, changes);
        }

        tvInfo.setText(duration + ", " + changesStr);
    }

    @Override
    public void onRealtimeReceived(List<RealtimeData> realtimeData){
        /* create a hash map that maps train id to it's realtime data */
        HashMap<String, RealtimeData> stops = new HashMap<>();
        for(RealtimeData train : realtimeData){
            String trainId = train.getService().getServiceUid();
            stops.put(trainId, train);
        }

        /* update our adapter and pass real time info along */
        rvLegs.setAdapter(new JourneyDetailsAdapter(journey, stops, getApplicationContext()));
        rvLegs.invalidate();

        Log.d("JourneyActivity", "Updated adapter with realtime data");
    }

}
