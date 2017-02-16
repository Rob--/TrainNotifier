package io.github.rob__.trainnotifier.Trains;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.rob__.trainnotifier.API.Models.Mobile.Journey;
import io.github.rob__.trainnotifier.CustomDialog;
import io.github.rob__.trainnotifier.CustomListeners;
import io.github.rob__.trainnotifier.R;
import io.github.rob__.trainnotifier.Utils.Utils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class TrainsFragment extends Fragment implements TrainsView, CustomListeners.JourneyClickListener, CustomListeners.TrainSavedListener {

    private TrainsPresenter presenter;
    @BindView(R.id.rvSavedJourneys) RecyclerView rvSavedJourneys;
    @BindView(R.id.tvNoSavedJourneys) TextView tvNoSavedJourneys;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trains, container, false);

        ButterKnife.bind(this, v);

        rvSavedJourneys.setHasFixedSize(true);
        rvSavedJourneys.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter = new TrainsPresenter(this, getContext());
        presenter.loadSavedJourneys();

        return v;
    }

    public static TrainsFragment newInstance(){
        return new TrainsFragment();
    }

    @Override
    public void trainSaved(){
        presenter.loadSavedJourneys();
    }

    @Override
    public void showJourneys(Journey[] journeys){
        tvNoSavedJourneys.setVisibility(journeys.length > 0 ? GONE : VISIBLE);
        rvSavedJourneys.setVisibility(journeys.length > 0 ? VISIBLE : GONE);

        /* journeys will be chronologically ordered due the way they're saved by index */
        rvSavedJourneys.setAdapter(new TrainJourneyAdapter(journeys, TrainJourneyAdapter.SAVED_JOURNEY_ADAPTER, getContext(), this));
    }

    @Override
    public void journeyClicked(final Journey journey, final int position){
        final CustomDialog customDialog = new CustomDialog(getActivity(), R.string.removeQuestion, CustomDialog.SAVED_JOURNEY_DIALOG);
        customDialog.updateViews(journey, false);

        customDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.removeJourney(position, getContext());
                presenter.loadSavedJourneys();
                customDialog.cancel();
            }
        });

        customDialog.display();
    }
}
