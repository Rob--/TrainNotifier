package io.github.rob__.trainnotifier.Search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import io.github.rob__.trainnotifier.API.Models.Mobile.JourneyData;
import io.github.rob__.trainnotifier.API.Models.Mobile.Journey;
import io.github.rob__.trainnotifier.Utils.AnimationUtils;
import io.github.rob__.trainnotifier.CustomDialog;
import io.github.rob__.trainnotifier.CustomListeners;
import io.github.rob__.trainnotifier.R;
import io.github.rob__.trainnotifier.Trains.TrainJourneyAdapter;
import io.github.rob__.trainnotifier.Utils.Utils;

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

    @BindView(R.id.btnFindTrains) Button btnFindTrains;
    @OnClick(R.id.btnFindTrains) void btnSubmit(){
        presenter.findTrains();
    }

    @OnClick(R.id.ivSwap) void ivSubmit() {
        presenter.swapSearchInput();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

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

        /* hide the keyboard */
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getActivity().getCurrentFocus();
        if(currentFocus != null){
            manager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
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
    public void showJourneys(JourneyData journeys){
        rvSearchResults.setVisibility(VISIBLE);
        pbLoadingResults.setVisibility(GONE);
        tvLoadingText.setVisibility(GONE);
        tvSubLoadingText.setVisibility(GONE);

        Journey[] journeyArray = new Journey[journeys.getJourneys().size()];
        journeyArray = journeys.getJourneys().toArray(journeyArray);
        rvSearchResults.setAdapter(new TrainJourneyAdapter(journeyArray, TrainJourneyAdapter.SEARCH_RESULT_ADAPTER, getContext(), this));
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
        final CustomDialog customDialog = new CustomDialog(getActivity(), R.string.pollQuestion);

        customDialog.updateViews(journey, true);
        customDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                journey.setAdditionalProperty("pollTime", customDialog.getPollingTime());
                Utils.saveJourney(journey, getContext());
                if(trainSavedListener != null) trainSavedListener.trainSaved();
                customDialog.cancel();
            }
        });

        customDialog.display();
    }
}
