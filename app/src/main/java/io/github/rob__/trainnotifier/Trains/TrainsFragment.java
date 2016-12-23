package io.github.rob__.trainnotifier.Trains;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.rob__.trainnotifier.API.Models.Journey;
import io.github.rob__.trainnotifier.CustomListeners;
import io.github.rob__.trainnotifier.R;
import io.github.rob__.trainnotifier.Utils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class TrainsFragment extends Fragment implements TrainsView, CustomListeners.JourneyClickListener, CustomListeners.TrainSavedListener {

    private TrainsPresenter presenter;
    @BindView(R.id.rvSavedJourneys) RecyclerView rvSavedJourneys;
    @BindView(R.id.tvNoSavedJourneys) TextView tvNoSavedJourneys;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_trains, container, false);

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
        rvSavedJourneys.setAdapter(new TrainJourneyAdapter(journeys, getContext(), this));
    }

    @Override
    public void journeyClicked(final Journey journey, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Remove this train?");

        final AlertDialog dialog;

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.removeJourney(position, getContext());
                presenter.loadSavedJourneys();
            }
        });

        builder.setNegativeButton("No", null);

        dialog = builder.create();
        dialog.show();
    }
}
