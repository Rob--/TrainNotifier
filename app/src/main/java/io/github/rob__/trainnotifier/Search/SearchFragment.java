package io.github.rob__.trainnotifier.Search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.rob__.trainnotifier.API.Models.API;
import io.github.rob__.trainnotifier.API.Models.Journey;
import io.github.rob__.trainnotifier.AnimationUtils;
import io.github.rob__.trainnotifier.CustomListeners;
import io.github.rob__.trainnotifier.R;
import io.github.rob__.trainnotifier.Utils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchFragment extends Fragment implements SearchView, CustomListeners.JourneyClickListener, CustomListeners.RecentSearchListener {

    private SearchPresenter presenter;
    private CustomListeners.TrainSavedListener trainSavedListener;

    @BindView(R.id.etTo) AutoCompleteTextView etTo;
    @BindView(R.id.etFrom) AutoCompleteTextView etFrom;
    @BindView(R.id.rvSearchResults) RecyclerView rvSearchResults;
    @BindView(R.id.rlRecentSearches) RelativeLayout rlRecentSearches;
    @BindView(R.id.rvRecentSearches) RecyclerView rvRecentSearches;
    @BindView(R.id.pbLoadingResults) ProgressBar pbLoadingResults;
    @BindView(R.id.tvLoadingText) TextView tvLoadingText;
    @BindView(R.id.tvSubLoadingText) TextView tvSubLoadingText;
    @BindView(R.id.tvNoRecentSearches) TextView tvNoRecentSearches;

    @OnClick(R.id.btnFindTrains) void btnSubmit(){
        presenter.findTrains();
    }

    @OnClick(R.id.ivSwap) void ivSubmit() {
        presenter.swapSearchInput();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_trains, container, false);

        ButterKnife.bind(this, v);

        presenter = new SearchPresenter(this, getContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, Utils.getStations());
        etTo.setAdapter(adapter);
        etFrom.setAdapter(adapter);

        rvSearchResults.setHasFixedSize(true);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(getContext()));

        rvRecentSearches.setHasFixedSize(true);
        rvRecentSearches.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter.getRecentSearches(getContext());

        return v;
    }

    public static SearchFragment newInstance(){
        return new SearchFragment();
    }

    public void setOnTrainSavedListener(CustomListeners.TrainSavedListener listener){
        this.trainSavedListener = listener;
    }

    @Override
    public void swapSearchInput(){
        String from = etFrom.getText().toString();
        String to = etTo.getText().toString();

        etFrom.setText(to);
        etTo.setText(from);

        etFrom.clearFocus();
        etTo.clearFocus();
    }

    @Override
    public void findTrainsClicked(){
        String from = etFrom.getText().toString();
        String to = etTo.getText().toString();

        String errorText = Utils.formSearchError(from, to);
        if(!errorText.equals("")){
            Toast.makeText(getContext(), errorText, Toast.LENGTH_SHORT).show();
            return;
        }

        Utils.saveRecentSearch(from, to, getContext());

        if(!AnimationUtils.collapsedBefore(rlRecentSearches)){
            AnimationUtils.collapseView(rlRecentSearches);
        }

        rvSearchResults.setVisibility(GONE);
        pbLoadingResults.setVisibility(VISIBLE);
        tvLoadingText.setVisibility(VISIBLE);
        tvLoadingText.setText(R.string.searchLoadingText);
        tvSubLoadingText.setVisibility(GONE);

        presenter.getJourneys(from, to);
    }

    @Override
    public void showRecentSearches(String[][] recentSearches){
        /* if there are no recent searches, manipulate the layout to show the text view
           stating there are no recent searches instead of showing an empty recycler view */
        if(Utils.isRecentSearchesEmpty(getContext())){
            tvNoRecentSearches.setVisibility(View.VISIBLE);
            rvRecentSearches.setVisibility(View.GONE);
        } else {
            tvNoRecentSearches.setVisibility(View.GONE);
            rvRecentSearches.setVisibility(View.VISIBLE);
            rvRecentSearches.setAdapter(new RecentSearchAdapter(recentSearches, this));
        }
    }

    @Override
    public void recentSearchClicked(String[] recentSearch){
        etFrom.setText(recentSearch[0]);
        etTo.setText(recentSearch[1]);

        etFrom.clearFocus();
        etTo.clearFocus();
    }

    @Override
    public void showJourneys(API journeys){
        rvSearchResults.setVisibility(VISIBLE);
        pbLoadingResults.setVisibility(GONE);
        tvLoadingText.setVisibility(GONE);
        tvSubLoadingText.setVisibility(GONE);

        rvSearchResults.setAdapter(new SearchResultAdapter(journeys, getContext(), this));
    }

    @Override
    public void failedGettingJourneys(){
        rvSearchResults.setVisibility(GONE);
        pbLoadingResults.setVisibility(GONE);
        tvLoadingText.setVisibility(VISIBLE);
        tvLoadingText.setText(R.string.searchErrorText);
        tvSubLoadingText.setVisibility(VISIBLE);
        tvSubLoadingText.setText(R.string.searchErrorExplanationText);
    }

    @Override
    public void journeyClicked(final Journey journey, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = getActivity().getLayoutInflater().inflate(R.layout.confirm_dialog, null);
        builder.setView(view);
        builder.setTitle(R.string.pollQuestion);

        String departTime = Utils.getFormattedTime(journey.getDepartureDateTime());
        TextView tvDepart = (TextView) view.findViewById(R.id.tvRowDepart);
        tvDepart.setText(departTime);

        String arriveTime = Utils.getFormattedTime(journey.getArrivalDateTime());
        TextView tvArrive = (TextView) view.findViewById(R.id.tvRowArrive);
        tvArrive.setText(arriveTime);

        String platform = journey.getLegs().get(0).getOrigin().getPlatform();
        TextView tvPlatform = (TextView) view.findViewById(R.id.tvRowPlatform);
        if(platform != null && !platform.isEmpty()){
            tvPlatform.setText(getString(R.string.platform, platform));
        } else {
            tvPlatform.setText("");
        }

        String destinationCode = journey.getLegs().get(0).getDestination().getStationCode();
        TextView tvRoute = (TextView) view.findViewById(R.id.tvRowRoute);
        tvRoute.setText(getString(R.string.route, Utils.stationFromCode(destinationCode)));

        int changes = journey.getLegs().size() - 1;
        TextView tvChanges = (TextView) view.findViewById(R.id.tvRowChanges);
        if(changes == 0) {
            tvChanges.setText(R.string.direct);
        } else {
            tvChanges.setText(getResources().getQuantityString(R.plurals.changes, changes));
        }

        final AlertDialog alertDialog = builder.create();

        Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.saveJourney(journey, getContext());
                trainSavedListener.trainSaved();
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }
}
